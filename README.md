# API
## 品牌管理门店
- <a href="#1">品牌创建门店</a>  

- <a href="#2">品牌删除门店</a>

- <a href="#3">品牌查询门店</a>

- <a href="#9">保存品牌信息</a>

- <a href="#8">查询品牌信息</a>

- <a href="#20">sn查询品牌APP信息</a>


## 商家管理门店
- <a href="#4">商家创建门店</a>

- <a href="#5">商家查询门店</a>

- <a href="#6">保存门店菜品图片</a>

- <a href="#7">保存门店菜品图片</a>

## 商家广告
- <a href="#11">保存广告</a>

- <a href="#12">通过广告ID获取广告</a>

- <a href="#17">通过广告ID删除广告</a>

- <a href="#13">通过商家账号获取广告列表</a>

- <a href="#14">保存广告图片</a>

- <a href="#15">通过广告ID获取图片</a>

- <a href="#16">通过图片ID删除图片</a>

## 小程序黄页获取集团
- <a href="#10">获取用户与门店距离</a>
 
## 活动规则设置 
- <a href="#18">查询拼图,口令活动规则 </a>
- <a href="#19">修改拼图,口令活动规则 </a>
- <a href="#20">查询限时活动时间规则配置 </a>
- <a href="#21">更新限时活动时间规则配置 </a>

## 平台小程序管理
- <a href="#22">平台小程序列表</a>
- <a href="#23">新增，修改平台小程序</a>
- <a href="#saveqrcodeverify">保存平台小程序二维码校验文件</a>
- <a href="#findqrcodeverify">查询平台小程序二维码校验文件</a>


## 商家公众号授权列表
- <a name="24">商家公众号/小程序授权列表</a>
- <a name="25">商家公众号发起授权</a>
- <a name="26">查询商家公众号/小程序基本信息</a>
- <a name="27">刷新公众号/小程序调用凭证</a>
- <a name="28">查询公众号/小程序绑定的开放平台</a>
- <a name="29">解绑公众号绑定的开放平台</a>
- <a name="30">获取公众号关联的小程序</a>
- <a name="31">公众号取消小程序关联</a>
- <a name="32">公众号关联小程序</a>


## 商家小程序授权列表
- <a name="33">获取小程序类目</a>
- <a name="34">查询小程序模板</a>
- <a name="35">上传小程序模板</a>
- <a name="37">创建效验文件</a>
- <a name="36">获取效验文件</a>
- <a name="38">获取小程序当前版本</a>

## 提交商家小程序列表
- <a name="39">提交商家小程序列表</a>
- <a name="41">提交审核</a>
- <a name="40">取消审核</a>
- <a name="42">刷新审核状态</a>

## 发布商户小程序列表
- <a name="43">发布商户小程序列表</a>
- <a name="44">发布小程序代码</a>



<a name="1">品牌创建门店</a>
### 品牌创建门店
```
POST /wechatgateway/storeinfo/saveFromBrand

参数：
storeProvince:四川
storeCity:成都
storeDistrict:华阳
storeAddress:华府大道一段
storeDescription:好吃
storePhone:88888888
storeOfficehours:10-22
haveWifi:1
storeName:销魂掌华阳店10  //必填
storeLogo:https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=439339977,98971476&fm=27&gp=0.jpg
brandAccount:BOSS3  //必填
storeType:0  //必填  0：未合作  1：合作 
merchantAccount:WCBBJ  //如果storeType为1，merchantAccount必填

返回：
{
    "code": 0,
    "msg": "成功",
    "data": {
        "storeId": "1516613025071432490",
        "brandAccount": "BOSS3",
        "storeType": 0,
        "storeName": "销魂掌华阳店11",
        "storeProvince": "四川",
        "storeCity": "成都",
        "storeDistrict": "华阳",
        "storeAddress": "华府大道一段",
        "storeLogo": "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=439339977,98971476&fm=27&gp=0.jpg",
        "storeDescription": "好吃",
        "storePhone": "88888888",
        "storeOfficehours": "10-22",
        "haveParking": 0,
        "haveWifi": 1,
        "havePrivateroom": 0,
        "haveBooking": 0,
        "isOpenTime": 0,
        "times": 1
    }
}

```

<a name="2">品牌删除门店</a>
### 品牌删除门店
``` 
POST /wechatgateway/storeinfo/delete?storeId=1516613025071432490

```
<a name="3">品牌查询门店</a>
### 品牌查询门店
``` 
GET /wechatgateway/storeinfo/findByBrandAccount?brandAccount=BOSS3

返回：
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "storeId": "12345",
            "merchantAccount": "XHZHYD",
            "merchantId": "123456",
            "brandAccount": "BOSS3",
            "storeType": 0,
            "storeName": "销魂掌华阳店",
            "storeProvince": "四川省",
            "storeCity": "成都市",
            "storeDistrict": "华阳",
            "storeAddress": "华府大道1号",
            "storeDescription": "好吃",
            "storePhone": "88888888",
            "storeOfficehours": "10-22",
            "haveParking": 1,
            "haveWifi": 0,
            "havePrivateroom": 0,
            "haveBooking": 0,
            "storeMenu": "[]",
            "isOpenTime": 1,
            "times": 2,
            "promise": "斯蒂芬斯蒂芬芬斯蒂芬",
            "createTime": 1516289163000,
            "updateTime": 1516612162000
        },
        {
            "storeId": "1516091350913998389",
            "merchantAccount": "XHZHYD1",
            "merchantId": "1234561",
            "brandAccount": "BOSS3",
            "storeType": 0,
            "storeName": "销魂掌华阳店1",
            "storeProvince": "四川",
            "storeCity": "成都市",
            "storeDistrict": "华阳",
            "storeAddress": "车各庄1234",
            "storeLocation": "104.062026,30.505954",
            "storeLogo": "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=439339977,98971476&fm=27&gp=0.jpg",
            "storeDescription": "好吃",
            "storePhone": "88888888",
            "storeOfficehours": "10-22",
            "haveParking": 0,
            "haveWifi": 1,
            "havePrivateroom": 0,
            "haveBooking": 0,
            "storeMenu": "[{\"picUrl\":\"https:\\/\\/adv.wangxiaobao.cc\\/FvrNEfmD1rgW5YOOyORySBcjc5C5\",\"dishName\":\"1\"},{\"picUrl\":\"https:\\/\\/adv.wangxiaobao.cc\\/FtGC_eocWIWc9WWMN2jWTD9sWK6N\",\"dishName\":\"2\"}]",
            "storePhoto": "[\"https:\\/\\/adv.wangxiaobao.cc\\/FoO07JfWGl5cgqkirDFfnChPa4sI\"]",
            "isOpenTime": 0,
            "times": 0,
            "createTime": 1516091350000,
            "updateTime": 1516612163000
        }
    ]
}

```

