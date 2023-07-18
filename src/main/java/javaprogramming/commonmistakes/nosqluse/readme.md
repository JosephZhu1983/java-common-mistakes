## 数据存储：NoSQL与RDBMS如何取长补短、相辅相成？
- 取长补短之 Redis vs MySQL：redisvsmysql
- 取长补短之 InfluxDB vs MySQL：influxdbvsmysql
- 取长补短之 Elasticsearch vs MySQL：esvsmysql
- 结合NoSQL和MySQL应对高并发的复合数据库架构：N/A

## 注意，运行esvsmysql之前需要先为ES安装IK分词器

步骤如下：
- 进入容器：docker exec -it es01 /bin/bash
- 安装IK分词插件：bin/elasticsearch-plugin install https://github.com/medcl/elasticsearch-analysis-ik/releases/download/v7.5.2/elasticsearch-analysis-ik-7.5.2.zip
- 重启容器：docker restart es01
- 把es01替换为es02和es03，重新执行上面三步