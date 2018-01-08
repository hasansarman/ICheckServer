package com.hsarman.radonrad.serverchecker.confighelper;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.FileBasedConfiguration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.FileBasedConfigurationBuilder;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.builder.fluent.Parameters;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.configuration2.plist.PropertyListConfiguration;

import com.hsarman.radonrad.serverchecker.webservice.StatusWebServiceSoap;

import static com.hsarman.radonrad.serverchecker.utils.Statics.*;
public class ConfigHelper {
	public static FileBasedConfiguration config=null;
	public static ConfigHelper instance=null;
	Configurations configs;
	static Logger logger = Logger.getLogger( ConfigHelper.class.getName() );
	FileBasedConfigurationBuilder<FileBasedConfiguration> builder;
	public static ConfigHelper get_instance() {
		if(instance==null) {
			new ConfigHelper();
		}
		return instance;
	}
private  ConfigHelper() {
	  configs = new Configurations();
	try
	{ 
		Path currentRelativePath = Paths.get("");
	String s = currentRelativePath.toAbsolutePath().toString();
	System.out.println(s);
			instance=this;
			
			
			
			Parameters params = new Parameters();
			  builder =
			    new FileBasedConfigurationBuilder<FileBasedConfiguration>(PropertiesConfiguration.class)
			    .configure(params.properties()
			        .setFileName("serverchecker.properties"));
			
			
			
			
			
			  config = builder.getConfiguration();
			
			
			
			
			
			
	      //config = configs.properties(new File( s+"/bonding_engine.properties"));
	     // configs.propertiesBuilder(  s+"/bonding_engine.properties").setAutoSave(true);
	}
	catch (ConfigurationException cex)
	{
	    // Something went wrong
		logger.log(Level.SEVERE, cex.getMessage());
		cex.printStackTrace();
		
	}
}
	public void CHECK_INSTANCE() {
		if(instance==null) {
			new ConfigHelper();
		}
		if(config==null) {
			new ConfigHelper();
		}
	}

	public String getproperty(String name) {
		CHECK_INSTANCE();
		String tempodata = config.getString(name);
		return tempodata;
		//int dbPort = config.getInt("database.port");
		//String dbUser = config.getString("database.user");
		///String dbPassword = config.getString("database.password", "secret");  // provide a default
		//long dbTimeout = config.getLong("database.timeout");
	}
	public void updateproperty(String name,String value) {
		CHECK_INSTANCE();
		config.setProperty(name, value);
	}
	public void addproperty(String name,String value) {
		CHECK_INSTANCE();
		config.addProperty(name,value);
	}
	
