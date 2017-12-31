package com.hsarman.radonrad.serverchecker.webservice;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.jws.WebMethod;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.JSONP;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;

import com.google.gson.Gson;
import com.hsarman.radonrad.serverchecker.database_utils.models.Stat_Logs;
import com.hsarman.radonrad.serverchecker.utils.Statics;

import oshi.SystemInfo;
import oshi.hardware.Baseboard;
import oshi.hardware.CentralProcessor;
import oshi.hardware.ComputerSystem;
import oshi.hardware.Display;
import oshi.hardware.Firmware;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.NetworkIF;
import oshi.hardware.PowerSource;
import oshi.hardware.Sensors;
import oshi.hardware.UsbDevice;
import oshi.hardware.CentralProcessor.TickType;
import oshi.software.os.FileSystem;
import oshi.software.os.NetworkParams;
import oshi.software.os.OSFileStore;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.software.os.OperatingSystem.ProcessSort;
import oshi.util.FormatUtil;
import oshi.util.Util;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/StatusWebServiceRest")
public class StatusWebServiceRest {
	@OPTIONS
	@Path("{path : .*}")
	public Response options() {
	    return Response.ok("")
	            .header("Access-Control-Allow-Origin", "*")
	            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
	            .header("Access-Control-Allow-Credentials", "true")
	            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
	            .header("Access-Control-Max-Age", "1209600")
	            .build();
	}
    @GET
    @Path("rest")
    @Produces({"application/json", "application/javascript"})
    @JSONP(queryParam = "callback")
    
    public String test() {
        return " Please visit http://[IP]:[PORT]/application.wadl";
    }
    
    

	static Logger logger = Logger.getLogger( StatusWebServiceSoap.class.getName() );
	  private ComputerSystem computerSystem;
	private SystemInfo si;
	private HardwareAbstractionLayer hal;
	private OperatingSystem os;
	private StatusData fixed_refresh_full_status_data;
	long last_refresh_time_fixed_refresh_full_status_data=0;
	private StatusData fixed_refresh_important_summary_data;
	private long last_refresh_time_fixed_refresh_important_summary_data=0;
	private StatusData fixed_refresh_small_summary_data;
	private long last_refresh_time_fixed_refresh_small_summary_data=0;
	
	
/*
 * @Path("{year}/{month}/{day}")
	public Response getUserHistory(
			@PathParam("year") int year,
			@PathParam("month") int month,
			@PathParam("day") int day)*/
	
	 @GET
	    @Path("FIXED_REFRESHED_FULL_DATA/{username}/{password}")
	    @Produces({"application/json", "application/javascript"})
    @JSONP(queryParam = "callback")
	public String FIXED_REFRESHED_FULL_DATA(@PathParam("username") String username,@PathParam("password") String password) {
		 if(CHECK_USER(username,password)) {
				return "GO AWAY";
			}
		 
		 
		 if(last_refresh_time_fixed_refresh_full_status_data==0) {
			 last_refresh_time_fixed_refresh_full_status_data=System.currentTimeMillis();
		 }
		long diff= System.currentTimeMillis()-last_refresh_time_fixed_refresh_full_status_data;
		 if(diff<(Statics.FIXED_REFRESH_FULL_DATA_TIME*1000) && fixed_refresh_full_status_data!=null ){
			 return RETURN_GSON(fixed_refresh_full_status_data,"FIXED_FULL_CACHED");
		 }
		 si = new SystemInfo();

		   hal = si.getHardware();
		   os = si.getOperatingSystem();

		  computerSystem=  hal.getComputerSystem();
		  
		StatusData itemtemp=new StatusData();
		itemtemp=BASE_DATA(itemtemp );
		itemtemp=CPU_LOADS(itemtemp );
		itemtemp=TOPPROCESS(itemtemp,5 );
		itemtemp=SENSORS(itemtemp );
		itemtemp=POWERSOURCE(itemtemp );
		itemtemp=DISKS(itemtemp);
		itemtemp=USBS(itemtemp); 
		itemtemp=DISPLAYS(itemtemp);
		itemtemp=NETWORK(itemtemp);
		itemtemp=NETWORK_PARAMS(itemtemp);
		itemtemp=FILESYSTEM(itemtemp);
		fixed_refresh_full_status_data=itemtemp;
		
		return RETURN_GSON(itemtemp,"FIXED_FULL");

		
		
	}
	