<a name="4">商家创建门店</a>
### 商家创建门店
``` 
POST /wechatgateway/storeinfo/saveFromMerchant

参数：
storeProvince:四川
storeCity:成都
storeDistrict:华阳
storeAddress:华府大道一段
storeDescription:好吃
storePhone:88888888
storeOfficehours:10-22
haveWifi:1
storeName:销魂掌华阳店11  //必填
storeLogo:https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=439339977,98971476&fm=27&gp=0.jpg
brandAccount:BOSS3  //必填
storeType:1  //必填 商家账号登陆APP，创建门店信息时，storeType必须为1 表示合作
merchantId:1234  
merchantAccount:XHZHY11  //必填

返回：
{
    "code": 0,
    "msg": "成功",
    "data": {
        "storeId": "1516613424093685505",
        "merchantAccount": "XHZHY11",
        "merchantId": "1234",
        "brandAccount": "BOSS3",
        "storeType": 1,
        "storeName": "销魂掌华阳店11",
        "storeProvince": "四川",
        "storeCity": "成都",
        "storeDistrict": "华阳",
        "storeAddress": "华府大道一段",
        "storeLogo": "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=439339977,98971476&fm=27&gp=0.jpg",
        "storeDescription": "好吃",
        "storePhone": "88888888",
        "storeOfficehours": "10-22",
        "haveParking": 0,
        "haveWifi": 1,
        "havePrivateroom": 0,
        "haveBooking": 0,
        "isOpenTime": 0,
        "times": 1
    }
}

```

<a name="5">商家查询门店</a>
### 商家查询门店

```
GET /wechatgateway/storeinfo/findByMerchantAccount?merchantAccount=XHZHYD

返回
{
    "code": 0,
    "msg": "成功",
    "data": {
        "storeId": "12345",
        "merchantAccount": "XHZHYD",
        "merchantId": "123456",
        "storeName": "销魂掌华阳店",
        "storeProvince": "四川",
        "storeCity": "成都",
        "storeDistrict": "华阳",
        "storeAddress": "车各庄1234",
        "storeLocation": "104.062026,30.505954",
        "storeDescription": "好吃",
        "storePhone": "88888888",
        "storeOfficehours": "10-22",
        "haveParking": 0,
        "haveWifi": 1,
        "havePrivateroom": 0,
        "haveBooking": 0,
        "storeMenu": "{http://www.baidu.com1234}",
        "storePhoto": "{http://www.baidu.com1234}",
        "createTime": 1515933666000,
        "updateTime": 1515934287000
    }
}
```

<a name="6">保存门店菜品图片</a>
### 保存门店菜品图片
```
POST /wechatgateway/storeinfo/saveStoreMenu 

参数：
storeId:12345
storeMenu: 移动端自定义格式

```

<a name="7">保存门店菜品图片</a>
### 保存门店图片
``` 
POST /wechatgateway/storeinfo/saveStorePhoto

参数
storeId:12345
storePhoto: 移动端自定义格式

返回 略
```
<a name="8">查询品牌信息</a>
### 查询品牌信息
``` 
GET /wechatgateway/brandinfo/findByOrgId?orgId=12345
或
GET /wechatgateway/brandinfo/findByAccount?orgAccount=XHZ

返回：
{
    "code": 0,
    "msg": "成功",
    "data": {
        "brandId": "1516093835325110194",
        "orgId": "12345",
        "orgAccount": "XHZ",
        "logoUrl": "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516103773318&di=b4ce0fae6c3db2dc600b76c22bd21acb&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fbainuo%2Fwh%3D720%2C436%2Fsign%3Dd035b0daa56eddc426b2bcfc0beb9ac9%2F9c16fdfaaf51f3de6a4e275b9ceef01f3a297915.jpg",
        "createTime": 1516093835000,
        "updateTime": 1516093835000
    }
}
```

<a name="9">保存品牌信息</a>
### 保存品牌信息
``` 
POST /wechatgateway/brandinfo/save

参数
orgAccount:BOSS3  //品牌账号必填
logoUrl:https://adv.wangxiaobao.cc/Fq-a4sS6WKohGbpj8nG_rB2LdojU
orgName:雪花大集团 //品牌名称必填
orgId:9236
brandId:1516093835325110194 //主键，如果有是更新，没有为新建

返回
{
    "code": 0,
    "msg": "成功",
    "data": {
        "brandId": "1516093835325110194",
        "orgId": "9236",
        "orgAccount": "BOSS3",
        "orgName": "雪花大集团",
        "logoUrl": "https://adv.wangxiaobao.cc/Fq-a4sS6WKohGbpj8nG_rB2LdojU",
        "createTime": 1516093835000,
        "updateTime": 1516441246000
    }
}
```

<a name="20">sn查询品牌APP信息</a>
### sn查询品牌APP信息
``` 
GET /wechatgateway/brandinfo/findBySn?sn=CB023170500003B7

返回
{
    "code": 0,
    "msg": "成功",
    "data": {
        "sn": "CB023170500003B7",
        "merchantAccount": "WCBBJ",
        "tabelName": "二号桌",
        "brandAccount": "MINIAPPPP",
        "appId": "wxff3043a582d31257"
    }
}

```



<a name="10">获取用户与门店距离</a>
### 获取用户与门店距离
``` 
GET /wechatgateway/storeinfo/getDistance?longitude=104.068359&latitude=30.538196

参数
longitude:104.068359
latitude:30.538196

返回：
{
    "code": 0,
    "msg": "成功",
    "data": {
        "brandInfo": {
            "brandId": "1516093835325110194",
            "orgId": "9236",
            "orgAccount": "XHZ",
            "orgName": "9236",
            "logoUrl": "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1516103773318&di=b4ce0fae6c3db2dc600b76c22bd21acb&imgtype=0&src=http%3A%2F%2Fe.hiphotos.baidu.com%2Fbainuo%2Fwh%3D720%2C436%2Fsign%3Dd035b0daa56eddc426b2bcfc0beb9ac9%2F9c16fdfaaf51f3de6a4e275b9ceef01f3a297915.jpg",
            "createTime": 1516093835000,
            "updateTime": 1516181968000
        },
        "stores": [
            {
                "merchantId": "691",
                "merchantAccount": "WCBBJ",
                "storeName": "旺小宝（北京分公司）",
                "storeProvince": "四川",
                "storeCity": "成都",
                "storeDistrict": "华阳",
                "storeLocation": "104.062026,30.505954",
                "storeAddress": "华福大道一段",
                "distance": "4850",
                "storeType": 1    //0:未合作  1:合作
            }
        ]
    }
}
```

