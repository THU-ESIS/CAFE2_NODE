#CAFE_NODE
To see detailed information about this package, please check the [wiki page](https://github.com/THU-EarthInformationScienceLab/CAFE_NODE/wiki)  .
## Before your Installation
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