	 @GET
	    @Path("FIXED_REFRESHED_IMPORTANT_SUMMARY/{username}/{password}")
	    @Produces({"application/json", "application/javascript"})
    @JSONP(queryParam = "callback")
	public String FIXED_REFRESHED_IMPORTANT_SUMMARY(@PathParam("username") String username,@PathParam("password") String password) {
		 if(CHECK_USER(username,password)) {
				return "GO AWAY";
			}
		 
		  
		 if(last_refresh_time_fixed_refresh_important_summary_data==0) {
			 last_refresh_time_fixed_refresh_important_summary_data=System.currentTimeMillis();
		 }
		long diff= System.currentTimeMillis()-last_refresh_time_fixed_refresh_important_summary_data;
		 if(diff<(Statics.FIXED_REFRESH_IMPORTANT_SUMMARY_TIME*1000) && fixed_refresh_important_summary_data!=null){
			 return RETURN_GSON(fixed_refresh_important_summary_data,"IMPORTANT_SUMMARY_CACHED");
		 }
		 si = new SystemInfo();

		   hal = si.getHardware();
		   os = si.getOperatingSystem();

		  computerSystem=  hal.getComputerSystem();
		  
		StatusData itemtemp=new StatusData();
		  itemtemp=CPU_LOADS(itemtemp );
		  itemtemp=TOPPROCESS(itemtemp,5 );
		  itemtemp=SENSORS(itemtemp );
		  itemtemp=DISKS(itemtemp);
		  itemtemp=NETWORK(itemtemp);
		fixed_refresh_important_summary_data=itemtemp;
		
		return RETURN_GSON(itemtemp,"IMPORTANT_SUMMARY");

		
		
	}
	
	 @GET
	    @Path("FIXED_REFRESHED_SMALL_SUMMARY/{username}/{password}")
	    @Produces({"application/json", "application/javascript"})
    @JSONP(queryParam = "callback")
	public String FIXED_REFRESHED_SMALL_SUMMARY(@PathParam("username") String username,@PathParam("password") String password) {
		 if(CHECK_USER(username,password)) {
				return "GO AWAY";
			}
		 
		  
		 if(last_refresh_time_fixed_refresh_small_summary_data==0) {
			 last_refresh_time_fixed_refresh_small_summary_data=System.currentTimeMillis();
		 }
		long diff= System.currentTimeMillis()-last_refresh_time_fixed_refresh_small_summary_data;
		 if(diff<(Statics.FIXED_REFRESH_SMALL_SUMMARY_TIME*1000) && fixed_refresh_small_summary_data!=null){
			 return RETURN_GSON(fixed_refresh_important_summary_data,"SMALL_SUMMARY_CACHED");
		 }
		 si = new SystemInfo();

		   hal = si.getHardware();
		   os = si.getOperatingSystem();

		  computerSystem=  hal.getComputerSystem();
		  
		StatusData itemtemp=new StatusData();
		 itemtemp=CPU_LOADS(itemtemp );
		 
		  itemtemp=NETWORK(itemtemp);
		fixed_refresh_important_summary_data=itemtemp;
		
		return RETURN_GSON(itemtemp,"SMALL_SUMMARY");

		
		
	}
 
  
	