<a name="11">保存广告</a>
### 保存广告
``` 
POST /wechatgateway/adinfo/save

参数
 
adName:广告2 //必填
adType:0 //必填 0为图片类型 1为视频类型
merchantAccount:WCBBJ //必填

返回
{
    "code": 0,
    "msg": "成功",
    "data": {
        "adId": "1516618016074716763",
        "adName": "广告2",
        "adType": 0,
        "merchantAccount": "WCBBJ"
    }
}
```

<a name="12">通过广告ID获取广告</a>
### 通过广告ID获取广告
``` 
GET /wechatgateway/adinfo/getById?adId=12345
```

<a name="17">通过广告ID删除广告</a>
### 通过广告ID删除广告
``` 
POST /wechatgateway/adinfo/delete?adId=12345
```


<a name="13">通过商家账号获取广告列表</a>
### 通过商家账号获取广告列表
``` 
GET /wechatgateway/adinfo/getByMerchantAccount?merchantAccount=WCBBJ

返回：
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "adInfo": {
                "adId": "1516618016074716763",
                "adName": "广告2",
                "adType": 0,
                "merchantAccount": "WCBBJ",
                "createTime": 1516618003000,
                "updateTime": 1516618003000
            },
            "adDetails": [
                {
                    "detailId": "1516620474735486109",
                    "adId": "1516618016074716763",
                    "detailUrl": "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=439339977,98971476&fm=27&gp=0.jpg",
                    "detailKey": "1111111",
                    "detailSize": 1024000,
                    "isneedwifi": 1,
                    "createTime": 1516620462000,
                    "updateTime": 1516620462000
                }
            ]
        }
    ]
}

```

<a name="14">保存广告图片</a>
### 保存广告图片
``` 
POST /wechatgateway/adDetail/save
```
参数
``` 
adId:1516618016074716763 //必填 广告ID
detailUrl:https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=439339977,98971476&fm=27&gp=0.jpg  //必填
detailKey:1111111 //必填 七牛key
detailSize:1024000 //必填 广告大小 KB
isneedwifi:1 //必填  0：不需要wifi下载 1：需要wifi下载
```
返回
``` 
{
    "code": 0,
    "msg": "成功",
    "data": {
        "detailId": "1516620474735486109",
        "adId": "1516618016074716763",
        "detailUrl": "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=439339977,98971476&fm=27&gp=0.jpg",
        "detailKey": "1111111",
        "detailSize": 1024000,
        "isneedwifi": 1
    }
}
```

<a name="15">通过广告ID获取图片</a>
### 通过广告ID获取图片

```
GET /wechatgateway/adDetail/getByAdId?adId=1234
```
返回
``` 
{
    "code": 0,
    "msg": "成功",
    "data": [
        {
            "detailId": "1516355711441314276",
            "adId": "1516352831383641402",
            "detailUrl": "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=439339977,98971476&fm=27&gp=0.jpg",
            "createTime": 1516355708000,
            "updateTime": 1516355708000
        },
        {
            "detailId": "1516355831836111215",
            "adId": "1516352831383641402",
            "detailUrl": "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=439339977,98971476&fm=27&gp=0.jpg",
            "createTime": 1516355828000,
            "updateTime": 1516355828000
        }
    ]
}
```

<a name="16">通过图片ID删除图片</a>
### 通过图片ID删除图片
``` 
GET /wechatgateway/adDetail/delete?detailId=1516354826607934901
```


<a name="18">拼图,口令游戏规则设置</a>
### 查询拼图游戏规则设置

```
post /wechatgateway/constantcode/findConstantCodeByType
```
参数 key-value
``` 
type:time_limit_game_rule //必填(固定值)
constantKey:jigsaw_game_rule  //必填（口令：speak_game_rule，拼图：jigsaw_game_rule）
```

返回
``` 
{
    "code": "0",
    "message": "成功",
    "data": {
        "constantCodeId": "7",
        "type": "time_limit_game_rule",
        "value": "{\"intervals\":[60,90,120,150,180],\"pictures\":[\"http://img.zcool.cn/community/014608582ae13ea84a0e282b0b2099.jpg@1280w_1l_2o_100sh.jpg\",\"http://img2.imgtn.bdimg.com/it/u=1057953037,1138962619&fm=27&gp=0.jpg\",\"http://pic23.nipic.com/20120826/10209170_183108669000_2.jpg\",\"http://pic4.nipic.com/20090901/515807_125042019142_2.jpg\"],\"theme\":\"http://img06.tooopen.com/images/20170720/tooopen_sy_217426757833.jpg\"}",
        "name": "拼图游戏规则",
        "status": "1",
        "constantKey": "jigsaw_game_rule"
    }
}
```

<a name="19">修改拼图,口令游戏规则设置</a>
### 修改拼图游戏规则设置

```
post /wechatgateway/constantcode/save
```
参数 json
``` 
    {
        "constantCodeId": "7",
        "type": "time_limit_game_rule",
        "value": "{\"intervals\":[60,90,120,150,180],\"pictures\":[\"http://img.zcool.cn/community/014608582ae13ea84a0e282b0b2099.jpg@1280w_1l_2o_100sh.jpg\",\"http://img2.imgtn.bdimg.com/it/u=1057953037,1138962619&fm=27&gp=0.jpg\",\"http://pic23.nipic.com/20120826/10209170_183108669000_2.jpg\",\"http://pic4.nipic.com/20090901/515807_125042019142_2.jpg\"],\"theme\":\"http://img06.tooopen.com/images/20170720/tooopen_sy_217426757833.jpg\"}",
        "name": "拼图游戏规则",
        "status": "1",
        "constantKey": "jigsaw_game_rule"
    }
```

