# code-cooperation
CodeCooperation


# 接口文档

## 注：线上访问地址：  123.57.251.186:8888

### 共通参数(header)
+ 注：发送验证码以及登陆注册接口可以要求不传共通参数，其他接口都需要有。

|字段名字|描述|类型|是否必须|值(举例)|
|---|---|---|---|---|
|t|用户token|String|no|asidj9123ja|


#### 1.发送验证码

+ 接口：/api/v1/send/code
+ 方法：GET
+ 参数：如下
|字段名字|描述|类型|是否必须|值(举例)|
|emlAddr|邮箱地址|String|yes|18138227923@qq.com|

+ 结果：
```
{
    "code": 0,
    "msg": "success",
    "data": null
}
```




#### 2.1注册

+ 接口：/api/v1/regist
+ 方法：POST
+ 参数：如下

|字段名字|描述|类型|是否必须|值(举例)|
|---|---|---|---|---|
|emlAddr|邮箱地址|String|yes|13739926022@sina.cn|
|code|验证码|String|yes|123456|
|passwd|密码|String|yes|123456|

+ 结果：
```
{
    "code": 0,
    "msg": "success",
    "data": {
        "id": 1,
        "emlAddr": "13739926022@sina.cn",
        "passwd": "123456",
        "token": "Ofr6Payqm0xu6umd",
        "name": "用户xAtx",
        "favorites": null,
        "createTime": 1586229795534,
        "updateTime": 0
    }
}

```



#### 2.2登录

+ 接口：/api/v1/login
+ 方法：POST
+ 参数：如下

|字段名字|描述|类型|是否必须|值(举例)|
|---|---|---|---|---|
|emlAddr|邮箱地址|String|yes|13739926022@sina.cn|
|passwd|密码|String|yes|123456|

+ 结果：
```
{
    "code": 0,
    "msg": "success",
    "data": {
        "id": 1,
        "emlAddr": "13739926022@sina.cn",
        "passwd": "123456",
        "token": "Ofr6Payqm0xu6umd",
        "name": "用户xAtx",
        "favorites": null,
        "createTime": 1586229795534,
        "updateTime": 0
    }
}

```