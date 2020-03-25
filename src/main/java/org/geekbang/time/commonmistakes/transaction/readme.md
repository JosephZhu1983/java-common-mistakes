## 20%的业务代码的Spring声明式事务，可能都没处理正确
- 小心Spring的事务可能没有生效：transactionproxyfailed
- 事务即便生效也不一定能回滚：transactionrollbackfailed
- 请确认事务传播配置是否符合自己的业务逻辑：transactionpropagation
- （补充）使用MyBatis配合Propagation.NESTED事务传播模式的例子：nested