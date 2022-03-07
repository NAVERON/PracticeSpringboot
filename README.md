
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



# 技术测验  

测试使用的知识点  

- AOP 使用 pointcut定义 winth, execution 等, Around Before After 等注解 切面  
- properties 配置覆盖  配置数据库, redis, 邮箱  
- logback 配置日志方式 `spring-logback.xml`配置  
- JPA 的使用， mybatis后面再测试使用  



## Redis




## Zookeeper 




## RPC,gRPC使用




## themeleaf 











