package com.hsarman.radonrad.serverchecker.utils;

import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.ws.Endpoint;

import com.hsarman.radonrad.serverchecker.database_utils.DBHelper;
import com.hsarman.radonrad.serverchecker.webservice.StatusWebServiceRest;
 
public class Statics {

	
	 public static final String DB_NAME = "Rad_server.sqlite";
	 public static String WHO_IS_YOUR_DADY="RADONRAD - HASAN SARMAN";
	 public static String ADMIN_USERNAME="";
	 public static String ADMIN_PASSWORD="";
	
	
	
	
	 public static int MAX_SQLITE_DATABASE_SIZE=1000;
	 public static int MAX_LOG_SIZE=1000;
	public static int FIXED_REFRESH_FULL_DATA_TIME=200;
	 
	public static int FIXED_REFRESH_IMPORTANT_SUMMARY_TIME=100;
	public static int FIXED_REFRESH_SMALL_SUMMARY_TIME=30;
	

	public static int WEBSERVICE_PORT=9898;
	public static String WEBSERVICE_TYPE="rest";
	
	public static HashMap<String,String> USERS=new HashMap<String,String>();
	public static DBHelper dbHelper;
	public static long last_updated_onDB=0;
	public static Endpoint KOKO_ENDPOINT;
	public static StatusWebServiceRest REST_SERVER;
}
