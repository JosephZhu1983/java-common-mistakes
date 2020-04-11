## 别以为“自动挡”就不可能出现OOM
- 太多份相同的对象导致OOM：usernameautocomplete
- 使用WeakHashMap不等于不会OOM：weakhashmapoom
- Tomcat参数配置不合理导致OOM：impropermaxheadersize
