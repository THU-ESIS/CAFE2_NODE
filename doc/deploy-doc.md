#### 概述  

> 首先需要阐明的一个问题是：此系统的服务器节点有两种类型：`Central Node`与`Worker Node`。无论是哪种类型的节点，它们的`源代码``war包内容（除了数据库配置部分）``数据库表结构`在初次部署至tomcat之初都是一样的。在初次部署至tomcat后的`选择部署类型(Central Node还是Worker Node)`后，它们才有了中央节点与子节点的区别。  

#### 从零部署的步骤：  
1. 准备数据库。安装mysql、创建用户名 ，产生`数据库ip地址 (jdbc.host)` `数据库端口 (jdbc.port)` `数据库用户名 (jdbc.user)` `数据库密码 (jdbc.password)`  
2. 创建数据库。创建一个数据库，授权给步骤一中的`jdbc.user`，产生`数据库名 (jdbc.database)`  
3. 创建数据库表。使用代码目录`/db-init/src/main/resources/init.sql`的建表脚本创建数据库表  
4. 打war包。在代码根目录使用如下命令打包：  
```bash
mvn clean package -Dmaven.test.skip=true -Djdbc.host=${jdbc.host} -Djdbc.port=${jdbc.port} -Djdbc.user=${jdbc.user} -Djdbc.password=${jdbc.password} -Djdbc.database=${jdbc.database}
```
其中，${}部分的参数需要替换成步骤1和步骤2中产生的参数。一个具体的打包命令示例如下：  
```bash
mvn clean package -Dmaven.test.skip=true -Djdbc.host=166.111.7.72 -Djdbc.port=3306 -Djdbc.user=yanke -Djdbc.password=passwordOfYanke -Djdbc.database=cmip5data_info
```
打包生成的war包位置为`/datamanager-web/target/datamanager-web.war`  
5. 部署war包至tomcat。将war包取一个合适的名字，放在`tomcat/webapps`目录下，启动tomcat。war包名字将决定这个web应用未来的访问路径。如：war包起名为`datamanager-worker.war`，那么部署完成后，访问地址将为`host:port/datamanager-worker`  
6. 选择该节点的部署模式。在浏览器中访问`http://host:port/datamanager-web/web/deployment`，在此页面中进行部署模式的配置。  
7. 对于Worker Node，索引本机上的nc文件。在浏览器中访问`http://host:port/datamanager-web/web/parser`，在Path中输入nc文件的根目录并提交，即可开始索引。  
