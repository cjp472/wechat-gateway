package com.wangxiaobao.wechatgateway.controller.store;

import com.wangxiaobao.wechatgateway.entity.header.LoginUserInfo.Merchant;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.wangxiaobao.wechatgateway.VO.ResultVO;
import com.wangxiaobao.wechatgateway.VO.store.BrandVO;
import com.wangxiaobao.wechatgateway.VO.store.StoreDistanceVO;
import com.wangxiaobao.wechatgateway.entity.geo.GeoAddress;
import com.wangxiaobao.wechatgateway.entity.geo.GeoDistance;
import com.wangxiaobao.wechatgateway.entity.header.LoginUserInfo;
import com.wangxiaobao.wechatgateway.entity.header.PlatformOrgUserInfo;
import com.wangxiaobao.wechatgateway.entity.store.BrandInfo;
import com.wangxiaobao.wechatgateway.entity.store.StoreInfo;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.form.store.StoreInfoFormForBrand;
import com.wangxiaobao.wechatgateway.form.store.StoreInfoFormForMerchant;
import com.wangxiaobao.wechatgateway.form.store.StoreTimesForm;
import com.wangxiaobao.wechatgateway.service.push.PushService;
import com.wangxiaobao.wechatgateway.service.store.BrandInfoService;
import com.wangxiaobao.wechatgateway.service.store.StoreInfoService;
import com.wangxiaobao.wechatgateway.utils.AmapUtil;
import com.wangxiaobao.wechatgateway.utils.JsonResult;
import com.wangxiaobao.wechatgateway.utils.KeyUtil;
import com.wangxiaobao.wechatgateway.utils.ResultVOUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by halleyzhang on 2018/1/13.
 */
@RestController
@RequestMapping("/storeinfo")
@Slf4j
public class StoreInfoController {

  @Autowired
  private StoreInfoService storeInfoService;

  @Autowired
  private BrandInfoService brandInfoService;
  @Autowired
  private PushService pushService;
  @Autowired
  private RestTemplate restTemplate;
  @Autowired
  private AmapUtil amapUtil;
  @Value("${fleetingtime.merchantInfoBySn}")
  private String merchantInfoBySnUrl;
  @Value("${fleetingtime.merchantSnCount}")
  private String merchantSnCountUrl;

  /**
   * 集码器APP，品牌账号创建门店信息
   * @param storeInfoFormForBrand
   * @param bindingResult
   * @param loginUserInfo 升鹏gateway注入的APP登陆账号信息
   * @return
   */
  @PostMapping("/saveFromBrand")
  public ResultVO<StoreInfo> saveFromBrand(@Valid StoreInfoFormForBrand storeInfoFormForBrand,BindingResult bindingResult,LoginUserInfo loginUserInfo){
    if (bindingResult.hasErrors()) {
      log.error("【品牌创建商家】参数不正确, storeInfoFormForBrand={}", storeInfoFormForBrand);
      throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
          bindingResult.getFieldError().getDefaultMessage());
    }

    //如果参数中storeType为1:已合作，merchantAccount必填
    checkStoreType(storeInfoFormForBrand);

    //门店绑定账号验证 1.绑定账号必须是品牌下的账号 2.绑定账号下桌牌数量大于0
    checkMerchantAccout(storeInfoFormForBrand, loginUserInfo);

    StoreInfo storeInfo = new StoreInfo();
    //如果storeId为空，生成storeId，否则查询出最新的storeInfo用于更新
    if(!StringUtils.isEmpty(storeInfoFormForBrand.getStoreId())){
      storeInfo = storeInfoService.findOne(storeInfoFormForBrand.getStoreId());
    }else{
      storeInfoFormForBrand.setStoreId(KeyUtil.genUniqueKey());
    }
    BeanUtils.copyProperties(storeInfoFormForBrand,storeInfo);
    StoreInfo result = storeInfoService.save(storeInfo);

