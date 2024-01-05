## 《Java 开发坑点解析：从根因分析到最佳实践》源码目录

### 书籍购买地址

[京东购买](https://item.jd.com/13986317.html)
[当当购买](https://product.dangdang.com/11673548094.html)

### 源码说明

- 专栏的所有代码基于Java 8 + Spring Boot 2.2.1.RELEASE + Spring Cloud Greenwich.SR4 + Spring Data
  Moore-SR4开发，基于Maven做依赖管理。
- 每一个案例都是独立的SpringBoot或Java命令行应用程序，可以单独启动，避免相互干扰，但是它们公用一个Maven POM。
- 下载源码后，先在根目录运行docker-compose up命令来通过Docker运行相关的MySQL、Redis、ES、RabbitMQ等系统，随后再来启动应用。
-

专栏大部分内容只依赖MySQL一个组件，如果docker-compose启动有困难的话可以先注释docker-compose.yml中的相关组件，比如注释ES和RabbitMQ，等后面设计篇需要用到的时候再启动，并且需要同时删除pom.xml中的相关SpringBoot
Starter模块。
- 源码根目录下有一个readme.md的Markdown文件，这里有一个目录列了每一篇文章对应的源码位置，同时来到每一个源码包中下面还有一个readme.md文件，里面列了每一篇文章中每一个小节的源码包名。
- 大多数源码中的案例都会使用wrong和right这样方法命名来代表错误实现和正确实现，你可以结合书籍内容对比实现来理解。
- 有一些案例（比如SQL索引一文）会基于当前时间生成测试数据，所以不确保文中的测试结果本地可以重现，需要自己调整测试用例。

### 书籍代码索引

#### 说明

- 点击链接进入每一节的代码文件夹
- 每一个文件夹中又有一个readme.md文件，里面列了每节中每一个小节的源码文件夹
- 【思考】代表思考与讨论中涉及到的代码
- 【扩展】代表扩展阅读中涉及到的代码
- 【补充】代表更多其它的不出现在书中涉及到内容的代码

#### 第1章 Java 8中常用的重要知识点

- 1.0：[java8](src/main/java/javaprogramming/commonmistakes/java8/)

#### 第2章 代码篇

- 2.1
  使用了并发工具类库，并不等于就没有线程安全问题了：[concurrenttool](src/main/java/javaprogramming/commonmistakes/concurrenttool/)
- 2.2 代码加锁：不要让“锁”事成为烦心事：[lock](src/main/java/javaprogramming/commonmistakes/lock/)
- 2.3 线程池：业务代码中最常用也最容易犯错的组件：[threadpool](src/main/java/javaprogramming/commonmistakes/threadpool/)
- 2.4 连接池：别让连接池帮了倒忙：[connectionpool](src/main/java/javaprogramming/commonmistakes/connectionpool/)
- 2.5 HTTP调用：您考虑到超时、重试、并发了吗：[httpinvoke](src/main/java/javaprogramming/commonmistakes/httpinvoke/)
- 2.6
  20%的业务代码的Spring声明式事务，可能都没处理正确：[transaction](src/main/java/javaprogramming/commonmistakes/transaction/)
- 2.7 数据库索引：索引不是万能药：[sqlindex](src/main/java/javaprogramming/commonmistakes/sqlindex/)
- 2.8 判等问题：程序里如何确定你就是你：[equals](src/main/java/javaprogramming/commonmistakes/equals/)
- 2.9
  数值计算：注意精度、舍入和溢出问题：[numeralcalculations](src/main/java/javaprogramming/commonmistakes/numeralcalculations/)
- 2.10 集合类：坑满地的List列表操作：[collection](src/main/java/javaprogramming/commonmistakes/collection/)
- 2.11 空值处理：分不清楚的null和恼人的空指针：[nullvalue](src/main/java/javaprogramming/commonmistakes/nullvalue/)
- 2.12 异常处理：别让自己在出问题的时候变为瞎子：[exception](src/main/java/javaprogramming/commonmistakes/exception/)
- 2.13 日志：日志记录真没您想象得那么简单：[logging](src/main/java/javaprogramming/commonmistakes/logging/)
- 2.14 文件IO：实现高效正确的文件读写并非易事：[io](src/main/java/javaprogramming/commonmistakes/io/)
- 2.15 序列化：一来一回，你还是原来的你吗：[serialization](src/main/java/javaprogramming/commonmistakes/serialization/)
- 2.16 用好Java 8的日期时间类，少踩一些“老三样”的坑：[datetime](src/main/java/javaprogramming/commonmistakes/datetime/)
- 2.17 别以为“自动挡”就不可能出现OOM：[oom](src/main/java/javaprogramming/commonmistakes/oom/)
- 2.18
  当反射、注解和泛型遇到OOP时，会有哪些坑：[advancedfeatures](src/main/java/javaprogramming/commonmistakes/advancedfeatures/)
- 2.19 Spring框架：IoC和AOP是扩展的核心：[springpart1](src/main/java/javaprogramming/commonmistakes/springpart1/)
- 2.20
  Spring框架：帮我们做了很多工作也带来了复杂度：[springpart2](src/main/java/javaprogramming/commonmistakes/springpart2/)

#### 第3章 系统设计

- 3.1 代码重复：搞定代码重复的三个绝招：[redundantcode](src/main/java/javaprogramming/commonmistakes/redundantcode/)
- 3.2 接口设计：系统间对话的语言，一定要统一：[apidesign](src/main/java/javaprogramming/commonmistakes/apidesign/)
- 3.3 缓存设计：缓存可以锦上添花也可以落井下石：[cachedesign](src/main/java/javaprogramming/commonmistakes/cachedesign/)
- 3.4 业务代码写完，就意味着生产就绪了吗：[productionready](src/main/java/javaprogramming/commonmistakes/productionready/)
- 3.5 异步处理好用，但非常容易用错：[asyncprocess](src/main/java/javaprogramming/commonmistakes/asyncprocess/)
- 3.6 数据存储：NoSQL与RDBMS如何取长补短、相辅相成？：[nosqluse](src/main/java/javaprogramming/commonmistakes/nosqluse/)

#### 第4章 代码安全问题

- 4.1 数据源头：任何客户端的东西都不可信任：[clientdata](src/main/java/javaprogramming/commonmistakes/clientdata/)
- 4.2
  安全兜底：涉及钱时，必须考虑防刷、限量和防重：[securitylastdefense](src/main/java/javaprogramming/commonmistakes/securitylastdefense/)
- 4.3 数据和代码：数据就是数据，代码就是代码：[dataandcode](src/main/java/javaprogramming/commonmistakes/dataandcode/)
- 4.4 如何正确地保存和传输敏感数据：[sensitivedata](src/main/java/javaprogramming/commonmistakes/sensitivedata/)

#### 第5章 Java程序故障排查

- 5.1 定位Java应用问题的排错套路：N/A
- 5.2
  分析定位Java问题，一定要用好这些工具：[troubleshootingtools](src/main/java/javaprogramming/commonmistakes/troubleshootingtools/)
- 5.3 Java程序从虚拟机迁移到Kubernetes的一些坑：N/A

### 点赞趋势

如果觉得源码对你有帮助，欢迎Star
[![点赞趋势](https://starchart.cc/JosephZhu1983/java-common-mistakes.svg)](https://starchart.cc/JosephZhu1983/java-common-mistakes)

