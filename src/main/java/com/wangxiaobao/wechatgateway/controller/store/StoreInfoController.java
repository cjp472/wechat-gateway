package com.wangxiaobao.wechatgateway.controller.store;

import com.wangxiaobao.wechatgateway.VO.ResultVO;
import com.wangxiaobao.wechatgateway.VO.store.BrandVO;
import com.wangxiaobao.wechatgateway.VO.store.StoreDistanceVO;
import com.wangxiaobao.wechatgateway.entity.geo.GeoDistance;
import com.wangxiaobao.wechatgateway.entity.header.PlateformOrgUserInfo;
import com.wangxiaobao.wechatgateway.entity.header.PlateformOrgUserInfo.Merchant;
import com.wangxiaobao.wechatgateway.entity.store.BrandInfo;
import com.wangxiaobao.wechatgateway.entity.store.StoreInfo;
import com.wangxiaobao.wechatgateway.enums.ResultEnum;
import com.wangxiaobao.wechatgateway.exception.CommonException;
import com.wangxiaobao.wechatgateway.form.store.StoreInfoForm;
import com.wangxiaobao.wechatgateway.service.store.BrandInfoService;
import com.wangxiaobao.wechatgateway.service.store.StoreInfoService;
import com.wangxiaobao.wechatgateway.utils.AmapUtil;
import com.wangxiaobao.wechatgateway.utils.KeyUtil;
import com.wangxiaobao.wechatgateway.utils.ResultVOUtil;
import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
  private AmapUtil amapUtil;

  @PostMapping("/save")
  public ResultVO<StoreInfo> save(@Valid StoreInfoForm storeInfoForm,BindingResult bindingResult){
    if (bindingResult.hasErrors()) {
      log.error("【创建商家】参数不正确, storeInfoForm={}", storeInfoForm);
      throw new CommonException(ResultEnum.PARAM_ERROR.getCode(),
          bindingResult.getFieldError().getDefaultMessage());
    }
    StoreInfo storeInfo = new StoreInfo();

    //如果storeId为空，生成storeId，否则查询出最新的storeInfo用于更新
    if(!StringUtils.isEmpty(storeInfoForm.getStoreId())){
      storeInfo = storeInfoService.findOne(storeInfoForm.getStoreId());
    }else{
      storeInfoForm.setStoreId(KeyUtil.genUniqueKey());
    }
    BeanUtils.copyProperties(storeInfoForm,storeInfo);
    StoreInfo result = storeInfoService.save(storeInfo);

    //如果门店坐标为空，从高德地图获取坐标  目前前端传坐标
//    if(StringUtils.isEmpty(result.getStoreLocation())){
//      storeInfoService.storeLocationSave(result);
//    }
    return ResultVOUtil.success(result);
  }

  @PostMapping("/saveStoreMenu")
  public ResultVO<StoreInfo> storeMenuSave(@RequestParam("merchantAccount") String merchantAccount,
      @RequestParam("storeMenu") String storeMenu){

    StoreInfo result = storeInfoService.storeMenuSave(merchantAccount,storeMenu);
    return ResultVOUtil.success(result);
  }

  @PostMapping("/saveStorePhoto")
  public ResultVO<StoreInfo> storePhotoSave(@RequestParam("merchantAccount") String merchantAccount,
      @RequestParam("storePhoto") String storePhoto){

    StoreInfo result = storeInfoService.storePhotoSave(merchantAccount,storePhoto);
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
   * 配合黄页首页，获取用户与商户距离
   * @param longitude
   * @param latitude
   * @param plateformOrgUserInfo
   * @return
   */
  @GetMapping("/getDistance")
  public ResultVO<StoreDistanceVO> getDistance(@RequestParam("longitude") String longitude,@RequestParam("latitude") String latitude,PlateformOrgUserInfo plateformOrgUserInfo){
    //获取用户的坐标
    String destination = longitude+","+latitude;

    //从header里获取用户进入的小程序所在品牌的商家列表
    List<String> merchantIds = new ArrayList<>();
    for(Merchant merchant: plateformOrgUserInfo.getMerchant()){
      merchantIds.add(merchant.getMerchantId());
    }

    //把没有地址的门店特殊处理，标记为未获取地址
    List<StoreDistanceVO> storeDistances = new ArrayList<>();
    List<StoreInfo> stores = this.storeInfoService.findByMerchantIds(merchantIds);
    for(StoreInfo storeInfo:stores){
      if(StringUtils.isEmpty(storeInfo.getStoreLocation())){
        stores.remove(storeInfo);
        StoreDistanceVO storeDistanceVO = new StoreDistanceVO();
        BeanUtils.copyProperties(storeInfo,storeDistanceVO);
        storeDistanceVO.setDistance("未获取距离");
        storeDistanceVO.setStoreLocation("未获取坐标");
        storeDistances.add(storeDistanceVO);
      }
    }

    //配置了坐标的商家去高德地图获取与用户的距离
    String orgin = "";
    for(StoreInfo storeInfo:stores){
      orgin = orgin+"|"+storeInfo.getStoreLocation();//拼接每个门店的坐标批量查询
    }
    orgin = orgin.substring(1,orgin.length());
    List<GeoDistance> distances = amapUtil.getDistance(orgin,destination);

    //把距离放回到商家返回给前端
    for(int i=0;i<stores.size();i++){
      StoreDistanceVO storeDistanceVO = new StoreDistanceVO();
      BeanUtils.copyProperties(stores.get(i),storeDistanceVO);
      storeDistanceVO.setDistance(distances.get(i).getDistance());
      storeDistances.add(storeDistanceVO);
    }

    //返回结果放入品牌信息，因为黄页中顶部有品牌信息要展示
    BrandInfo brandInfo = brandInfoService.findByOrgId(plateformOrgUserInfo.getOrgId());
    BrandVO result = new BrandVO();
    result.setBrandInfo(brandInfo);
    result.setStores(storeDistances);
    return ResultVOUtil.success(result);
  }
}
