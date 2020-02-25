# Thunder DataSource Connection Pool
> 该项目是一个数据源连接池的一款产品，版本1.0
欢迎大家评测点评指导，通过该项目对自身技能的提升，目前还没用于商业生产环境，仅供学习使用与参考。
- 该版本实现了数据库连接池功能，使用极其简单。（已经实现）
- 后续规划实现数据库操作功能（规划中）
    - 1.0.1 实现基本的增、删、改、查
    
# 使用说明
   - 配置文件： classpath: jdbc.properties
```text
username=root
password=root
url=jdbc:mysql://localhost:3306/test
driverClassName=com.mysql.jdbc.Driver
# 数据库连接池参数（后续实现更多参数配置）
initialSize=50
maxActive=300
```
只需要两步就可以完成连接的获取，内部细节参考源码
``` text
// 创建对象
ThunderDataSource dataSource = new ThunderDataSource();
// 获取连接
dataSource.getConnection();

```    