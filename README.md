
# 项目规划

主要用于练习springboot各种功能  
实现船舶避碰的第5个版本  
划分后台管理, 网页登陆, 船舶信息管理  

## 实现功能

- [ ] 后台实现用户管理, 船舶管理, 船舶轨迹记录等功能, 提供api服务  
- [ ] 同时提供网页服务用于用户登陆和使用  
- [ ] 后期有时间的话, 使用javafx/fxgl实现后台管理功能, 或客户端使用功能, 测试客户端和springboot后台的交互  


## 时间安排

> 可以插入 mermaid 绘制的项目管理图, 测试使用 

3.1 - 3.13 springboot 后端必须完成  

- 使用redis 邮箱注册验证流程  
- 使用kafka/activeMQ 轨迹点记录, 异步处理/消峰  
- zookeeper 配置和监听功能使用  
- grpc的植入使用, 练习  

3.15 - 3.22 前端功能/javafx 

- thymeleaf 网页基本功能  
- fxgl 的使用, 快速实现  


## 问题解决记录 

1. jpa 定义的entity `@Table`注解指名关联的表名称, 对象的属性字段需要使用`@Column(name = "")`指定对应的列名称, 否则会自动在表中补充缺失的字段  
2. jpa Converter 转换 localateime/ localdate 在新版本JPA中可能已经实现, 不需要手动处理  
3. 定义api的时候 需要注意接口重复, 所以一般在同一个api类中最好使用不同的前缀  
4. resource docs目录下存放自定义脚本  
5. jpa 自定义query的使用, 删除语句返回删除数据的表示, 重写deleteById 方法等. 自定义一种按照java对象查询, 另一种使用nativeuery = true 直接写SQL   
6. controller返回示图, 当前一把使用setViewName, 没有使用setView 原因延后查询  
7. 页面跳转 redirect: 后跟url地址, 直接返回string时, 返回的是templates的模板地址  
8. 避免重复提交的方法中, 一般的思路为 DCL 或者 cache 缓存，设置过期时间； 简单做法还有LRU LinkedListHash 可以直接使用, 一般用作对频繁请求不那么敏感的场景  
9. Builder 内建static class 构建, 类似于工厂模式, 快速创建对象  
10. 工具类一般使用静态方法, 使用时直接调用 ClassName.xxxMethod()  
11. 返回Json结构使用泛型创建ResponseBody  返回业务码和对应的含义, 世纪的应用中团队自行定义和修改, 区分不同的业务场景 划分不同的Enum  
12. thymeleaf 页面的语法与普通的html语法稍微有区别，多参考官方文档即可  
13. post 请求使用RequestParam 参数, 需要直接在请求url中拼接参数, 不能使用requestBody, 另外 如果使用RequestBody 需要全部格式化成表传的Json  
14. 如果开启了security 权限验证, 在某些情况会出现 禁止跨域请求  403 Foribion 错误, 可以临时关闭, 以后在了解接口安全相关的架构  
15. 整体的逻辑架构中, controller曾最好不要处理多种符合逻辑, 全部放在service曾作逻辑哦处理, Controller 只处理参数验证 日志记录和试图设置ModelAndView 跳转参数等状态设置  
16. 经过测试总结   ModelAndView 当设置普通视图的时候, addObject会传入页面对象, 当设置重定向试图的时候, 传入的是uri requestParam 参数  
17. 出现数据过大无法存储到数据库中问题, 汇总为一类问题就是需要关注数据类型和合法校验, 可以写到aop或者filter中  
```shell
MysqlDataTruncation: Data truncation: Data too long for column 'call_number' at row 1
```
18. 注意redis中`setIfPresent`和`setIfAbsent`的区别  absent 如果不存在设置返回true, 如果已经有了, false; present 已经有了覆盖, true, 如果没有 返回false  
19. @ConfigurationProperties 的使用, 直接获取系列公共前缀的对象, 可以快速获取配置参数; 驼峰命名 properties中使用`-`连接, 如对象使用`userName`, 配置文件中就需要使用`user-name`, 或者`userName` / `user_name` / `USER_NAME`  
20. 注解`@RequestMapping` 使用path = xxx 更直观, 以后直接使用path  
21. 关于用户注册过程中密码加密的想法 在前端加密和后端加密是一样的, 前端是可以直接获取原始密码的, 方便起见以后直接放在后端加密; 好处 加密方式多样, 灵活的变化和解密耦合(如果加密方式变化 解密也需要变化, 兼容更多的加密规则)  
22. jwt token 五状态登陆技术, json web token 语言无关的身份认证框架, 结构分为header<算法和格式声明>, payload <定制内容，主要载荷, 使用Base64Url 编码>, sign<签名 >  
我的理解 : JWT 使用计算机算力解决无状态登陆 restful风格接口 分布式 内存session增长的问题  

>  iss (issuer)：表示签发人  
> exp (expiration time)：表示token过期时间  
> sub (subject)：主题  
> aud (audience)：受众  
> nbf (Not Before)：生效时间  
> iat (Issued At)：签发时间  
> jti (JWT ID)：编号  

23. kafka的简单使用template完成测试, 今后需要深入了解使用方法, 理论有一个大致的认识  



# 技术测验  

测试使用的知识点  

- AOP 使用 pointcut定义 winth, execution 等, Around Before After 等注解 切面  
- properties 配置覆盖  配置数据库, redis, 邮箱  
- logback 配置日志方式 `spring-logback.xml`配置  
- JPA 的使用， mybatis后面再测试使用  
- 使用webSecurity 安全验证 `curl -i --user wangyulong:testing -X GET localhost:8080/api/v1/users`  



## Redis

命令行的使用, 基本数据结构  



## Zookeeper 

> 需要实验实现zookeeper作为配置中心, 读取配置数据, 并监听配置数据的变化  


## Kafka 基础

当前版本依赖于zookeeper, 注意端口号的设置, 了解整个bin目录的结构, 配置文件的设置含义等...  



## RPC, gRPC使用

单独测试, 当前只是简单的测试验证, 可以进行不同项目的通信  
如果要深入使用, 需要完成不同工程的模块化

- 接口工程, 定义统一的通信数据格式和服务API  
- 调用服务端, 实现接口的实际动作  
- 客户端调用rpc, 实现调用实现的服务端  

> 这里面还可以改进的地方 : 客户端并不能主动的直到接口定义, 如果有服务注册发现就好了  
> 每个服务实现方注册自己的实现, 客户端根据需要决定调用哪一种实现  




## themeleaf 

已经简单使用, 与JSP类似, 在html中插入标记语言 `${xxx}`, 写法上类似, thymeleaf的好处是, 直接将变量写到 tag属性中, 没有变量实际传入不会报错, 指挥显示默认值, 前端友好  