返回
``` 
{
    "code": "0",
    "message": "成功",
    "data": {
        "constantCodeId": "7",
        "type": "time_limit_game_rule",
        "value": "{\"intervals\":[60,90,120,150,180],\"pictures\":[\"http://img.zcool.cn/community/014608582ae13ea84a0e282b0b2099.jpg@1280w_1l_2o_100sh.jpg\",\"http://img2.imgtn.bdimg.com/it/u=1057953037,1138962619&fm=27&gp=0.jpg\",\"http://pic23.nipic.com/20120826/10209170_183108669000_2.jpg\",\"http://pic4.nipic.com/20090901/515807_125042019142_2.jpg\"],\"theme\":\"http://img06.tooopen.com/images/20170720/tooopen_sy_217426757833.jpg\"}",
        "name": "拼图游戏规则",
        "status": "1",
        "constantKey": "jigsaw_game_rule"
    }
}
```

<a name="20">查询限时活动时间配置</a>
### 查询限时活动时间配置

```
post /wechatgateway/constantcode/selectGameBaseConfig
```
参数 无
``` 
    
```

返回
``` 
{
    "code": "0",
    "message": "成功",
    "data": {
        "constantCodeId": "8",
        "type": "game_base_config",
        "value": "{\"preGameWait\":60,\"QRWait\":120,\"gameWait\":240,\"resultWait\":60,\"afterGameWait\":120,\"jigsawGameWait\":250,\"game_ background\":\"https://img.wangxiaobao.cc/%E5%8F%A3%E4%BB%A4%E6%B8%B8%E6%88%8F.png\"}",
        "name": "口令游戏基础配置",
        "status": "1",
        "constantKey": "3"
    }
}
```

<a name="21">更新限时活动时间配置</a>
### 更新限时活动时间配置

```
post /wechatgateway/constantcode/updateGameBaseConfig
```
参数 json
``` 
  {
        "constantCodeId": "8",
        "type": "game_base_config",
        "value": "{\"preGameWait\":60,\"QRWait\":120,\"gameWait\":240,\"resultWait\":60,\"afterGameWait\":120,\"jigsawGameWait\":250,\"game_ background\":\"https://img.wangxiaobao.cc/%E5%8F%A3%E4%BB%A4%E6%B8%B8%E6%88%8F.png\"}",
        "name": "口令游戏基础配置",
        "status": "1",
        "constantKey": "3"
    }  
```

返回
``` 
{
    "code": "0",
    "message": "成功",
    "data": 
}
```

<a name="22">平台小程序列表查询</a>
### 平台小程序列表查询

```
post /wechatgateway/platformXiaoCXManage/selectByCondition
```
参数 json
``` 
  {
	"page":1,  必填
	"size":10, 必填 
	"name":"123"  选填
}
```

返回
``` 
{
    "code": "0",
    "message": "成功",
    "data": {
        "content": [
            {
                "appId": "wx55804dbb87a85288",
                "appSecret": "e6d19d53683c06a6477e82e324653184",
                "topLimit": "0",
                "isValidate": "1",
                "type": "1",
                "createDate": 1516266183000,
                "updateDate": 1521423104000,
                "code": 129,
                "name": "123"
            }
        ],
        "last": true,
        "totalPages": 1,
        "totalElements": 1,
        "size": 10,
        "number": 0,
        "sort": [
            {
                "direction": "DESC",
                "property": "createDate",
                "ignoreCase": false,
                "nullHandling": "NATIVE",
                "ascending": false,
                "descending": true
            }
        ],
        "first": true,
        "numberOfElements": 1
    }
}
```

<a name="23">新增或编辑平台小程序</a>
### 新增或编辑平台小程序

```
post /wechatgateway/platformXiaoCXManage/save
```
参数 json
``` 
  {
	"appId":1,  必填
	"appSecret":10, 必填 
	"name":"name",  必填
	"type":"1",    必填
	"code":"123"   选填
}
```

返回
``` 
{
    "code": "0",
    "message": "成功",
    "data":
}
```

<a name="24">商家公众号/小程序授权列表</a>
### 商家公众号授权列表

```
post /wechatgateway/gongzhonghao/selectGongzhonghaoList
```
参数 json
``` 
  {
	"page":1,  必填
	"size":10, 必填 
	"brandName":"清粥小菜",  选填
	"authType":"1"      (1:公众号；2：小程序)
}
```

返回
``` 
{
    "code": "0",
    "message": "成功",
    "data": {
        "count": 0,
        "pageNum": 1,
        "pageSize": 10,
        "datas": [
            {
                "wxOpenPlatformId": "9dde98406c82423292d2802e9ce51b8e",
                "wxAppid": "wxda9d306d97c24c8a",
                "createDate": 1516346572000,
                "authType": "1",
                "organizationAccount": "WCBBJ",
                "nickName": "旺小宝CRM",
                "brandName": "旺小宝测试",
                "openPlatformName": "旺小宝桌牌",
                "status": "已授权"
            },
            {
                "wxOpenPlatformId": "f5821bd4bd2f4a45b70946c5c1aeba33",
                "wxAppid": "wx92be428ecdf227ab",
                "createDate": 1516593706000,
                "authType": "1",
                "organizationAccount": "ygpp",
                "nickName": "旺小宝会员",
                "brandName": "养个啪啪",
                "openPlatformName": "旺小宝桌牌",
                "status": "已授权"
            },
            {
                "organizationAccount": "liping1",
                "brandName": "李平1",
                "openPlatformName": "旺小宝桌牌",
                "status": "未授权"
            }
        ]
    }
}
```

<a name="25">商家公众号发起授权</a>
### 商家公众号发起授权

```
get /wechatgateway/index/wx/getAuthUrl
```
参数 无
``` 
  
```
返回
``` 
	跳转到微信授权页面
```

<a name="26">查询商家公众号/小程序基本信息</a>
### 查询商家公众号基本信息

