#CAFE_NODE
To see detailed information about CAFE, please check the [wiki page](https://github.com/THU-EarthInformationScienceLab/CAFE_NODE/wiki).
##Before your Installation
`Notice`: This package should be installed on the server-side. Data archives need to be read from this server. As the function of this package is limited by some other external applications, a Linux environment is required. To ensure the node work correctly, following applications have to be installed before your installation:       
######1.	MySQL Server and Client (http://www.mysql.com/downloads/ )     
```Bash 
sudo apt-get install mysql-server mysql-client  #For Ubuntu user
```     
######2.	Tomcat 7 (http://tomcat.apache.org/download-70.cgi )      
```Bash 
sudo apt-get install tomcat7     #For Ubuntu user
```     
######3.	JDK1.7 (http://www.oracle.com/technetwork/java/javase/downloads/jdk7-downloads-1880260.html )    
######4.	NCL 6.1.2 or higher version (https://www.earthsystemgrid.org/dataset/ncl.630.html  )    
`Warning`: To ensure the node work correctly, we highly recommend you to install NCL in the default directory /usr/local/ncl and set the environment variable as NCARG_ROOT=/usr/local/ncl    
######5.	CDO 1.6.4 or higher version (Climate Data Operator, https://code.zmaw.de/projects/cdo/ )    
```Bash 
sudo apt-get install cdo       #For Ubuntu user
```     
######6.	NCO（NetCDF Operator, http://nco.sourceforge.net/ ）     
```Bash 
sudo apt-get install nco       #For Ubuntu user
```   
######7.	netcdf library (optional, http://www.unidata.ucar.edu/downloads/netcdf/index.jsp ) 
```Bash 
sudo apt-get install netcdf-bin       #For Ubuntu user
```
######8.	Maven. （http://maven.apache.org/download.cgi ）

######Before your installation, you could set the parameters in the configuration file to ensure the application work correctly.You could modify this file `/config/src/main/resources/baseResources/config.properties` and set the values of four parameters. The default setting is as follows:    
```Bash 
1.	TempFolder=/usr/local/nclscripts/               #The folder stores temp files
2.	ncl_path=/usr/local/ncl/bin/ncl                #The installation path of NCL
3.	ncl_env=NCARG_ROOT=/usr/local/ncl              #The environment variable of NCL
4.	ScriptFolder=/usr/local/nclscripts/            #The folder stores analytic scripts
```
######As a beginner, you have to download default analytic scripts [CAFE_SCRIPTS](https://github.com/THU-EarthInformationScienceLab/CAFE_SCRIPTS) and place the `nclscripts` folder under `ScriptFolder` so that you could successfully use these analytic functions.

#####PAY ATTENTION: Since this version only support `CF-compliant netCDF files`, you may have to reorganize the file names and directories of your data to the specific structure. You may have to refer to this site to reorganize your data archive: `http://cmip-pcmdi.llnl.gov/cmip5/output_req.html ` A software has been provided for reorganization in this web page.
#####When all the preparations are done, you could begin to set up this package.

##Installation procedures
######1.	Database preparation. 
You have to create a user name of your database system, obtain your ip address (`jdbc.host`),access port (`jdbc.port`),user name (`jdbc.user`) and password (`jdbc.password`) 
######2.	Creating a database. 
Create a database then grand privileges to the database user (`jdbc.user`) created in `step 1` and obtain the database name (`jdbc.database`)
######3.  Creating database tables. 
The path of initiation script is: `/db-init/src/main/resources/init.sql`
You have to enter mySQL, use the database in `step2` and run this script.
######4.  Packaging.
You could use following command to generate a `.war` package:
```Bash 
mvn clean package -Dmaven.test.skip=true -Djdbc.host=${jdbc.host} -Djdbc.port=${jdbc.port} -Djdbc.database=${jdbc.database} -Djdbc.user=${ jdbc.user} -Djdbc.password=${jdbc.password}
```
you have to replace ${} to the parameters in `step1` and `step2`, for example:
```Bash 
mvn clean package -Dmaven.test.skip=true -Djdbc.host=101.100.101.100 -Djdbc.port=3306 -Djdbc.database=model_data -Djdbc.user=abc -Djdbc.password=123456
```
######5.  Deploying the war package under the Tomcat.
You could name the `.war` package and place it under `tomcat/webapps`. Then you have to start Tomcat service. The name of the war package will determine the access path of the web application. For example, if the name of the war package is `datamanager-worker.war`, then after deployment, the access address will be `[host]:[port]/datamanager-worker`
######6.  Choosing deployment mode.
You could access this web page（`http://{host}:{port}/datamanager-web/web/deployment`） to choose deployment mode. Generally, in a complete infrastructure, there are one central node and several worker nodes, all the worker nodes are peer-to-peer.
######7.  Data indexing.      
For worker wodes, you could access the web page `http://{host}: {port}/datamanager-web/web/parser`, enter the root folder of the data archive and submit the form from the webpage, the data in this node will then be indexed automatically. The information of the data will be synchronously updated locally and remotely.
