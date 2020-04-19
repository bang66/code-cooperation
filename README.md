# code-cooperation
CodeCooperation


# 接口文档

## 注：线上访问地址：  123.57.251.186:8888

### 共通参数(header)
+ 注：发送验证码以及登陆注册接口可以要求不传共通参数，其他接口都需要有。

|字段名字|描述|类型|是否必须|值(举例)|
|---|---|---|---|---|
|t|用户token|String|no|asidj9123ja|



### 异常码

+ 以下为系统错误

|异常码|描述|含义|
|---|---|---|
|1000|prams error|入参参数错误|
|1001|system error|服务器异常|
|1002|illegal request|非法请求(即无token，前端需跳至登录)|
|1003|data missing|查询数据不存在|


+ 以下为业务错误

|异常码|描述|含义|
|---|---|---|
|2000|account error|账号有误(登录错误)|
|2001|account not regist|账号未注册(前端需跳至注册)|
|2002|mail send error|邮件验证码发送失败|
|2003|this mailAddress not recive code|未向此邮箱发送验证码|
|2004|this code is error|验证码有误|
|2005|account already regist|此账号已注册(前端需跳至登录)|
|2006|project already favourited|此项目已被收藏过|



#### 1.发送验证码

+ 接口：/api/v1/send/code
+ 方法：GET
+ 参数：如下

|字段名字|描述|类型|是否必须|值(举例)|
|---|---|---|---|---|
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
        "signature":"这个人很懒，什么都没有留下",
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
        "signature":"这个人很懒，什么都没有留下",
        "createTime": 1586229795534,
        "updateTime": 0
    }
}

```




#### 3.1首页

+ 接口：/api/v1/homePage/get
+ 方法：GET
+ 参数：仅共通参数


+ 结果：
```
{
    "code": 0,
    "msg": "success",
    "data": {
        "userName": "用户xAtx",
        "userSignature": "test",
        "projectList": [
            {
                "projectId": "PYi17qi0",
                "name": "Test",
                "code": "",
                "createTime": "2020-04-08 01:54:46",
                "creator": "用户12hu"
            }
        ]
    }
}

```





#### 3.2创建项目

+ 接口：/api/v1/project/create
+ 方法：POST
+ 参数：共通参数及以下

|字段名字|描述|类型|是否必须|值(举例)|
|---|---|---|---|---|
|projectName|项目名字|String|yes|Test|
|projectDesc|项目简介|String|yes|测试|

+ 结果：
```
{
    "code": 0,
    "msg": "success",
    "data": "PYi17qi0"
}

```




#### 3.3提交评论

+ 接口：/api/v1/comment/submit
+ 方法：POST
+ 参数：共通参数及以下

|字段名字|描述|类型|是否必须|值(举例)|
|---|---|---|---|---|
|projectId|项目id|String|yes|PYi17qi0|
|comment|评论内容|String|yes|very good|

+ 结果：
```
{
    "code": 0,
    "msg": "success",
    "data": null
}

```




#### 3.4项目详情

+ 接口：/api/v1/project/detail
+ 方法：GET
+ 参数：共通参数及以下

|字段名字|描述|类型|是否必须|值(举例)|
|---|---|---|---|---|
|projectId|项目id|String|yes|PYi17qi0|

+ 结果：
```
{
    "code": 0,
    "msg": "success",
    "data": {
        "projectId": "PYi17qi0",
        "name": "Test",
        "code": "public class Test{\n    public static void main(String[] args) {\n        System.out.println(\"hello world\");\n    }\n}\n",
        "desc": "测试",
        "messageDTOList": [
            {
                "userId": 1,
                "message": "very good",
                "userName": "用户xAtx"
            }
        ]
    }
}

```




#### 3.5收藏项目

+ 接口：/api/v1/project/favourite
+ 方法：POST
+ 参数：共通参数及以下

|字段名字|描述|类型|是否必须|值(举例)|
|---|---|---|---|---|
|projectId|项目id|String|yes|PYi17qi0|

+ 结果：
```
{
    "code": 0,
    "msg": "success",
    "data": null
}

```



#### 3.6提交项目

+ 接口：/api/v1/project/submit
+ 方法：POST
+ 参数：共通参数及以下(请求体为json)

|字段名字|描述|类型|是否必须|值(举例)|
|---|---|---|---|---|
|projectId|项目id|String|yes|PYi17qi0|
|code|项目代码|String|yes|asdasdasd|

+请求体：
```
{
    "projectId":"PYi17qi0", 
    "code":"public class Test{\n    public static void main(String[] args) {\n        System.out.println(\"hello world\");\n    }\n}"
}

```


+ 结果：
```
{
    "code": 0,
    "msg": "success",
    "data": null
}

```



#### 3.7运行项目

+ 接口：/api/v1/project/run
+ 方法：POST
+ 参数：共通参数及以下

|字段名字|描述|类型|是否必须|值(举例)|
|---|---|---|---|---|
|projectId|项目id|String|yes|PYi17qi0|

+ 结果：
```
{
    "code": 0,
    "msg": "success",
    "data": "hello world\n"
}

```




#### 4.1查看我收藏的项目

+ 接口：/api/v1/mine/favourite
+ 方法：GET
+ 参数：仅共通参数


+ 结果：
```
{
    "code": 0,
    "msg": "success",
    "data": [
        {
            "projectId": "PYi17qi0",
            "name": "Test",
            "code": "public class Test{\n    public static void main(String[] args) {\n        System.out.println(\"hello world\");\n    }\n}\n",
            "createTime": "2020-04-08 01:54:46",
            "creator": "用户12hu"
        }
    ]
}

```




#### 4.2查看我参与的项目

+ 接口：/api/v1/mine/join
+ 方法：GET
+ 参数：仅共通参数


+ 结果：
```
{
    "code": 0,
    "msg": "success",
    "data": [
        {
            "projectId": "PYi17qi0",
            "name": "Test",
            "code": "public class Test{\n    public static void main(String[] args) {\n        System.out.println(\"hello world\");\n    }\n}\n",
            "createTime": "2020-04-08 01:54:46",
            "creator": "用户12hu"
        }
    ]
}

```




#### 4.3修改个人信息

+ 接口：/api/v1/update/userInfo
+ 方法：POST
+ 参数：共通参数及以下

|字段名字|描述|类型|是否必须|值(举例)|
|---|---|---|---|---|
|userName|用户昵称|String|yes|阿三等奖|
|signature|个性签名|String|yes|asdasdasd|

+ 结果：
```
{
    "code": 0,
    "msg": "success",
    "data": null
}

```