```
get /wechatgateway/gongzhonghao/getWXopenPlatformMerchantInfo
```
参数 key-value
``` 
  wxAppId 公众号appid  必填
```
返回
``` 
	{
    "code": "0", 
    "message": "成功", 
    "data": {
        "wxOpenPlatformId": "b847610d104a43fcb5239f9b02c21484", 
        "wxAppid": "wx38968167ae9030cc", 
        "authoriceAccessToken": "8_UgWsdMlBEHLKpCkqPEOKSJvsFKX8-hW8dJZY3c3gylCJBRQ50_G4PioL0hDBQNHDuSfSdzRPVHNgCCRGmzHPFdZpGtLYZ56pbdQLMZB6Ji0J-dYbilMceI-GOf-IxuDkxEfwsdzRqZBf8ngULIRaAJDCZI", 
        "authoriceRefreshToken": "refreshtoken@@@LgRB3t04VeYpCSny7A3GuQ7uGGD4srL1fMu1GwfyVLA", 
        "createUser": "createUser", 
        "createDate": 1522377549000, 
        "updateUser": "updateUser", 
        "updateDate": 1522635019000, 
        "openAppid": "wx98aa51a56286ed49", 
        "authType": "1", 
        "organizationAccount": "BJAYPTSYDJT_GROUP", 
        "nickName": "旺小宝同城", 
        "headImg": "http://wx.qlogo.cn/mmopen/qf9UScA0OLe68y3WCmSesKoj5nG4EWsjp2n7ZM9106u0SEk3LgIqUPY3XtsgZicLsuaNTfGes2OwP34Z7ZTy0vzY1kZ8oiajrP/0", 
        "verifyTypeInfo": "{\"id\":0}", 
        "userName": "gh_14bc2c38e83f"
    }
}
```


<a name="27">刷新公众号/小程序调用凭证</a>
### 刷新公众号调用凭证

```
get /wechatgateway/gongzhonghao/refreshAuthInfo
```
参数 key-value
``` 
  wxAppId 公众号appid  必填
```
返回
``` 
	{
    "code": "0", 
    "message": "成功", 
    "data": {
        "wxOpenPlatformId": "b847610d104a43fcb5239f9b02c21484", 
        "wxAppid": "wx38968167ae9030cc", 
        "authoriceAccessToken": "8_hQa3Xz4sW8zWRycae6-JzAU_HarVPbgePT1QN6G54ak9bqQFd9feegunyZ4kg22bilA-WlgB2LUnNdnZ5d2rEw4cW2PW1kd1U1TIjIk_mp18Ks3b1ZI9GFPHdNH61QtdGhLLXc_nIHTajxplQKHiALDFZW", 
        "authoriceRefreshToken": "refreshtoken@@@LgRB3t04VeYpCSny7A3GuQ7uGGD4srL1fMu1GwfyVLA", 
        "createUser": "createUser", 
        "createDate": 1522377549000, 
        "updateUser": "updateUser", 
        "updateDate": 1522736176000, 
        "openAppid": "wx98aa51a56286ed49", 
        "authType": "1", 
        "organizationAccount": "BJAYPTSYDJT_GROUP", 
        "nickName": "旺小宝同城", 
        "headImg": "http://wx.qlogo.cn/mmopen/qf9UScA0OLe68y3WCmSesKoj5nG4EWsjp2n7ZM9106u0SEk3LgIqUPY3XtsgZicLsuaNTfGes2OwP34Z7ZTy0vzY1kZ8oiajrP/0", 
        "verifyTypeInfo": "{\"id\":0}", 
        "userName": "gh_14bc2c38e83f"
    }
}
```



<a name="28">查询公众号/小程序绑定的开放平台</a>
### 查询公众号绑定的开放平台

```
get /wechatgateway/gongzhonghao/getBindOpen
```
参数 key-value
``` 
  wxAppid appid  必填
```
返回
``` 
	{
    "code": "0", 
    "message": "成功", 
    "data": {
        "errcode": 0, 
        "open_appid": "wx98aa51a56286ed49", 
        "errmsg": "ok"
    }
}
```


<a name="29">解绑公众号绑定的开放平台</a>
### 解绑公众号绑定的开放平台

```
get /wechatgateway/gongzhonghao/unbindOpen
```
参数 json
``` 
  {
  	"authType":"1",    授权类型（1：公众号；2:小程序）   必填   
  	"wxAppid":"",	   appid					   必填
  	"openAppid":""	   开放平台appid				   选填	
  }
```
返回
``` 
	{
    "code": "0", 
    "message": "成功", 
    "data": 
}
```



<a name="30">获取公众号关联的小程序</a>
### 获取公众号关联的小程序

```
get /wechatgateway/gongzhonghao/wxamplinkget
```
参数 wxAppid
``` 
  	wxAppid	   公众号appid			 必填
```
返回
``` 
	{
    "code": "0", 
    "message": "成功", 
    "data": "{\"errcode\":0,\"errmsg\":\"ok\",\"wxopens\":{\"items\":[{\"nickname\":\"旺小宝会员\",\"headimg_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_png\\/7GwXNMx62jTAAomRf3Xpn6ibxm4r3cNP9VRJTdBaOmDyxFoJXePE6C3T5SiakbvlybKYJapdBVTibmCAicVPglPmibA\\/0?wx_fmt=png\",\"status\":1,\"source\":\"SOURCE_WXSTORE\",\"func_infos\":[{\"id\":1,\"status\":0,\"name\":\"微信认证\"},{\"id\":2,\"status\":0,\"name\":\"微信支付\"}],\"selected\":0,\"username\":\"gh_a5b4e20534b0\",\"email\":\"\",\"nearby_display_status\":0,\"copy_verify_status\":0,\"released\":1},{\"nickname\":\"国盛桌牌\",\"headimg_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_png\\/WLb6lOSMmLFXs4tXdIQZDQX7AVAL0Ndw4wficcnKvcANSa0VpWKhBTeJEPdnGia9hyobgE5f69FEDJGjzCf5T8eg\\/0?wx_fmt=png\",\"status\":1,\"source\":\"SOURCE_NORMAL\",\"func_infos\":[{\"id\":1,\"status\":1,\"name\":\"微信认证\"},{\"id\":2,\"status\":0,\"name\":\"微信支付\"}],\"selected\":0,\"username\":\"gh_18abf6623b12\",\"email\":\"shenwei@guoshengtianfeng.com\",\"nearby_display_status\":0,\"copy_verify_status\":3,\"released\":0},{\"nickname\":\"点餐指南\",\"headimg_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_png\\/rVpAJceTEDHe8LHuOnY9bv2ysCpGlibibISRQf4y0WgTnYephpcWN0ial2FJAfpPfxgq4MVBumvZiaicVRIY0Ga7Azg\\/0?wx_fmt=png\",\"status\":1,\"source\":\"SOURCE_FASTREGISTER\",\"func_infos\":[{\"id\":1,\"status\":1,\"name\":\"微信认证\"},{\"id\":2,\"status\":0,\"name\":\"微信支付\"}],\"selected\":0,\"username\":\"gh_7912b98d9a50\",\"email\":\"vaio292@gmail.com\",\"nearby_display_status\":0,\"copy_verify_status\":0,\"released\":0},{\"nickname\":\"旺小宝互动\",\"headimg_url\":\"http:\\/\\/mmbiz.qpic.cn\\/mmbiz_png\\/cCgpcLIucaEicUSSXqJL9kkBbJVqBiaQgRSV3K6eOreFfzTqaOibnDc0L3WXcDhOYicYs07E7UeZk6pPYH2VCvkDdg\\/0?wx_fmt=png\",\"status\":4,\"source\":\"SOURCE_COMPONENT_API\",\"func_infos\":[]}]}}"
}
```



