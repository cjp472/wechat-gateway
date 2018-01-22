# API
## 品牌管理门店
- <a href="#1">品牌创建门店</a>  

- <a href="#2">品牌删除门店</a>

- <a href="#3">品牌查询门店</a>

- <a href="#9">保存品牌信息</a>

- <a href="#8">查询品牌信息</a>


## 商家管理门店
- <a href="#4">商家创建门店</a>

- <a href="#5">商家查询门店</a>

- <a href="#6">保存门店菜品图片</a>

- <a href="#7">保存门店菜品图片</a>

## 商家广告
- <a href="#11">保存广告</a>

- <a href="#12">通过广告ID获取广告</a>

- <a href="#13">通过商家账号获取广告列表</a>

- <a href="#14">保存广告图片</a>

- <a href="#15">通过广告ID获取图片</a>

- <a href="#16">通过图片ID删除图片</a>

## 小程序黄页获取集团
- <a href="#10">获取用户与门店距离</a>



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
merchantAccount:XHZHYD
storeMenu: 移动端自定义格式

```

<a name="7">保存门店菜品图片</a>
### 保存门店图片
``` 
POST /wechatgateway/storeinfo/saveStorePhoto

参数
merchantAccount:XHZHYD
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
                "distance": "4850"
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