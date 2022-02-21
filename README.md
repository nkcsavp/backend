# Backend
Backend of Algorithm Visualization Platform.

后端代码执行模块来自：https://github.com/NicerWang/DJudger

## API接口定义

### 统一返回值格式

> 所有返回值均携带`Http`状态码，在`aioxs`中需要使用`catch`处理。
>
> 除`/info/tasks`

```json
{
  "msg": "Verify Code Send Successfully",
  "code": "200"
}
```

### 用户功能接口

* 注册（需要申请邮箱验证码）
  
  * 申请邮箱验证码`/verify` 
  
    * 参数
  
      * `mail`邮箱（目前仅支持南开企业邮箱`@(mail.)nankai.edu.cn`）
  * 登录`/register`
  
    * 参数
      * `mail`邮箱
      * `pwd`密码
      * `verify`邮箱验证码
  
* 登录
  * 地址`/login`
  * 参数
    * `mail`邮箱
    * `pwd`密码
  
* 退出登录
  * 地址`/logout`
  
* 查看当前用户所有执行过的任务(需登陆)

  * 地址`/info/tasks`
  * 返回值示例:
  ```json
  [{"uid": 4,
    "time": 1632392708107,
    "status": 1,
    "animation": "{'ops':['get(1)']}",
    "sample": "{1,2,6,5431321,32}",
    "idetifier": "4_1632392708107",
    "lang": "java",
    "mode": "array",
    "code": "..."}]
  ```

* 更新密码(需登陆)

  * 地址`/info/updatepwd`
* 参数
  
  * `pwd`新密码

### 代码提交与运行接口(需登陆)

* 地址
  * `/submit`
  
* 参数
  * `sample`测试用例
  
    * 格式为:`1,2,3,4,5`
      * 对于`tree`，0和负数代表空结点
  
  * `mode`模式
  
    * `array` or `tree` or `graph`
  
  * `lang`语言类型
  
    * `java` or `cpp` or `python `
  
  * `relation`关系矩阵(仅`graph`)
  
    二维矩阵转换为数组，格式和`sample`一致，但仅允许0和1出现。
  
* 请求体
  
  具体代码，尤其注意`Python`的格式问题

