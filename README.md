# Backend
Backend of Algorithm Visualization Platform.

后端代码执行模块来自：https://github.com/NicerWang/DJudger


## API接口定义

### 用户功能接口

* 注册
  * 地址`/register`
  * 参数
    * `name`用户名
    * `pwd`密码
  * 返回值
    * `true` or `false`
  
* 登录
  * 地址`/login`
  * 参数
    * `name`用户名
    * `pwd`密码
  * 返回值
    * `true` or `false`
  
* 退出登录
  * 地址`/logout`
  * 返回值
    * `true` or `false`
  
* 查看当前用户所有执行过的任务(需登陆)
  
  * 地址`/info/tasks`
  * 返回值示例:
  ```json
    [{"uid": 4,
    "time": 1632392708107,
    "status": 1,
    "animation": "{'ops':['get(1)']}",
    "sample": "{1,2,6,5431321,32}",
    "sid": "4_1632392708107",
    "lang": "java",
    "mode": "array"}]
  ```

### 代码提交与运行接口(需登陆)

* 地址
  * `/submit`
  
* 参数
  * `code`代码
  
    仅提交`main(String[] args)`函数内部的代码。
  
    ```java
    // Array
    DataList data = new DataList();
    data.swap(idx1,idx2);
    data.get(idx);
    data.size();
    //Tree
    BinaryTree bt = new BinaryTree();
    BinaryTreeNode root = bt.getRoot();
    root.left.insertLeft(1);
    root.right.removeLeft();
    bt.swap(root.left,root.right);
    ```
  
  * `sample`测试用例
    
    * 格式为:`1,2,3,4,5`
      * 对于`tree`，0和负数代表空结点
    
  * `mode`模式
    
    * `array` or `tree`
    
  * `lang`语言类型
    
    * `java` or `cpp` or `python `
    * 目前只支持`Java`，`C++`与`Python`即将支持
  
* 返回值
  `json动画序列文件` or `TLE` or `TMR`
  
  分别代表执行成功返回的序列/执行超时/用户请求过于频繁。