	 @GET
	    @Path("JsonBASE_INFO/{username}/{password}")
	    @Produces({"application/json", "application/javascript"})
    @JSONP(queryParam = "callback")
 public String JsonBASE_INFO(@PathParam("username") String username,@PathParam("password") String password) {
	 if(CHECK_USER(username,password)) {
			return "GO AWAY";
		}
	 si = new SystemInfo();

	   hal = si.getHardware();
	   os = si.getOperatingSystem();

	  computerSystem=  hal.getComputerSystem();
	  
StatusData itemtemp=new StatusData();
itemtemp=BASE_DATA(itemtemp);
	
return RETURN_GSON(itemtemp,"JSON_BASE_INFO");

	  
 }
	 @GET
	    @Path("Json_CPU_LOADS/{username}/{password}/{input}")
	    @Produces({"application/json", "application/javascript"})
    @JSONP(queryParam = "callback")
public String Json_CPU_LOADS(@PathParam("username") String username,@PathParam("password") String password,@PathParam("input") int input) {
	
	si = new SystemInfo();

	   hal = si.getHardware();
	   os = si.getOperatingSystem();

	  computerSystem=  hal.getComputerSystem();
	  StatusData itemtemp=new StatusData();
	  itemtemp=CPU_LOADS(itemtemp);
	  	
	  return RETURN_GSON(itemtemp,"JSON_CPU_LOADS");
}
	 @GET
	    @Path("Json_PROCESSES/{username}/{password}/{input}")
	    @Produces({"application/json", "application/javascript"})
    @JSONP(queryParam = "callback")
public String Json_PROCESSES(@PathParam("username") String username,@PathParam("password") String password,@PathParam("input") int input) {
	if(CHECK_USER(username,password)) {
		return "GO AWAY";
	}
	
	si = new SystemInfo();

	   hal = si.getHardware();
	   os = si.getOperatingSystem();

	  computerSystem=  hal.getComputerSystem();
	  StatusData itemtemp=new StatusData();
	  itemtemp=TOPPROCESS(itemtemp,input);
	  	
	  return RETURN_GSON(itemtemp,"JSON_PROCESSES");
	
	
}
	 @GET
	    @Path("Json_SENSORS/{username}/{password}")
	    @Produces({"application/json", "application/javascript"})
    @JSONP(queryParam = "callback")
public String Json_SENSORS(@PathParam("username") String username,@PathParam("password") String password) {
	if(CHECK_USER(username,password)) {
		return "GO AWAY";
	}
	
	
	si = new SystemInfo();

	   hal = si.getHardware();
	   os = si.getOperatingSystem();

	  computerSystem=  hal.getComputerSystem();
	  StatusData itemtemp=new StatusData();
	  itemtemp=SENSORS(itemtemp);
	  	
	  return RETURN_GSON(itemtemp,"JSON_SENSORS");
	
}
	 @GET
	    @Path("Json_POWERSOURCE/{username}/{password}")
	    @Produces({"application/json", "application/javascript"})
    @JSONP(queryParam = "callback")
public String Json_POWERSOURCE(@PathParam("username") String username,@PathParam("password") String password) {
	
	if(CHECK_USER(username,password)) {
		return "GO AWAY";
	}
	si = new SystemInfo();

	   hal = si.getHardware();
	   os = si.getOperatingSystem();

	  computerSystem=  hal.getComputerSystem();
	  StatusData itemtemp=new StatusData();
	itemtemp=POWERSOURCE(itemtemp );
	 return RETURN_GSON(itemtemp,"JSON_POWERSOURCE");
	
	
}
	 @GET
	    @Path("Json_DISKS/{username}/{password}")
	    @Produces({"application/json", "application/javascript"})
    @JSONP(queryParam = "callback")
public String Json_DISKS(@PathParam("username") String username,@PathParam("password") String password) {
	if(CHECK_USER(username,password)) {
		return "GO AWAY";
	}
	si = new SystemInfo();

	   hal = si.getHardware();
	   os = si.getOperatingSystem();

	  computerSystem=  hal.getComputerSystem();
	  StatusData itemtemp=new StatusData();
	  itemtemp=DISKS(itemtemp);
	 return RETURN_GSON(itemtemp,"JSON_DISKS");
	
	
}
	 @GET
	    @Path("Json_USBS/{username}/{password}")
	    @Produces({"application/json", "application/javascript"})
    @JSONP(queryParam = "callback")
public String Json_USBS(@PathParam("username") String username,@PathParam("password") String password) {
	
	if(CHECK_USER(username,password)) {
		return "GO AWAY";
	}
	si = new SystemInfo();

	   hal = si.getHardware();
	   os = si.getOperatingSystem();

	  computerSystem=  hal.getComputerSystem();
	  StatusData itemtemp=new StatusData();
	  itemtemp=USBS(itemtemp);
	 return RETURN_GSON(itemtemp,"JSON_USBS");
	
	 
	
	
}
	 @GET
	    @Path("Json_DISPLAYS/{username}/{password}")
	    @Produces({"application/json", "application/javascript"})
    @JSONP(queryParam = "callback")
public String Json_DISPLAYS(@PathParam("username") String username,@PathParam("password") String password) {
	if(CHECK_USER(username,password)) {
		return "GO AWAY";
	}
	si = new SystemInfo();

	   hal = si.getHardware();
	   os = si.getOperatingSystem();

	  computerSystem=  hal.getComputerSystem();
	  StatusData itemtemp=new StatusData();
	  itemtemp=DISPLAYS(itemtemp);
	 return RETURN_GSON(itemtemp,"JSON_DISPLAYS");
	
	 
	
	
	
}
	 @GET
	    @Path("Json_NETWORK/{username}/{password}")
	    @Produces({"application/json", "application/javascript"})
    @JSONP(queryParam = "callback")
public String Json_NETWORK(@PathParam("username") String username,@PathParam("password") String password) {
	if(CHECK_USER(username,password)) {
		return "GO AWAY";
	}
	si = new SystemInfo();

	   hal = si.getHardware();
	   os = si.getOperatingSystem();

	  computerSystem=  hal.getComputerSystem();
	  StatusData itemtemp=new StatusData();
	  itemtemp=NETWORK(itemtemp);
	 return RETURN_GSON(itemtemp,"JSON_NETWORK");
	
	 
	
	 
}
	 @GET
	    @Path("Json_NETWORK_PARAMS/{username}/{password}")
	    @Produces({"application/json", "application/javascript"})
    @JSONP(queryParam = "callback")
public String Json_NETWORK_PARAMS(@PathParam("username") String username,@PathParam("password") String password) {
	if(CHECK_USER(username,password)) {
		return "GO AWAY";
	}
	si = new SystemInfo();

	   hal = si.getHardware();
	   os = si.getOperatingSystem();

	  computerSystem=  hal.getComputerSystem();
	  StatusData itemtemp=new StatusData();
	  itemtemp=NETWORK_PARAMS(itemtemp);
	 return RETURN_GSON(itemtemp,"JSON_NETWORK_PARAMS");
	 
	
	
}
	 @GET
	    @Path("Json_FILESYSTEM/{username}/{password}")
	    @Produces({"application/json", "application/javascript"})
    @JSONP(queryParam = "callback")
public String Json_FILESYSTEM(@PathParam("username") String username,@PathParam("password") String password) {
	if(CHECK_USER(username,password)) {
		return "GO AWAY";
	}
	si = new SystemInfo();

	   hal = si.getHardware();
	   os = si.getOperatingSystem();

	  computerSystem=  hal.getComputerSystem();
	  StatusData itemtemp=new StatusData();

		itemtemp=FILESYSTEM(itemtemp);
	 return RETURN_GSON(itemtemp,"JSON_FILESYSTEM");
	 
	
}
	 @GET
	    @Path("Json_IMPORTANT_SUMMARY/{username}/{password}")
	    @Produces({"application/json", "application/javascript"})
    @JSONP(queryParam = "callback")
public String Json_IMPORTANT_SUMMARY(@PathParam("username") String username,@PathParam("password") String password) {
	if(CHECK_USER(username,password)) {
		return "GO AWAY";
	}
	si = new SystemInfo();

	   hal = si.getHardware();
	   os = si.getOperatingSystem();

	  computerSystem=  hal.getComputerSystem();
	  StatusData itemtemp=new StatusData();

	  itemtemp=CPU_LOADS(itemtemp );
	  itemtemp=TOPPROCESS(itemtemp,5 );
	  itemtemp=SENSORS(itemtemp );
	  itemtemp=DISKS(itemtemp);
	  itemtemp=NETWORK(itemtemp);
	  return RETURN_GSON(itemtemp,"JSON_IMPORTANT_SUMMARY");
	 
}
	 @GET
	    @Path("Json_SMALL_SUMMARY/{username}/{password}")
	    @Produces({"application/json", "application/javascript"})
    @JSONP(queryParam = "callback")
public String Json_SMALL_SUMMARY(@PathParam("username") String username,@PathParam("password") String password) {
	if(CHECK_USER(username,password)) {
		return "GO AWAY";
	}
	si = new SystemInfo();

	   hal = si.getHardware();
	   os = si.getOperatingSystem();

	  computerSystem=  hal.getComputerSystem();
	  StatusData itemtemp=new StatusData();

	  itemtemp=CPU_LOADS(itemtemp );
	 
	  itemtemp=NETWORK(itemtemp);
	  return RETURN_GSON(itemtemp,"JSON_SMALL_SUMMARY");
	 
	
}
	  private boolean CHECK_USER(String username, String password) {
			if(Statics.USERS.keySet().size()==0) {
				return false;
			}
			else {
				Set<String> userkeys = Statics.USERS.keySet();
				for(String userN:userkeys) {
					if(userN==username) {
						String pass = Statics.USERS.get(username);
						if(pass==password) {
							return true;
						}
					}
				}
				
			}
			return false;
		}
private String RETURN_GSON(StatusData itemtemp,String type) {
	Gson gson = new Gson();
	String s=gson.toJson(itemtemp);
	try {
		boolean enter_db=false;
		if(Statics.last_updated_onDB==0) {
			enter_db=true;
		}
		else if((System.currentTimeMillis()-Statics.last_updated_onDB)>3600000) {
			enter_db=true;
		}
		
		if(enter_db) {
			Statics.last_updated_onDB=System.currentTimeMillis();
			PASS_DATA_TO_DB();
		
		}
	} 
	catch(Exception e) {
		logger.log(Level.SEVERE, e.getMessage());
	}
	
	return "callback("+s+");";
}
private void PASS_DATA_TO_DB() {
	
	try {
	
		Stat_Logs st=new Stat_Logs();
		Date d=new Date();
		st.setCurrent_timestamp(d);
		//st.setWhois(messageContext.get(MessageContext.HTTP_REQUEST_HEADERS).toString());
		st.setWhois("AUTOMATIC HOURLY APPLICATION LOG");
		st.setStat_type("HOURLY RECORD");
		st.setStatus_data(JsonStatusFULLx());
		 
	    // System.out.println(messageContext.get(MessageContext.HTTP_REQUEST_HEADERS)); 
	}
	catch(Exception e) {
		logger.log(Level.SEVERE, e.getMessage());
	}
}
private String JsonStatusFULLx() {
	
	
	
    si = new SystemInfo();

   hal = si.getHardware();
   os = si.getOperatingSystem();

  computerSystem=  hal.getComputerSystem();
  
  
StatusData itemtemp=new StatusData();

itemtemp=BASE_DATA(itemtemp );
itemtemp=CPU_LOADS(itemtemp );
itemtemp=TOPPROCESS(itemtemp,5 );
itemtemp=SENSORS(itemtemp );
itemtemp=POWERSOURCE(itemtemp );
itemtemp=DISKS(itemtemp);
itemtemp=USBS(itemtemp); 
itemtemp=DISPLAYS(itemtemp);
itemtemp=NETWORK(itemtemp);
itemtemp=NETWORK_PARAMS(itemtemp);
itemtemp=FILESYSTEM(itemtemp);

return RETURN_GSON(itemtemp,"JsonStatusFULL");
}
@GET
@Path("JsonStatusFULL/{username}/{password}")
@Produces({"application/json", "application/javascript"})
    @JSONP(queryParam = "callback")
public String JsonStatusFULL(@PathParam("username") String username,@PathParam("password") String password) {
	
	
	if(CHECK_USER(username,password)) {
		return "GO AWAY";
	}
    si = new SystemInfo();

   hal = si.getHardware();
   os = si.getOperatingSystem();

  computerSystem=  hal.getComputerSystem();
  
  
StatusData itemtemp=new StatusData();

itemtemp=BASE_DATA(itemtemp );
itemtemp=CPU_LOADS(itemtemp );
itemtemp=TOPPROCESS(itemtemp,5 );
itemtemp=SENSORS(itemtemp );
itemtemp=POWERSOURCE(itemtemp );
itemtemp=DISKS(itemtemp);
itemtemp=USBS(itemtemp); 
itemtemp=DISPLAYS(itemtemp);
itemtemp=NETWORK(itemtemp);
itemtemp=NETWORK_PARAMS(itemtemp);
itemtemp=FILESYSTEM(itemtemp);

return RETURN_GSON(itemtemp,"JsonStatusFULL");
}
private StatusData FILESYSTEM(StatusData itemtemp) {
	
	 
	 FileSystem fileSystem = os.getFileSystem();
	 itemtemp.FILEDESCRIPTOR=fileSystem.getOpenFileDescriptors();
		itemtemp.MAX_FILE_DESCRIPTOR=fileSystem.getMaxFileDescriptors();


		
		OSFileStore[] fsArray = fileSystem.getFileStores();

	 
	 for (OSFileStore fs : fsArray) {
	     long usable = fs.getUsableSpace();
	     long total = fs.getTotalSpace();
	     
	 	itemtemp.add_filesysx( usable,
				total,
				  fs.getName(),
				 fs.getLogicalVolume(),
				 fs.getType(),
				 fs.getDescription(),
				 fs.getVolume(),  fs.getMount());
	     
	 }
	return itemtemp; 

	 
}
private StatusData NETWORK_PARAMS(StatusData itemtemp) {
	NetworkParams networkParams = os.getNetworkParams();
	 itemtemp.HOST_NAME=networkParams.getHostName();
	 itemtemp.DOMAIN_NAME= networkParams.getDomainName();
	 itemtemp.DNS_SERVERS=Arrays.toString(networkParams.getDnsServers());
	 itemtemp.IPV4_GATEWAY=networkParams.getIpv4DefaultGateway();
	 itemtemp.IPV6_GATEWAY= networkParams.getIpv6DefaultGateway();
	  
return itemtemp;
}
private StatusData NETWORK(StatusData itemtemp) {
	 
	 NetworkIF[] networkIFs = hal.getNetworkIFs();
	 for (NetworkIF net : networkIFs) {
		 boolean hasData = net.getBytesRecv() > 0 || net.getBytesSent() > 0 || net.getPacketsRecv() > 0
	             || net.getPacketsSent() > 0;
		 itemtemp.ADD_NETWORKIF(
				 net.getName(),
				 net.getDisplayName(),
				 net.getMacaddr(),
				 net.getMTU()+"",
				
				 Arrays.toString(net.getIPv4addr()),
				 Arrays.toString(net.getIPv6addr()),
				( hasData ? net.getPacketsRecv()+""  : "?"),
				( hasData ? FormatUtil.formatBytes(net.getBytesRecv()) : "?"),
				(hasData ? net.getPacketsSent()+"": "?"),
				 (hasData ? FormatUtil.formatBytes(net.getBytesSent()) : "?"),
				( hasData ? " (" + net.getInErrors() + " err)" : ""),
						( hasData ? " (" + net.getOutErrors() + " err)" : ""),
		 FormatUtil.formatValue(net.getSpeed(), "bps")
		 
				 );
		 
	 }
	 return itemtemp;
}
private StatusData DISPLAYS(StatusData itemtemp) {
	 
	 final Display[] displays = hal.getDisplays();
	 for (Display display : displays) {
		  itemtemp.DISPLAYS.add(display.toString());
		   
	     
	 }
	 return itemtemp;
}
private StatusData USBS(StatusData itemtemp) {
	 final UsbDevice[] usbDevices = hal.getUsbDevices(true);
	 
	 for (UsbDevice usbDevice : usbDevices) {
	   itemtemp.USB_DEVICES.add(usbDevice.toString());
	 }
	 return itemtemp;
}
private StatusData DISKS(StatusData itemtemp) {

	 final HWDiskStore[] diskStores=hal.getDiskStores();
	 for(HWDiskStore p:diskStores) {
		 itemtemp.ADD_DISKSTORE(p);
		
	 }
		return itemtemp;
}
private StatusData POWERSOURCE(StatusData itemtemp) {

	 final PowerSource[] powerSources = hal.getPowerSources();
	 
	 for(PowerSource p:powerSources) {
		 
		 String status="";
			if(p.getTimeRemaining()<-1d)
				status="CHARGING";
			
			itemtemp.ADD_POWER_SOURCE (p.getTimeRemaining(),
					status, p.getName(), ( p.getRemainingCapacity() * 100d)+"");
		  
		
	 }
	 return itemtemp;
}
private StatusData SENSORS(StatusData itemtemp ) {

final Sensors sensors = hal.getSensors();
itemtemp.CPU_TEMP=  sensors.getCpuTemperature();
itemtemp.FAN_SPEEDS=  Arrays.toString(sensors.getFanSpeeds());
itemtemp.CPU_VOLTAGE= sensors.getCpuVoltage();
return itemtemp;
}
private StatusData TOPPROCESS(StatusData itemtemp, int count) {
	// TODO Auto-generated method stub
	GlobalMemory memory = hal.getMemory();
	List<OSProcess> procs = Arrays.asList(os.getProcesses(count, ProcessSort.CPU));

//  System.out.println("   PID  %CPU %MEM       VSZ       RSS Name");
String ss="";
for (int i = 0; i < procs.size() && i < 5; i++) {
    OSProcess p = procs.get(i);
    itemtemp.TOP5PROCESS.add( p.getProcessID()+".."+
            100d * (p.getKernelTime() + p.getUserTime()) / p.getUpTime()+".."+
            100d * p.getResidentSetSize() / memory.getTotal()+".."+ FormatUtil.formatBytes(p.getVirtualSize())+".."+
            FormatUtil.formatBytes(p.getResidentSetSize())+".."+ p.getName());
}

return itemtemp;
	
	
	
}

private StatusData BASE_DATA(StatusData itemtemp ) {
	itemtemp.MANUFACTURER= computerSystem.getManufacturer();
	itemtemp.MODEL= computerSystem.getModel();
	itemtemp.SERIAL= computerSystem.getSerialNumber();
	
	 final Firmware firmware = computerSystem.getFirmware();
	itemtemp.FIRMWARE_MANUFACTURER= firmware.getManufacturer();
	 itemtemp.FIRMWARE_NAME= firmware.getName();
	 itemtemp.FIRMWARE_DESCRIPTION= firmware.getDescription();
	 itemtemp.FIRMWARE_VERSION= firmware.getVersion();
	 
	 itemtemp.FIRMWARE_RELEASE_DATE= firmware.getReleaseDate();
	 
	final Baseboard baseboard = computerSystem.getBaseboard();
	 itemtemp.BASEBOARD_MANUFACTURER=  baseboard.getManufacturer();
	itemtemp.BASEBOARD_MODEL=   baseboard.getModel();
	itemtemp.BASEBOARD_VERSION=    baseboard.getVersion();
	itemtemp.BASEBOARD_SERIALNUMBER=    baseboard.getSerialNumber();
	
	
	
	 final CentralProcessor processor = hal.getProcessor();
	itemtemp.PROCESSOR=    processor.toString();
	itemtemp.PROCESSOR_PHYSICAL=    processor.getPhysicalProcessorCount() ;
	itemtemp.PROCESSOR_LOGICAL=    processor.getLogicalProcessorCount() ;
	itemtemp.PROCESSOR_IDENTIFIER=    processor.getIdentifier() ;
	itemtemp.PROCESSOR_ID=    processor.getProcessorID();
	

	final GlobalMemory memory = hal.getMemory();
itemtemp.MEMORY_AVALIABLE=    FormatUtil.formatBytes(memory.getAvailable());
itemtemp.MEMORY_TOTAL=    FormatUtil.formatBytes(memory.getTotal());
itemtemp.SWAP_AVALIABLE=    FormatUtil.formatBytes(memory.getSwapUsed()) ;
itemtemp.SWAP_TOTAL=    FormatUtil.formatBytes(memory.getSwapUsed()) ;




itemtemp.UPTIME= FormatUtil.formatElapsedSecs(processor.getSystemUptime());
return itemtemp;
}

private StatusData CPU_LOADS(StatusData itemtemp) {
	 final CentralProcessor processor = hal.getProcessor();
long[] prevTicks = processor.getSystemCpuLoadTicks();
System.out.println("CPU, IOWait, and IRQ ticks @ 0 sec:" + Arrays.toString(prevTicks));
// Wait a second...
Util.sleep(1000);
long[] ticks = processor.getSystemCpuLoadTicks();
System.out.println("CPU, IOWait, and IRQ ticks @ 1 sec:" + Arrays.toString(ticks));
long user = ticks[TickType.USER.getIndex()] - prevTicks[TickType.USER.getIndex()];
long nice = ticks[TickType.NICE.getIndex()] - prevTicks[TickType.NICE.getIndex()];
long sys = ticks[TickType.SYSTEM.getIndex()] - prevTicks[TickType.SYSTEM.getIndex()];
long idle = ticks[TickType.IDLE.getIndex()] - prevTicks[TickType.IDLE.getIndex()];
long iowait = ticks[TickType.IOWAIT.getIndex()] - prevTicks[TickType.IOWAIT.getIndex()];
long irq = ticks[TickType.IRQ.getIndex()] - prevTicks[TickType.IRQ.getIndex()];
long softirq = ticks[TickType.SOFTIRQ.getIndex()] - prevTicks[TickType.SOFTIRQ.getIndex()];
long steal = ticks[TickType.STEAL.getIndex()] - prevTicks[TickType.STEAL.getIndex()];
long totalCpu = user + nice + sys + idle + iowait + irq + softirq + steal;
itemtemp.PROCESSOR_USER=   user;
itemtemp.PROCESSOR_NICE=   nice;
itemtemp.PROCESSOR_SYS=   sys;
itemtemp.PROCESSOR_IDLE=   idle;
itemtemp.PROCESSOR_IOWAIT=   iowait;
itemtemp.PROCESSOR_IRQ=   irq;
itemtemp.PROCESSOR_SOFTIRQ=   softirq;
itemtemp.PROCESSOR_STEAL=   steal;
itemtemp.PROCESSOR_TOTALCPU=   totalCpu;
itemtemp.PROCESSOR_LOADBETWEENTICKS= processor.getSystemCpuLoadBetweenTicks() * 100;
itemtemp.PROCESSOR_SYSTEMCPULOAD= processor.getSystemCpuLoad() * 100;




// per core CPU

double[] load = processor.getProcessorCpuLoadBetweenTicks();
for (double avg : load) {
	
    itemtemp.LOAD_PER_CORE.add((avg*100)+"");
}


 


itemtemp.PROCESS_COUNT= os.getProcessCount();
itemtemp.PROCESS_THREADCOUNT= os.getThreadCount();
	return itemtemp;
}

    
    
    
}