<a name="31">公众号取消小程序关联</a>
### 公众号取消小程序关联

```
get /wechatgateway/gongzhonghao/unbindWxampunlink
```
参数 wxAppid
``` 
  	wxAppid	   公众号appid			 必填
  	xcxAppid   小程序appid            必填
```
返回
``` 
	{
    "code": "0", 
    "message": "成功", 
    "data": ""
}
```



<a name="32">公众号关联小程序</a>
### 公众号关联小程序

```
get /wechatgateway/gongzhonghao/bindWxamplink
```
参数 wxAppid
``` 
  	wxAppid	   公众号appid			 必填
  	xcxAppid   小程序appid            必填
```
返回
``` 
	{
    "code": "0", 
    "message": "成功", 
    "data": ""
}
```


<a name="33">获取小程序类目</a>
### 公众号关联小程序

```
get /wechatgateway/miniprogram/getcategory
```
参数 
``` 
  	wxAppid	   appid			 必填
  	
```
返回
``` 
	{
    "code": "0", 
    "message": "成功", 
    "data": {
        "errcode": 0, 
        "category_list": [
            {
                "first_class": "餐饮", 
                "second_class": "点评与推荐", 
                "first_id": 220, 
                "second_id": 221
            }, 
            {
                "first_class": "餐饮", 
                "second_class": "菜谱", 
                "first_id": 220, 
                "second_id": 225
            }
        ], 
        "errmsg": "ok"
    }
}

```




<a name="34">查询小程序模板</a>
### 查询小程序模板

```
post /wechatgateway/wxMinprogramTemplate/selectList
```
参数 json
``` 
  	type	   "1" 固定值			 必填
  	
```
返回
``` 
	{
    "code": "0", 
    "message": "成功", 
    "data": [
        {
            "templateId": "68", 
            "draftId": "0", 
            "userVersion": "V0.0.1", 
            "userDesc": "第四版", 
            "wxCreateTime": 1516070846000, 
            "type": "1", 
            "isDefault": "0", 
            "createDate": 1516157254000, 
            "updateDate": 1519869760000
        }, 
        {
            "templateId": "54", 
            "draftId": "1", 
            "userVersion": "V0.0.2", 
            "userDesc": "第二版", 
            "wxCreateTime": 1519809446000, 
            "type": "1", 
            "isDefault": "0", 
            "createDate": 1519809457000, 
            "updateDate": 1519809462000
        }, 
        {
            "templateId": "67", 
            "draftId": "2", 
            "userVersion": "V0.0.2", 
            "userDesc": "第三版", 
            "wxCreateTime": 1519809483000, 
            "type": "1", 
            "isDefault": "0", 
            "createDate": 1519809490000, 
            "updateDate": 1519809499000
        }, 
        {
            "templateId": "3", 
            "draftId": "3", 
            "userVersion": "v0.0.3", 
            "userDesc": "第四版", 
            "wxCreateTime": 1519869748000, 
            "type": "1", 
            "isDefault": "0", 
            "createDate": 1519869761000, 
            "updateDate": 1520241219000
        }, 
        {
            "templateId": "4", 
            "draftId": "4", 
            "userVersion": "V0.0.4", 
            "userDesc": "第五版", 
            "wxCreateTime": 1520241210000, 
            "type": "1", 
            "isDefault": "0", 
            "createDate": 1520241213000, 
            "updateDate": 1520301272000
        }, 
        {
            "templateId": "5", 
            "draftId": "5", 
            "userVersion": "V0.0.5", 
            "userDesc": "第6版", 
            "wxCreateTime": 1520301262000, 
            "type": "1", 
            "isDefault": "0", 
            "createDate": 1520301273000, 
            "updateDate": 1520503065000
        }, 
        {
            "templateId": "6", 
            "draftId": "6", 
            "userVersion": "V0.0.6", 
            "userDesc": "第七版", 
            "wxCreateTime": 1520503055000, 
            "type": "1", 
            "isDefault": "0", 
            "createDate": 1520503067000, 
            "updateDate": 1520565668000
        }, 
        {
            "templateId": "7", 
            "draftId": "7", 
            "userVersion": "V0.0.7", 
            "userDesc": "第八版", 
            "wxCreateTime": 1520564123000, 
            "type": "1", 
            "isDefault": "0", 
            "createDate": 1520564130000, 
            "updateDate": 1520565671000
        }, 
        {
            "templateId": "8", 
            "draftId": "8", 
            "userVersion": "V0.0.7", 
            "userDesc": "最新版", 
            "wxCreateTime": 1520565658000, 
            "type": "1", 
            "isDefault": "0", 
            "createDate": 1520565675000, 
            "updateDate": 1520833882000
        }, 
        {
            "templateId": "9", 
            "draftId": "9", 
            "userVersion": "V0.0.9", 
            "userDesc": "第九版", 
            "wxCreateTime": 1522115150000, 
            "type": "1", 
            "isDefault": "0", 
            "createDate": 1522115093000, 
            "updateDate": 1522115153000
        }, 
        {
            "templateId": "10", 
            "draftId": "10", 
            "userVersion": "V0.1.0", 
            "userDesc": "第十版", 
            "wxCreateTime": 1522115155000, 
            "type": "1", 
            "isDefault": "0", 
            "createDate": 1522115100000, 
            "updateDate": 1522115157000
        }, 
        {
            "templateId": "11", 
            "draftId": "11", 
            "userVersion": "V0.1.1", 
            "userDesc": "第十一版", 
            "wxCreateTime": 1522115159000, 
            "type": "1", 
            "isDefault": "0", 
            "createDate": 1522115106000, 
            "updateDate": 1522115161000
        }, 
        {
            "templateId": "13", 
            "draftId": "13", 
            "userVersion": "V0.1.3", 
            "userDesc": "第十一版", 
            "wxCreateTime": 1522115163000, 
            "type": "1", 
            "isDefault": "0", 
            "createDate": 1522115231000, 
            "updateDate": 1522115234000
        }, 
        {
            "templateId": "14", 
            "draftId": "14", 
            "userVersion": "V0.1.4", 
            "userDesc": "第十一版", 
            "wxCreateTime": 1521798489000, 
            "type": "1", 
            "isDefault": "0", 
            "createDate": 1521798493000, 
            "updateDate": 1522289719000
        }, 
        {
            "templateId": "15", 
            "draftId": "15", 
            "userVersion": "V0.1.5", 
            "userDesc": "第十五版", 
            "wxCreateTime": 1522289708000, 
            "type": "1", 
            "isDefault": "1", 
            "createDate": 1522289724000, 
            "updateDate": 1522289720000
        }
    ]
}

```



