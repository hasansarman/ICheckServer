# [I Check Server](http://icheckserver.com)
### I Check Server is a web based and mobile advanced server healt checking application. This application uses builtin webservices to check its servers.
## Server Health Checker with WebService
 




A console based application to monitor your servers with integrated web gui and REST/SOAP web services.

In contrary to all other server checking softwares we provide IOS  & Android Apps to fully control&check your servers.





## Requirements


* JAVA (Jre or Jdk) >= 6 (it will automatically install if you use 1st installation method.)
[JDK JRE](http://www.oracle.com/technetwork/java/javase/downloads/index.html)


## Installation 

There are multiple ways to install the software. You can even modify and change the code for you needs.

### Installation Method 1
for additional installation methods please check this topic.
 Add ubuntu ppa to your system.
  ```perl
sudo add-apt-repository ppa:hasansarman/serverchecker

 ```
 ### Update package lists
 ```perl
 sudo apt-get update 
 ``` 
 ### Install
 ```perl
 sudo apt-get install serverchecker
 ```
 ### If you see any errors in the installation process.
 ```perl
  sudo apt-get install -f
 ```
 and your system is ready to work.
 ```perl
 sudo reboot
 ```
 Initialy the configuration file is filled with
 ```perl
 ADMIN_USERNAME = admin
 ADMIN_PASSWORD = {}0-9-0980-9oijgiyg@#@$%
 MAX_SQLITE_DATABASE_SIZE = 1000
 FIXED_REFRESH_FULL_DATA_TIME = 200
 FIXED_REFRESH_IMPORTANT_SUMMARY_TIME = 100
 FIXED_REFRESH_SMALL_SUMMARY_TIME = 30
 WEBSERVICE_PORT = 9898
 WEBSERVICE_TYPE = rest
 ```
 
 
 
 ## [Wiki](https://github.com/hasansarman/ServerHealthCheckerwithWebService/wiki) 

 1. [Installation](https://github.com/hasansarman/ServerHealthCheckerwithWebService/wiki/Installation)

2. [Alternative Installation Methods](https://github.com/hasansarman/ServerHealthCheckerwithWebService/wiki/Alternative-Installation-Methods)

3. [Configuration File](https://github.com/hasansarman/ServerHealthCheckerwithWebService/wiki/Configuration-File)

4. [Rest Functions](https://github.com/hasansarman/ServerHealthCheckerwithWebService/wiki/Rest-Functions)
 
5. [Soap Functions](https://github.com/hasansarman/ServerHealthCheckerwithWebService/wiki/Soap-Functions)

6. [Writing your own app using ServerChecker API](https://github.com/hasansarman/ServerHealthCheckerwithWebService/wiki/Writing-your-own-app-using-ServerChecker-API)
 
 
### By default REST is configured and after you start your service you can check your service via links below..

 ###### If you are using REST you can check your webservice via 
 http://[serverip]:9898/application.wadl
 
 ###### If you are using SOAP you can check our webservice via
 http://[serverip]:9898/StatusWebService?wsdl
           
   



Facade

    


Usage
=====
ServerChecker runs as a linux service on the background. and listens on the port 9898 by default. Accepting REST or SOAp requests and returning json/xml files for needs. 
[Check our Wiki for detailed information.](https://github.com/hasansarman/ServerHealthCheckerwithWebService/wiki/Writing-your-own-app-using-ServerChecker-API)

 

Credits
=======

* Hasan SARMAN https://radonrad.com
