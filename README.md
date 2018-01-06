# Server Health Checker with WebService
 
=======================



A console based application to monitor your servers with integrated web gui and REST/SOAP web services.

In contrary to all other server checking softwares we provide IOS  & Android Apps to fully control&check your servers.





Requirements
============

* JAVA (Jre or Jdk) >= 6 (it will automatically install if you use 1st installation method.)



Installation 
============
There are multiple ways to install the software. You can even modify and change the code for you needs.

###1st Way
 Add ubuntu ppa to your system.
  ```perl
 sudo add-apt-repository ppa:hasansarman/serverchecker

 ```
 ###Update package lists
 ```perl
 sudo apt-get update 
 ``` 
 ###Install
 ```perl
 sudo apt-get install serverchecker
 ```
 If you see any errors in the installation process.
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
  #####Config file
 Stored in /usr/bin/serverchecker/serverchecker.properties
  ```perl
  ADMIN_USERNAME = admin 
  ``` 
  --> username for using web services.
 ```perl
 ADMIN_PASSWORD = {}0-9-0980-9oijgiyg@#@$% 
 ``` 
--> password for using web services.
 ```perl
 MAX_SQLITE_DATABASE_SIZE = 1000 
 ```
--> Resets the database when 1000mb of the size reached.
 ```perl
 FIXED_REFRESH_FULL_DATA_TIME = 200 
 ```
 --> It will not refresh the data if FIXED_REFRESH functions are called until this time limit ends.so basically it will refresh data every 200 seconds.
 ```perl 
 FIXED_REFRESH_IMPORTANT_SUMMARY_TIME = 100 
 ```
 --> It will not refresh the data if FIXED_REFRESH functions are called until this time limit ends.so basically it will refresh data every 200 seconds.
 ```perl
 FIXED_REFRESH_SMALL_SUMMARY_TIME = 30
 ```
 --> It will not refresh the data if FIXED_REFRESH functions are called until this time limit ends.so basically it will refresh data every 200 seconds.
 ```perl
 WEBSERVICE_PORT = 9898
 ``` 
 ---> Port to listen
 ```perl
 WEBSERVICE_TYPE = rest
 ```
 ---> rest or soap you ve 2 options.
 
 
 If you are using REST you can check your webservice via http://[serverip]:9898/application.wadl
 If you are using SOAP you can check our webservice via http://[serverip]:9898/StatusWebService?wsdl
           
   

Service Provider

    

Facade

    


Usage
=====

 

Credits
=======

* Hasan SARMAN https://radonrad.com
