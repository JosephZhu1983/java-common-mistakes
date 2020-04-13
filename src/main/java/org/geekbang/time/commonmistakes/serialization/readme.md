## 序列化：一来一回，你还是原来的你吗？
- 序列化和反序列化需要确保算法一致：redistemplate
- 注意Jackson JSON反序列化对额外字段的处理：jsonignoreproperties
- 反序列化时要小心类的构造方法：deserializationconstructor
- 枚举作为API接口参数或返回值的两个大坑：enumusedinapi