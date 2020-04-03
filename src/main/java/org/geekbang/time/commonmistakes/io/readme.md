## 文件IO：实现高效正确的文件读写并非易事
- 文件读写需要确保字符编码一致：badencodingissue
- 使用Files类静态方法进行文件操作注意释放文件句柄：filestreamoperationneedclose
- 注意读写文件要考虑设置缓冲区：filebufferperformance

