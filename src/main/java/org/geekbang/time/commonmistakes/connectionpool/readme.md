## 连接池：别让连接池帮了倒忙
- 注意鉴别客户端SDK是否基于连接池：jedis
- 使用连接池务必确保复用：httpclient
- 连接池的配置不是一成不变的：datasource
- （补充）三种连接池如何设置两种『连接超时』：twotimeoutconfig