<a name="35">上传小程序模板</a>
### 上传小程序模板

```
post /wechatgateway/miniprogram/commit
```
参数 key-value
``` 
  	templateId	   模板id		 必填
  	wxAppid        小程序appid    必填
  	organizationAccount  品牌account  必填
  	
```
返回
``` 
	{"code":"0","message":"成功","data":{"errcode":0,"errmsg":"ok"}}

```


<a name="36">获取效验文件</a>
### 获取效验文件

```
get /wechatgateway/qrcode/findQrcodeFile
```
参数 key-value
``` 
  	wxAppId        小程序appid    必填
  	
```
返回
``` 
	{"code":"0","message":"成功","data":"2e55135cf4adaa89dce61c2cb3acb20f"}

```



<a name="37">创建效验文件</a>
### 创建效验文件

```
get /wechatgateway/miniprogramqrcode/qrcodejumpdownload
```
参数 key-value
``` 
  	wxAppid        小程序appid    必填
  	
```
返回
``` 
	{"code":"0","message":"成功","data":"2e55135cf4adaa89dce61c2cb3acb20f"}

```

<a name="38">获取小程序当前版本</a>
### 获取小程序当前版本

```
post /wechatgateway/organizationTemplate/selectCurrentTemplate
```
参数 json
``` 
  	wxAppId        小程序appid    必填
  	
```
返回
``` 
{
    "code": "0", 
    "message": "成功", 
    "data": [
        {
            "miniprogramTemplateId": "1522379592996684573", 
            "wxAppId": "wx7a6dadfed46e6db8", 
            "organizationAccount": "BJAYPTSYDJT_GROUP", 
            "templateId": "15", 
            "createDate": 1522379593000, 
            "updateDate": 1522389455000, 
            "status": "2", 
            "isOnline": "0", 
            "isNew": "0", 
            "auditid": "446520568"
        }, 
        {
            "miniprogramTemplateId": "1522389455249410873", 
            "wxAppId": "wx7a6dadfed46e6db8", 
            "organizationAccount": "BJAYPTSYDJT_GROUP", 
            "templateId": "5", 
            "createDate": 1522389455000, 
            "updateDate": 1522402522000, 
            "status": "2", 
            "isOnline": "0", 
            "isNew": "0"
        }, 
        {
            "miniprogramTemplateId": "1522389978433354915", 
            "wxAppId": "wx7a6dadfed46e6db8", 
            "organizationAccount": "BJAYPTSYDJT_GROUP", 
            "templateId": "6", 
            "createDate": 1522389978000, 
            "updateDate": 1522401542000, 
            "status": "-1", 
            "isOnline": "0", 
            "isNew": "0"
        }, 
        {
            "miniprogramTemplateId": "1522402522028217634", 
            "wxAppId": "wx7a6dadfed46e6db8", 
            "organizationAccount": "BJAYPTSYDJT_GROUP", 
            "templateId": "15", 
            "createDate": 1522402522000, 
            "updateDate": 1522721866000, 
            "status": "2", 
            "isOnline": "0", 
            "isNew": "0"
        }, 
        {
            "miniprogramTemplateId": "1522721866498920628", 
            "wxAppId": "wx7a6dadfed46e6db8", 
            "organizationAccount": "BJAYPTSYDJT_GROUP", 
            "templateId": "5", 
            "createDate": 1522721866000, 
            "updateDate": 1522721869000, 
            "status": "1", 
            "isOnline": "0", 
            "isNew": "0"
        }, 
        {
            "miniprogramTemplateId": "1522721869604239016", 
            "wxAppId": "wx7a6dadfed46e6db8", 
            "organizationAccount": "BJAYPTSYDJT_GROUP", 
            "templateId": "6", 
            "createDate": 1522721870000, 
            "updateDate": 1522739113000, 
            "status": "1", 
            "isOnline": "0", 
            "isNew": "0"
        }, 
        {
            "miniprogramTemplateId": "1522739113084514103", 
            "wxAppId": "wx7a6dadfed46e6db8", 
            "organizationAccount": "BJAYPTSYDJT_GROUP", 
            "templateId": "3", 
            "createDate": 1522739113000, 
            "status": "1", 
            "isOnline": "0", 
            "isNew": "1"
        }
    ]
}

```




<a name="39">提交商家小程序列表</a>
### 获取小程序当前版本

