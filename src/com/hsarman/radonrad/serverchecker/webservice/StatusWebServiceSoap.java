package com.hsarman.radonrad.serverchecker.webservice;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.BindingType;
import javax.xml.ws.Endpoint;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hsarman.radonrad.serverchecker.database_utils.models.Stat_Logs;
import com.hsarman.radonrad.serverchecker.utils.Statics;
import com.hsarman.radonrad.serverchecker.webservice.StatusData.POWERSOURCE;

import oshi.SystemInfo;
import oshi.hardware.Baseboard;
import oshi.hardware.CentralProcessor;
import oshi.hardware.CentralProcessor.TickType;
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
import oshi.software.os.FileSystem;
import oshi.software.os.NetworkParams;
import oshi.software.os.OSFileStore;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.software.os.OperatingSystem.ProcessSort;
import oshi.util.FormatUtil;
import oshi.util.Util;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;
import javax.jws.soap.SOAPBinding.Use;
@WebService
@SOAPBinding(style = Style.DOCUMENT, use=Use.LITERAL) 
public class StatusWebServiceSoap {
	static Logger logger = Logger.getLogger( StatusWebServiceSoap.class.getName() );
	 @Resource
	  public WebServiceContext webServiceContext; 
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
	
	

	
	@WebMethod
	public String FIXED_REFRESHED_FULL_DATA(String username,String password) {
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
	
	@WebMethod
	public String FIXED_REFRESHED_IMPORTANT_SUMMARY(String username,String password) {
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
	
	@WebMethod
	public String FIXED_REFRESHED_SMALL_SUMMARY(String username,String password) {
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
	
	@WebMethod
 public String JsonBASE_INFO(String username,String password) {
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
	@WebMethod
public String Json_CPU_LOADS(String username,String password,int input) {
	
	si = new SystemInfo();

	   hal = si.getHardware();
	   os = si.getOperatingSystem();

	  computerSystem=  hal.getComputerSystem();
	  StatusData itemtemp=new StatusData();
	  itemtemp=CPU_LOADS(itemtemp);
	  	
	  return RETURN_GSON(itemtemp,"JSON_CPU_LOADS");
}
	@WebMethod
public String Json_PROCESSES(String username,String password,int input) {
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
	@WebMethod
public String Json_SENSORS(String username,String password) {
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
	@WebMethod
public String Json_POWERSOURCE(String username,String password) {
	
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
	@WebMethod
public String Json_DISKS(String username,String password) {
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
	@WebMethod
public String Json_USBS(String username,String password) {
	
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
	@WebMethod
public String Json_DISPLAYS(String username,String password) {
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
	@WebMethod
public String Json_NETWORK(String username,String password) {
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
	@WebMethod
public String Json_NETWORK_PARAMS(String username,String password) {
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
	@WebMethod
public String Json_FILESYSTEM(String username,String password) {
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
	@WebMethod
public String Json_IMPORTANT_SUMMARY(String username,String password) {
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
	@WebMethod
public String Json_SMALL_SUMMARY(String username,String password) {
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
		try {
		MessageContext messageContext = webServiceContext.getMessageContext();
		
		logger.log(Level.INFO, type+"&&&&&&&&&&&&&&&"+messageContext.get(MessageContext.HTTP_REQUEST_HEADERS)+"&&&&&&&&&&&&&&&"+s);
		

	     // Send some headers back in the response message.
	     Map<String, String> responseHeaders = new HashMap<String, String>();
	     responseHeaders.put("Access-Control-Allow-Origin", "*");
	     responseHeaders.put("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
	     responseHeaders.put("Access-Control-Allow-Credentials", "true");
	     responseHeaders.put("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
	     responseHeaders.put("Access-Control-Max-Age", "1209600");
	    messageContext.putAll(responseHeaders);
	     //messageContext.put(com.ibm.websphere.webservices.Constants.RESPONSE_TRANSPORT_PROPERTIES, responseHeaders);

		}
		catch(Exception e) {}
		
		}
	catch(Exception e) {
		logger.log(Level.SEVERE, e.getMessage());
	}
	
	return s;
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
@WebMethod
public String JsonStatusFULL(String username,String password) {
	
	
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