    log.info("【品牌创建商家】成功 storeInfoFormForBrand={} storeInfo={}",storeInfoFormForBrand,result);
    return ResultVOUtil.success(result);
  }

  private void checkMerchantAccout(@Valid StoreInfoFormForBrand storeInfoFormForBrand,
      LoginUserInfo loginUserInfo) {
    if (!StringUtils.isEmpty(storeInfoFormForBrand.getMerchantAccount()) && storeInfoFormForBrand.getStoreType()==1){
      //验证账号是否合法
      List<Merchant> merchants = loginUserInfo.getMerchant();
      boolean hit = false;
      for(Merchant merchant:merchants){
        if(merchant.getMerchantAccount().equals(storeInfoFormForBrand.getMerchantAccount())){
          hit = true;
        }
      }
      if(!hit){
        log.error("【品牌创建商家】门店账号不合法, storeInfoFormForBrand={}", storeInfoFormForBrand);
        throw new CommonException(ResultEnum.MERCHANTACCOUT_INVALID);
      }

      //验证账号下是否有桌牌
      ResultVO<Integer> snCount = restTemplate.getForObject(
          merchantSnCountUrl + "?merchantAccount=" + storeInfoFormForBrand.getMerchantAccount(),
          ResultVO.class);
      if (snCount.getData() <= 0) {
        log.error("【品牌创建商家】门店账号下无桌牌，不可绑定, storeInfoFormForBrand={}", storeInfoFormForBrand);
        throw new CommonException(ResultEnum.NO_MORE_TABLECARD);
      }
    }
  }

  private void checkStoreType(@Valid StoreInfoFormForBrand storeInfoFormForBrand) {
    if (storeInfoFormForBrand.getStoreType()==1 && StringUtils.isEmpty(storeInfoFormForBrand.getMerchantAccount())){
      log.error("【品牌创建商家】商家账号为空, storeInfoFormForBrand={}", storeInfoFormForBrand);
      throw new CommonException(ResultEnum.ACCOUNT_IS_NULL);
    }
  }

  //brand delete store
  @PostMapping("/delete")
  public ResultVO delete(@RequestParam("storeId") String storeId){
    storeInfoService.delete(storeId);
    return ResultVOUtil.success();
  }

  /**
   * 集码器APP，商家账号登陆创建门店信息
   * @param storeInfoFormForMerchant
   * @param bindingResult
   * @return
   */
  @PostMapping("/saveFromMerchant")
  public ResultVO<StoreInfo> saveFromMerchant(@Valid StoreInfoFormForMerchant storeInfoFormForMerchant,BindingResult bindingResult){
    if (bindingResult.hasErrors()) {
      log.error("【商家创建商家】参数不正确, storeInfoFormForMerchant={}", storeInfoFormForMerchant);
      throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
          bindingResult.getFieldError().getDefaultMessage());
    }
    StoreInfo storeInfo = new StoreInfo();

    //如果storeId为空，生成storeId，否则查询出最新的storeInfo用于更新
    if(!StringUtils.isEmpty(storeInfoFormForMerchant.getStoreId())){
      storeInfo = storeInfoService.findOne(storeInfoFormForMerchant.getStoreId());
    }else{
      storeInfoFormForMerchant.setStoreId(KeyUtil.genUniqueKey());
    }
    BeanUtils.copyProperties(storeInfoFormForMerchant,storeInfo);
    //商家创建，类型为合作商家
    storeInfo.setStoreType(1);
    StoreInfo result = storeInfoService.save(storeInfo);

    log.info("【商家创建商家】成功 storeInfoFormForMerchant={} storeInfo={}",storeInfoFormForMerchant,result);
    return ResultVOUtil.success(result);
  }

  /**
   * 保存门店的菜品图片
   * @param storeId
   * @param storeMenu
   * @return
   */
  @PostMapping("/saveStoreMenu")
  public ResultVO<StoreInfo> storeMenuSave(@RequestParam("storeId") String storeId,
      @RequestParam("storeMenu") String storeMenu){

    StoreInfo result = storeInfoService.storeMenuSave(storeId,storeMenu);
    log.info("【保存菜品】成功 storeId={} storeMenu={} result={}",storeId,storeMenu,result);
    return ResultVOUtil.success(result);
  }

  /**
   * 保存门店的装修图片
   * @param storeId
   * @param storePhoto
   * @return
   */
  @PostMapping("/saveStorePhoto")
  public ResultVO<StoreInfo> storePhotoSave(@RequestParam("storeId") String storeId,
      @RequestParam("storePhoto") String storePhoto){

    StoreInfo result = storeInfoService.storePhotoSave(storeId,storePhoto);
    log.info("【保存门店图片】成功 storeId={} storePhoto={} result={}",storeId,storePhoto,result);
    return ResultVOUtil.success(result);
  }

  @GetMapping("/findByMerchantAccount")
  public ResultVO<StoreInfo> findByMerchantAccount(@RequestParam("merchantAccount") String merchantAccount){
    StoreInfo result = storeInfoService.findByMerchantAccount(merchantAccount);
    return ResultVOUtil.success(result);
  }

  @GetMapping("/findByMerchantId")
  public ResultVO<StoreInfo> findByMerchantId(@RequestParam("merchantId") String merchantId){
    StoreInfo result = storeInfoService.findByMerchantId(merchantId);
    return ResultVOUtil.success(result);
  }

  /**
   * 品牌账号查询门店列表
   * @param brandAccount
   * @return
   */
  @GetMapping("/findByBrandAccount")
  public ResultVO<List<StoreInfo>> findByBrandAccount(@RequestParam("brandAccount") String brandAccount){
    List<StoreInfo> result = storeInfoService.findByBrandAccount(brandAccount);
    return ResultVOUtil.success(result);
  }

  /**
   * 配合商家小程序黄页首页，获取用户与各门店距离以及品牌信息
   * @param longitude
   * @param latitude
   * @param platformOrgUserInfo
   * @return
   */
  @GetMapping("/getDistance")
  public ResultVO<StoreDistanceVO> getDistance(@RequestParam("longitude") String longitude,@RequestParam("latitude") String latitude,PlatformOrgUserInfo platformOrgUserInfo){
    /**
     * 给商家小程序返回的VO，包括以下三个信息
     * brandInfo：品牌信息
     * List<StoreDistanceVO> 用户与门店距离与门店信息
     * GeoAddress 用户的地理信息
     */
    BrandVO result = new BrandVO();

    //获取用户的坐标
    String destination = longitude+","+latitude;

    //检查是否能拿到用户使用的小程序所属品牌，后面需要通过品牌查门店列表
    checkBrandAccount(platformOrgUserInfo);

    //保存每个门店与用户的距离，和门店详细信息
    List<StoreDistanceVO> storeDistances = new ArrayList<>();
    getUserStoreDistance(platformOrgUserInfo, destination, storeDistances);
    result.setStores(storeDistances);

    //获取品牌信息
    BrandInfo brandInfo = brandInfoService.findByBrandAccount(platformOrgUserInfo.getOrganizationAccount());
    result.setBrandInfo(brandInfo);

    //获取用户的地址信息
    GeoAddress geoAddress = amapUtil.getAddress(destination);
    result.setUserAddress(geoAddress);
    log.info("【获取用户地址】成功 address={}",geoAddress);
    log.info("【获取用户附近门店】成功 result={}",result);

    return ResultVOUtil.success(result);
  }

  private void getUserStoreDistance(PlatformOrgUserInfo platformOrgUserInfo, String destination,
      List<StoreDistanceVO> storeDistances) {
    //通过品牌账号获取门店列表
    List<StoreInfo> stores = this.storeInfoService.findByBrandAccount(platformOrgUserInfo.getOrganizationAccount());

    //把没有地址的门店特殊处理，标记为未获取地址
    List<StoreInfo> noLocationStores = new ArrayList<>();
    for(StoreInfo storeInfo:stores){
      if(StringUtils.isEmpty(storeInfo.getStoreLocation())){
        StoreDistanceVO storeDistanceVO = new StoreDistanceVO();
        BeanUtils.copyProperties(storeInfo,storeDistanceVO);
        storeDistanceVO.setDistance("未获取距离");
        storeDistanceVO.setStoreLocation("未获取坐标");
        storeDistances.add(storeDistanceVO);
        noLocationStores.add(storeInfo);
      }
    }

    //删除没有地址的门店
    for(StoreInfo storeInfo:noLocationStores){
      stores.remove(storeInfo);
    }

    //配置了坐标的商家去高德地图获取与用户的距离
    if(!CollectionUtils.isEmpty(stores)){
      String orgin = "";
      for(StoreInfo storeInfo:stores){
        orgin = orgin+"|"+storeInfo.getStoreLocation();//拼接每个门店的坐标批量查询
      }

      List<GeoDistance> distances = new ArrayList<>();
      if(StringUtils.hasText(orgin)){
        orgin = orgin.substring(1,orgin.length());
        distances = amapUtil.getDistance(orgin,destination);
      }
      //把距离放回到商家返回给前端
      if(!CollectionUtils.isEmpty(distances)){
        for(int i=0;i<stores.size();i++){
          StoreDistanceVO storeDistanceVO = new StoreDistanceVO();
          BeanUtils.copyProperties(stores.get(i),storeDistanceVO);
          storeDistanceVO.setDistance(distances.get(i).getDistance());
          storeDistances.add(storeDistanceVO);
        }
      }
    }
  }

  private void checkBrandAccount(PlatformOrgUserInfo platformOrgUserInfo) {
    if(null == platformOrgUserInfo.getOrganizationAccount()){
      throw new CommonException(ResultEnum.BRAND_NOT_FOUND);
    }
  }

  /**
    * @methodName: updateStoreTimes
    * @Description: 更新商户倒计时配置
    * @Params: [storeTimesForm, bindingResult]
    * @Return: com.wangxiaobao.wechatgateway.VO.ResultVO<com.wangxiaobao.wechatgateway.entity.store.StoreInfo>
    * @createUser: ZhouTong
    * @createDate: 2018/1/19 15:40
    * @updateUser: ZhouTong
    * @updateDate: 2018/1/19 15:40
    */
  @PostMapping("/updateStoreTimes")
  public ResultVO<StoreInfo>  updateStoreTimes(@Validated StoreTimesForm storeTimesForm,BindingResult bindingResult) throws Exception{
    if(bindingResult.hasErrors())
        throw new CommonException(1,bindingResult.getFieldError().getDefaultMessage());
    if(storeTimesForm.getIsOpenTime() == 1 ){
      if(!StringUtils.hasText(storeTimesForm.getPromise())) {
        throw new CommonException(1, "请配置商家承诺");
      }
      if(storeTimesForm.getPromise().length() > 50) {
        throw new CommonException(1, "商家承诺不能大于50个字");
      }
    }
    StoreInfo storeInfo = storeInfoService.findByMerchantAccount(storeTimesForm.getMerchantAccount());
    if(storeInfo == null)
      throw new CommonException(1,"没有找到该商家信息为空");
    BeanUtils.copyProperties(storeTimesForm,storeInfo);
    storeInfo = storeInfoService.save(storeInfo);
    JsonResult jsonResult=pushService.pushStoreUpdateTimesMessage(storeInfo.getMerchantAccount(), JSONObject.toJSONString(storeInfo));
    log.info("更新商家倒计时推送消息返回:{}",jsonResult);
    return ResultVOUtil.success(storeInfo);
  }
  /**
    * @methodName: findStoreInfoBySn
    * @Description: 通过SN获取商户信息
    * @Params: [sn]
    * @Return: com.wangxiaobao.wechatgateway.VO.ResultVO<com.wangxiaobao.wechatgateway.entity.store.StoreInfo>
    * @createUser: ZhouTong
    * @createDate: 2018/1/19 11:46
    * @updateUser: ZhouTong
    * @updateDate: 2018/1/19 11:46
    */
  @GetMapping("/findStoreInfoBySn")
  public ResultVO<StoreInfo>  findStoreInfoBySn(String sn) throws Exception{
    if(!StringUtils.hasText(sn))
      throw new CommonException(1,"SN不能为空");
    String result = null;
    try {
      result = restTemplate.getForObject(merchantInfoBySnUrl + "?sn=" + sn, String.class);
    }catch (Exception e){
       log.error("通过SN查询商家信息异常",e);
      throw new CommonException(1,"远程调用获取商家信息异常");
    }
    JsonResult jsonResult=JSONObject.parseObject(result,JsonResult.class);
    JSONObject jsonObject=(JSONObject)jsonResult.getData();
    String mechantAccount = jsonObject.getString("merchantAccount");
    StoreInfo storeInfo = storeInfoService.findByMerchantAccount(mechantAccount);
    return ResultVOUtil.success(storeInfo);
  }
}
