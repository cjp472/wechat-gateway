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
        "storeName": "销魂掌华阳店",
        "storeProvince": "四川",
        "storeCity": "成都",
        "storeDistrict": "华阳",
        "storeAddress": "车各庄1234",
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
        "storeName": "销魂掌华阳店",
        "storeProvince": "四川",
        "storeCity": "成都",
        "storeDistrict": "华阳",
        "storeAddress": "车各庄1234",
        "storeDescription": "好吃",
        "storePhone": "88888888",
        "storeOfficehours": "10-22",
        "haveParking": 0,
        "haveWifi": 1,
        "havePrivateroom": 0,
        "haveBooking": 0,
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