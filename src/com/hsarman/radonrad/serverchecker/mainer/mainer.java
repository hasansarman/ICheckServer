package com.hsarman.radonrad.serverchecker.mainer;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.util.StringTokenizer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import com.hsarman.radonrad.serverchecker.confighelper.ConfigHelper;
import com.hsarman.radonrad.serverchecker.database_utils.DBHelper;
import com.hsarman.radonrad.serverchecker.utils.Statics;

import com.hsarman.radonrad.serverchecker.webservice.*;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;

public class mainer {
	static Logger logger = Logger.getLogger( mainer.class.getName() );
	private static FileHandler fh;
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String property = System.getProperty("java.library.path");
		StringTokenizer parser = new StringTokenizer(property, ";");
		while (parser.hasMoreTokens()) {
		    System.err.println(parser.nextToken());
		    }
		
		logger.log(Level.INFO, "ServerChecker ENGINE STARTING");
		Bootstrap();
		ExecutorService executorService = Executors.newFixedThreadPool(10);
		  

	//	executorService.execute(new TESTER());
		
		executorService.shutdown();
		

	}



	private static void Bootstrap() {
		// TODO Auto-generated method stub
		logger.log(Level.INFO, "READING CONFIGURATION");
	// ConfigHelper.get_instance().FILL_EMPTY_CONFIG();
		ConfigHelper.get_instance().READ_STATICS();
		
		logger.log(Level.INFO, "STARTING LOGGER");
		Initialize_File_Logger();
		 //logger.log(Level.SEVERE, "Hello logging: {0} ", "P1");
		
		logger.log(Level.INFO, "STARTING DB SYSTEMS");
		Statics.dbHelper=new DBHelper();
		logger.log(Level.INFO, "STARTING Status Server");
		
		WebServiceServer.get_instance().StartServer();
	
	}

	private static void Initialize_File_Logger() {

	    try {  

	        // This block configure the logger with handler and formatter  
	        fh = new FileHandler("server_checker.log",Statics.MAX_LOG_SIZE*1000000,1);  
	        logger.addHandler(fh);
	        SimpleFormatter formatter = new SimpleFormatter();  
	        fh.setFormatter(formatter);  

	        // the following statement is used to log any messages  
	        logger.info("Logger Initiliazed.. Application will start.");  

	    } catch (SecurityException e) {  
	        e.printStackTrace();  
	    } catch (IOException e) {  
	        e.printStackTrace();  
	    }  
		
	}
	

}
