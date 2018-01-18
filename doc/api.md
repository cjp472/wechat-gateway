# API
### 查询门店

```
GET /wechatgateway/storeinfo/findByMerchantAccount?merchantAccount=XHZHYD
```
参数

```
无
```

返回
``` 
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

### 创建门店
``` 
POST /wechatgateway/storeinfo/save
```

参数
``` 
merchantAccount:XHZHYD
merchantId:123456
storeProvince:四川
storeCity:成都
storeDistrict:华阳
storeAddress:车各庄1234
storeDescription:好吃
storePhone:88888888
storeOfficehours:10-22
haveWifi:1
storeName:销魂掌华阳店
```

返回
``` 
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
        "storeDescription": "好吃",
        "storePhone": "88888888",
        "storeLocation": "104.062026,30.505954",
        "storeOfficehours": "10-22",
        "haveParking": 0,
        "haveWifi": 1,
        "havePrivateroom": 0,
        "haveBooking": 0,
        "storeLogo": "{http://www.baidu.com123}",
        "storeMenu": "{http://www.baidu.com123}",
        "storePhoto": "{http://www.baidu.com321}",
        "createTime": 1515933666000,
        "updateTime": 1515933666000
    }
}
```

### 保存门店菜品图片
```
POST /wechatgateway/storeinfo/saveStoreMenu 
```
参数
``` 
merchantAccount:XHZHYD
storeMenu: 移动端自定义格式
```

返回 略

### 保存门店图片
``` 
POST /wechatgateway/storeinfo/saveStorePhoto
```
参数
``` 
merchantAccount:XHZHYD
storePhoto: 移动端自定义格式
```

返回 略

### 查询品牌
``` 
GET /wechatgateway/brandinfo/findByOrgId?orgId=12345
或
GET /wechatgateway/brandinfo/findByAccount?orgAccount=XHZ
```

返回
``` 
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

### 保存品牌信息
``` 
POST /wechatgateway/brandinfo/save
```

参数
``` 
orgId:12345
orgAccount:XHZ
logoUrl:https://timgsa.baidu.com/timg?
```

参数存在 brandId 会执行修改

返回
``` 
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

### 获取用户与门店距离
``` 
GET /wechatgateway/storeinfo/getDistance?longitude=104.068359&latitude=30.538196
```

参数
``` 
longitude:104.068359
latitude:30.538196
```

返回
``` 
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