	public void READ_STATICS() {
			/*
			 * 
			 *public static String ADMIN_USERNAME="";
	 public static String ADMIN_PASSWORD="";
	
	
	
	
	 public static int MAX_SQLITE_DATABASE_SIZE=1000;
	public static int FIXED_REFRESH_FULL_DATA_TIME=200;
	 
	public static int FIXED_REFRESH_IMPORTANT_SUMMARY_TIME=100;
	public static int FIXED_REFRESH_SMALL_SUMMARY_TIME=10;
	

	public static int WEBSERVICE_PORT=9898;
	
	
			 * */
		String tempo_wrong=getproperty("MAXIMUM_WRONG_PASSWORD_TRYOUTS");
		System.out.println("MAXIMUM_WRONG_PASSWORD_TRYOUTS=  "+tempo_wrong);
		String tempo_wrong_jail=getproperty("WRONG_PASSWORD_JAIL_BLOCK_TIME");
		System.out.println("WRONG_PASSWORD_JAIL_BLOCK_TIME=  "+tempo_wrong_jail);
		if(tempo_wrong==null || tempo_wrong=="") {
			MAXIMUM_WRONG_PASSWORD_TRYOUTS=5;
		}
		else {
			try {
				Integer.parseInt(tempo_wrong);
			}
			catch(Exception e) {
				MAXIMUM_WRONG_PASSWORD_TRYOUTS=5;
			}
		}
		if(tempo_wrong_jail==null || tempo_wrong_jail=="") {
			WRONG_PASSWORD_JAIL_BLOCK_TIME=15;
		}
		else {
			try {
				Integer.parseInt(tempo_wrong_jail);
			}
			catch(Exception e) {
				WRONG_PASSWORD_JAIL_BLOCK_TIME=15;
			}
		}
		
		
		ADMIN_USERNAME=getproperty("ADMIN_USERNAME");
		System.out.println("ADMIN_USERNAME=  "+ADMIN_USERNAME);
		
		ADMIN_PASSWORD=getproperty("ADMIN_PASSWORD");
		System.out.println("ADMIN_PASSWORD=  "+ADMIN_PASSWORD);
		USERS.put(ADMIN_USERNAME, ADMIN_PASSWORD);
		String tempo=getproperty("MAX_SQLITE_DATABASE_SIZE");
		System.out.println("MAX_SQLITE_DATABASE_SIZE=  "+tempo);
		if(tempo==null || tempo=="") {
			MAX_SQLITE_DATABASE_SIZE=1000;
		}
		else {
			try {
				Integer.parseInt(tempo);
			}
			catch(Exception e) {
				MAX_SQLITE_DATABASE_SIZE=1000;
			}
		}
		
		String tempo2=getproperty("FIXED_REFRESH_FULL_DATA_TIME");
		System.out.println("FIXED_REFRESH_FULL_DATA_TIME=  "+tempo2);
		if(tempo2==null || tempo=="") {
			FIXED_REFRESH_FULL_DATA_TIME=200;
		}
		else {
			try {
				FIXED_REFRESH_FULL_DATA_TIME=Integer.parseInt(tempo2);
			}
			catch(Exception e) {
				FIXED_REFRESH_FULL_DATA_TIME=200;
			}
		}
		
		
		String tempo3=getproperty("FIXED_REFRESH_IMPORTANT_SUMMARY_TIME");
		System.out.println("FIXED_REFRESH_IMPORTANT_SUMMARY_TIME=  "+tempo3);
		if(tempo3==null || tempo3=="") {
			FIXED_REFRESH_IMPORTANT_SUMMARY_TIME=100;
		}
		else {
			try {
				FIXED_REFRESH_IMPORTANT_SUMMARY_TIME=Integer.parseInt(tempo3);
			}
			catch(Exception e) {
				FIXED_REFRESH_IMPORTANT_SUMMARY_TIME=100;
			}
		}
		String tempo4=getproperty("FIXED_REFRESH_SMALL_SUMMARY_TIME");
		System.out.println("FIXED_REFRESH_SMALL_SUMMARY_TIME=  "+tempo4);
		if(tempo4==null || tempo4=="") {
			FIXED_REFRESH_SMALL_SUMMARY_TIME=30;
		}
		else {
			try {
				FIXED_REFRESH_SMALL_SUMMARY_TIME=Integer.parseInt(tempo4);
			}
			catch(Exception e) {
				FIXED_REFRESH_SMALL_SUMMARY_TIME=30;
			}
		}
		
		String tempo5=getproperty("WEBSERVICE_PORT");
		System.out.println("WEBSERVICE_PORT=  "+tempo5);
		if(tempo5==null || tempo5=="") {
			WEBSERVICE_PORT=9898;
		}
		else {
			try {
				WEBSERVICE_PORT=Integer.parseInt(tempo5);
			}
			catch(Exception e) {
				WEBSERVICE_PORT=9898;
			}
		}
		String tempo5x=getproperty("WEBSERVICE_TYPE");
		System.out.println("WEBSERVICE_TYPE=  "+tempo5x);
		if(tempo5x==null || tempo5x=="") {
			WEBSERVICE_TYPE="rest";
		}
		else {
			try {
				WEBSERVICE_TYPE=tempo5x;
			}
			catch(Exception e) {
				WEBSERVICE_TYPE="rest";
			}
		}
		String tempo6=getproperty("MAX_LOG_SIZE");
		System.out.println("MAX_LOG_SIZE=  "+tempo6);
		if(tempo6==null || tempo6=="") {
			MAX_LOG_SIZE=30;
		}
		else {
			try {
				MAX_LOG_SIZE=Integer.parseInt(tempo6);
			}
			catch(Exception e) {
				MAX_LOG_SIZE=30;
			}
		}
		
		
	}
public void WRITE_STATICS() {
	updateproperty("USERNAME",ADMIN_USERNAME);
	updateproperty("PASSWORD",ADMIN_PASSWORD);
	updateproperty("MAX_SQLITE_DATABASE_SIZE",MAX_SQLITE_DATABASE_SIZE+"");
	updateproperty("FIXED_REFRESH_FULL_DATA_TIME",FIXED_REFRESH_FULL_DATA_TIME+"");
	updateproperty("FIXED_REFRESH_IMPORTANT_SUMMARY_TIME",FIXED_REFRESH_IMPORTANT_SUMMARY_TIME+"");
	updateproperty("FIXED_REFRESH_SMALL_SUMMARY_TIME",FIXED_REFRESH_SMALL_SUMMARY_TIME+"");
	updateproperty("WEBSERVICE_PORT",WEBSERVICE_PORT+"");
	updateproperty("MAXIMUM_WRONG_PASSWORD_TRYOUTS",MAXIMUM_WRONG_PASSWORD_TRYOUTS+"");
	updateproperty("WRONG_PASSWORD_JAIL_BLOCK_TIME",WRONG_PASSWORD_JAIL_BLOCK_TIME+"");

	
	
	try {
		builder.save();
	} catch (ConfigurationException e) {
		// TODO Auto-generated catch block
		logger.log(Level.SEVERE, e.getMessage());
		e.printStackTrace();
	}
	//save_config();
	
}
public void FILL_EMPTY_CONFIG() {
	
	addproperty("USERNAME",ADMIN_USERNAME);
	addproperty("PASSWORD",ADMIN_PASSWORD);
	addproperty("MAX_SQLITE_DATABASE_SIZE",MAX_SQLITE_DATABASE_SIZE+"");
	addproperty("FIXED_REFRESH_FULL_DATA_TIME",FIXED_REFRESH_FULL_DATA_TIME+"");
	addproperty("FIXED_REFRESH_IMPORTANT_SUMMARY_TIME",FIXED_REFRESH_IMPORTANT_SUMMARY_TIME+"");
	addproperty("FIXED_REFRESH_SMALL_SUMMARY_TIME",FIXED_REFRESH_SMALL_SUMMARY_TIME+"");
	addproperty("WEBSERVICE_PORT",WEBSERVICE_PORT+"");
	addproperty("MAXIMUM_WRONG_PASSWORD_TRYOUTS",MAXIMUM_WRONG_PASSWORD_TRYOUTS+"");
	addproperty("WRONG_PASSWORD_JAIL_BLOCK_TIME",WRONG_PASSWORD_JAIL_BLOCK_TIME+"");

	try {
		builder.save();
	} catch (ConfigurationException e) {
		// TODO Auto-generated catch block
		logger.log(Level.SEVERE, e.getMessage());
		e.printStackTrace();
	}
	//save_config();
}
}
