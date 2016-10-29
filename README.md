#CAFE_NODE
To see detailed information about CAFE, please check the [wiki page](https://github.com/THU-EarthInformationScienceLab/CAFE_NODE/wiki).
##Before your Installation
`Notice`: This package should be installed on the server-side. Data archives need to be read from this server. As the function of this package is limited by some other external applications, a Linux environment is required. To ensure the node work correctly, following applications have to be installed before your installation:       
######1.	MySQL Server and Client (http://dev.mysql.com/downloads/mysql/5.6.html#downloads )     
```Bash 
sudo apt-get install mysql-server mysql-client  #For Ubuntu user
sudo service mysql start #open mysql service
``` 
`Warning`: To ensure the correct connection to the database, you may have to modify the file `/etc/mysql/my.cnf` and annotate the row start with `bind-address`
######2.	Tomcat 7 (http://tomcat.apache.org/download-70.cgi )      
```Bash 
sudo apt-get install tomcat7     #For Ubuntu user
After installing Tomcat, you have to start tomcat by "sh tomcat/bin/startup.sh"
and create a tomcat user by modifying the file "tomcat/conf/tomcat-users.xml"
```     
######3.	JDK1.7 (http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html)
`Warning`: You may have to configure environment variables as below.
```Bash
##java
export JAVA_HOME=/usr/local/java/jdk1.7.0_51
export JRE_HOME=${JAVA_HOME}/jre
export CLASSPATH=.:${JAVA_HOME}/lib:${JRE_HOME}/lib
export PATH=${JAVA_HOME}/bin:$PATH
```
######4.	NCL 6.1.2 or higher version (https://www.earthsystemgrid.org/dataset/ncl.630.html  )    
`Warning`: To ensure the node work correctly, we highly recommend you to install NCL in the default directory /usr/local/ncl and set the environment variable as NCARG_ROOT=/usr/local/ncl    
######5.	CDO 1.6.4 or higher version (Climate Data Operator, https://code.zmaw.de/projects/cdo/ )    
```Bash 
sudo apt-get install cdo       #For Ubuntu user
```     
we highly recommand you to use annaconda to install the latest version using command "‘conda install -c conda-forge nco".
######6.	NCO（NetCDF Operator, http://nco.sourceforge.net/ ）     
```Bash 
sudo apt-get install nco       #For Ubuntu user
```   
we highly recommand you to use annaconda to install the latest version using command "‘conda install -c conda-forge cdo".
######7.	netcdf library (optional, http://www.unidata.ucar.edu/downloads/netcdf/index.jsp ) 
```Bash 
sudo apt-get install netcdf-bin       #For Ubuntu user
```
we highly recommand you to use annaconda to install the latest version of netcdf4 using command "‘conda install -c conda-forge netcdf4".
######8.	Maven. （http://maven.apache.org/download.cgi ）
```Bash 
sudo apt-get install maven       #For Ubuntu user
```
######Before your installation, you could set the parameters in the configuration file to ensure the application work correctly.You could modify this file `/config/src/main/resources/baseResources/config.properties` and set the values of four parameters. The default setting is as follows:    
```Bash 
1.	TempFolder=/usr/local/nclscripts/               #The folder stores temp files
2.	ncl_path=/usr/local/ncl/bin/ncl                #The installation path of NCL
3.	ncl_env=NCARG_ROOT=/usr/local/ncl              #The environment variable of NCL
4.	ScriptFolder=/usr/local/nclscripts/            #The folder stores analytic scripts
```
######As a beginner, you need to download default analytic scripts [CAFE_SCRIPTS](https://github.com/THU-EarthInformationScienceLab/CAFE_SCRIPTS) and place all the scripts in `nclscripts` folder to the `ScriptFolder` so that you could later make use of these analytic functions.

#####PAY ATTENTION: Since this version only support `CF-compliant netCDF files`, you may have to reorganize the file names and directories of your data to the specific structure. You may have to refer to this site to reorganize your data archive: `http://cmip-pcmdi.llnl.gov/cmip5/output_req.html ` A software has been provided for reorganization in this web page.
#####When all the preparations are done, you could begin to set up this package.

##Installation procedures
######1.	Database preparation. 
You have to create a user for your database system using mySQL root account, obtain your ip address {`jdbc.host`},database access port {`jdbc.port`},database user name {`jdbc.user`} and password of the database user {`jdbc.password`}
```Bash 
e.g. CREATE USER 'username'@'%' IDENTIFIED BY 'password'; 
```
`Note`: To ensure the database can be connected using ip and from remote servers, '%' should be used.
######2.	Creating a database. 
Create a database then grand privileges to the database user {`jdbc.user`} created in `step 1` and obtain the database name {`jdbc.database`}
```Bash 
e.g. # if the name of your database called CAFENODE, then jdbc.database=CAFENODE
     # if your username is guest, password is '123456', then jdbc.user=geust, jdbc.password=123456
     # you can enter mySQL using command line 'mysql -u guest -p', then use following codes.
     GRANT all privileges ON CAFENODE.* TO 'username'@'%'
     FLUSH PRIVILEGES;
```
`Note`: To ensure the database can be connected using ip and from remote servers, '%' should be used.
######3.  Creating database tables. 
The path of initiation script is: `db-init/src/main/resources/init.sql`
You should first enter the directory `db-init/src/main/resources` of the CAFE-NODE source code folder.
Then You have to enter mySQL using command line 'mysql -u {username} -p', use the database in `step2` and run this script.
```Bash 
source init.sql;
```
######4.  Packaging.
You have to enter the root directory of CAFE_NODE folder.
Then you could use following command to compile the codes and generate a `.war` package:
```Bash 
mvn clean package -Dmaven.test.skip=true -Djdbc.host=${jdbc.host} -Djdbc.port=${jdbc.port} -Djdbc.database=${jdbc.database} -Djdbc.user=${ jdbc.user} -Djdbc.password=${jdbc.password} -DlogDir=${logDir}
```
`Note`:the `.war` package is under `datamanager-web/target/`
you have to replace ${} to the parameters in `step1` and `step2`, for example:
```Bash 
mvn clean package -Dmaven.test.skip=true -Djdbc.host=101.100.101.100 -Djdbc.port=3306 -Djdbc.database=CAFENODE -Djdbc.user=abc -Djdbc.password=123456 -DlogDir=/usr/local/CAFE/log 
# ${logDir} is your log directory for CAFE. If this directory does not exit, have to create it first.
```
######5.  Deploying the war package under the Tomcat.
You could rename the `.war` package and place it under `tomcat/webapps`. Then you have to start Tomcat service. The name of the war package will determine the access path of the web application. For example, if the name of the war package is `datamanager-worker.war`, then after deployment, the access address will be `http://{host}:{port}/datamanager-worker`
`Note`:{host} is the ip address of the node, and {port} is tomcat port. You can also use tomcat management webpage to deploy the package. 
######6.  Choosing deployment mode.
You could access this web page（`http://{host}:{port}/{war package name}/web/deployment`） to choose deployment mode. Generally, in a complete infrastructure, there are one central node and several worker nodes, all the worker nodes are peer-to-peer.
######7.  Data indexing.      
For worker wodes, you could access the web page `http://{host}: {port}/{war package name}/web/parser`, enter the root folder of the data archive and submit the form from the webpage, the data in this node will then be indexed automatically. The information of the data will be synchronously updated locally and remotely.