```
post /wechatgateway/organizationTemplate/selectOrganizeTemplateList

```
参数 json
``` 
  	{
    "page": 2, 
    "size": 10, 
    "orgName": ""
}
  	
```
返回
``` 
{
    "code": "0", 
    "message": "成功", 
    "data": {
        "count": 11, 
        "pageNum": 2, 
        "pageSize": 10, 
        "datas": [
            {
                "miniprogramTemplateId": "1520235234482886318", 
                "wxAppId": "wx9282e5fd404b09d4", 
                "organizationAccount": "halley1", 
                "templateId": "3", 
                "createDate": 1520235234000, 
                "updateDate": 1521798694000, 
                "status": "3", 
                "reason": null, 
                "auditid": "429058440", 
                "nickName": "onlywww", 
                "orgName": "Halley"
            }, 
            {
                "miniprogramTemplateId": "1522031347971340667", 
                "wxAppId": "wx9282e5fd404b09d4", 
                "organizationAccount": "halley1", 
                "templateId": "14", 
                "createDate": 1522031348000, 
                "updateDate": 1522306185000, 
                "status": "3", 
                "reason": null, 
                "auditid": "429338933", 
                "nickName": "onlywww", 
                "orgName": "Halley"
            }, 
            {
                "miniprogramTemplateId": "1522306185256615834", 
                "wxAppId": "wx9282e5fd404b09d4", 
                "organizationAccount": "halley1", 
                "templateId": "15", 
                "createDate": 1522306185000, 
                "updateDate": 1522395208000, 
                "status": "1", 
                "reason": null, 
                "auditid": null, 
                "nickName": "onlywww", 
                "orgName": "Halley"
            }, 
            {
                "miniprogramTemplateId": "1521798693872513500", 
                "wxAppId": "wx379dac703ad213f5", 
                "organizationAccount": "halley1", 
                "templateId": "14", 
                "createDate": 1521798694000, 
                "updateDate": 1522401661000, 
                "status": "3", 
                "reason": null, 
                "auditid": "439651488", 
                "nickName": "旺小宝演示", 
                "orgName": "Halley"
            }, 
            {
                "miniprogramTemplateId": "1522379592996684573", 
                "wxAppId": "wx7a6dadfed46e6db8", 
                "organizationAccount": "BJAYPTSYDJT_GROUP", 
                "templateId": "15", 
                "createDate": 1522379593000, 
                "updateDate": 1522389455000, 
                "status": "2", 
                "reason": null, 
                "auditid": "446520568", 
                "nickName": "李平商家测试", 
                "orgName": null
            }, 
            {
                "miniprogramTemplateId": "1522389455249410873", 
                "wxAppId": "wx7a6dadfed46e6db8", 
                "organizationAccount": "BJAYPTSYDJT_GROUP", 
                "templateId": "5", 
                "createDate": 1522389455000, 
                "updateDate": 1522402522000, 
                "status": "2", 
                "reason": null, 
                "auditid": null, 
                "nickName": "李平商家测试", 
                "orgName": null
            }, 
            {
                "miniprogramTemplateId": "1522389978433354915", 
                "wxAppId": "wx7a6dadfed46e6db8", 
                "organizationAccount": "BJAYPTSYDJT_GROUP", 
                "templateId": "6", 
                "createDate": 1522389978000, 
                "updateDate": 1522401542000, 
                "status": "-1", 
                "reason": null, 
                "auditid": null, 
                "nickName": "李平商家测试", 
                "orgName": null
            }, 
            {
                "miniprogramTemplateId": "1522402522028217634", 
                "wxAppId": "wx7a6dadfed46e6db8", 
                "organizationAccount": "BJAYPTSYDJT_GROUP", 
                "templateId": "15", 
                "createDate": 1522402522000, 
                "updateDate": 1522721866000, 
                "status": "2", 
                "reason": null, 
                "auditid": null, 
                "nickName": "李平商家测试", 
                "orgName": null
            }, 
            {
                "miniprogramTemplateId": "1522721866498920628", 
                "wxAppId": "wx7a6dadfed46e6db8", 
                "organizationAccount": "BJAYPTSYDJT_GROUP", 
                "templateId": "5", 
                "createDate": 1522721866000, 
                "updateDate": 1522721869000, 
                "status": "1", 
                "reason": null, 
                "auditid": null, 
                "nickName": "李平商家测试", 
                "orgName": null
            }, 
            {
                "miniprogramTemplateId": "1522721869604239016", 
                "wxAppId": "wx7a6dadfed46e6db8", 
                "organizationAccount": "BJAYPTSYDJT_GROUP", 
                "templateId": "6", 
                "createDate": 1522721870000, 
                "updateDate": 1522739113000, 
                "status": "1", 
                "reason": null, 
                "auditid": null, 
                "nickName": "李平商家测试", 
                "orgName": null
            }, 
            {
                "miniprogramTemplateId": "1522739113084514103", 
                "wxAppId": "wx7a6dadfed46e6db8", 
                "organizationAccount": "BJAYPTSYDJT_GROUP", 
                "templateId": "3", 
                "createDate": 1522739113000, 
                "updateDate": null, 
                "status": "1", 
                "reason": null, 
                "auditid": null, 
                "nickName": "李平商家测试", 
                "orgName": null
            }
        ]
    }
}

```


<a name="40">取消审核</a>
### 取消审核

```
get /wechatgateway/miniprogram/undocodeaudit

```
参数 key-value
``` 
  	wxAppid      小程序appid
  	
```
返回
``` 
{
    "code": "0", 
    "message": "成功", 
    "data": 
}

```





<a name="41">提交审核</a>
### 提交审核

```
get /wechatgateway/miniprogram/submitaudit

```
参数 key-value
``` 
  	wxAppid      小程序appid
  	miniprogramTemplateId     模板id主键
  	
```
返回
``` 
{
    "code": "0", 
    "message": "成功", 
    "data": 
}

```



<a name="42">刷新审核状态</a>
### 刷新审核状态

```
get /wechatgateway/organizationTemplate/getAuditstatus

```
参数 key-value
``` 
  	auditid      审核id      必填
  	wxAppid      小程序id    必填
  	
```
返回
``` 
{
    "code": "0", 
    "message": "成功", 
    "data": 
}

```



<a name="43">发布商户小程序列表</a>
### 发布商户小程序列表

```
get 同提交商户小程序列表

```
参数 
``` 
  	
  	
```
返回
``` 

```



<a name="44">发布小程序代码</a>
### 发布小程序代码

```
get /wechatgateway/organizationTemplate/release

```
参数 key-value
``` 
  	wxAppid     小程序appid
  	
```
返回
``` 

```

- <a href="#saveqrcodeverify">保存平台小程序二维码校验文件</a>
```
POST /wechatgateway/qrcode/saveQrcodeFile

参数说明：
wxAppid //appid
fileName //文件名称
fileContent //文件内容


返回:
{
    "code": "0",
    "message": "成功",
    "data": {
        "wxAppid": "1111111",
        "fileName": "434343122211",
        "fileContent": "rtrtrtert112221",
        "createDate": 1523416353000
    }
}
```

- <a href="#findqrcodeverify">查询平台小程序二维码校验文件</a>
```
GET /wechatgateway/qrcode/findQrcodeFile

参数说明：
wxAppid //appid
返回:
{
    "code": "0",
    "message": "成功",
    "data": "rtrtrtert112221"
}
```