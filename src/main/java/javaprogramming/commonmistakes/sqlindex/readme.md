## 数据库索引：索引不是万能药
- InnoDB是如何存储数据的？：N/A
- 聚簇索引和二级索引：N/A
- 考虑额外创建二级索引的代价：init.sql + indexcost.sql
- 不是所有针对索引列的查询都能用上索引：notuseindex.sql
- 数据库基于成本决定是否走索引：optimizer_trace.sql