package com.hsarman.radonrad.serverchecker.webservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.json.JSONObject;
import org.threeten.bp.LocalDate;

import oshi.SystemInfo;
import oshi.hardware.Baseboard;
import oshi.hardware.CentralProcessor;
import oshi.hardware.Firmware;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.HWPartition;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.hardware.PowerSource;
import oshi.hardware.Sensors;
import oshi.hardware.CentralProcessor.TickType;
import oshi.software.os.OSProcess;
import oshi.software.os.OperatingSystem;
import oshi.software.os.OperatingSystem.ProcessSort;
import oshi.util.FormatUtil;
import oshi.util.Util;

public class StatusData {
	 

	public String MANUFACTURER;
	public   String NETWORK_IN_SPEED="0";
	public   String NETWORK_OUT_SPEED="0";
	public   long LAST_BYTES_RECV_TOTAL=0;
	 public   long LAST_BYTES_SENT_TOTAL=0;
	public String MODEL;
	public String SERIAL;
	public String FIRMWARE_MANUFACTURER;
	public String FIRMWARE_NAME;
	public String FIRMWARE_DESCRIPTION;
	public String FIRMWARE_VERSION;
	public LocalDate FIRMWARE_RELEASE_DATE;
	public String BASEBOARD_MANUFACTURER;
	public String BASEBOARD_MODEL;
	public String BASEBOARD_VERSION;
	public String BASEBOARD_SERIALNUMBER;
	public String PROCESSOR;
	public int PROCESSOR_PHYSICAL;
	public int PROCESSOR_LOGICAL;
	public String PROCESSOR_IDENTIFIER;
	public String PROCESSOR_ID;
	public String MEMORY_AVALIABLE;
	public String MEMORY_TOTAL;
	public String SWAP_AVALIABLE;
	public String SWAP_TOTAL;
	public String UPTIME;
	
	public long PROCESSOR_USER;
	public long PROCESSOR_NICE;
	public long PROCESSOR_SYS;
	public long PROCESSOR_IDLE;
	public long PROCESSOR_IOWAIT;
	public long PROCESSOR_IRQ;
	public long PROCESSOR_SOFTIRQ;
	public long PROCESSOR_STEAL;
	public long PROCESSOR_TOTALCPU;
	public double PROCESSOR_LOADBETWEENTICKS;
	public double PROCESSOR_SYSTEMCPULOAD;
	ArrayList<String> LOAD_PER_CORE=new ArrayList<String>();
	public int PROCESS_COUNT;
	public int PROCESS_THREADCOUNT;
	ArrayList<String> TOP5PROCESS=new ArrayList<String>();
	public double CPU_TEMP;
	public double CPU_VOLTAGE;
	public String FAN_SPEEDS;
	ArrayList<POWERSOURCE> POWERSOURCES=new ArrayList<POWERSOURCE>();
	ArrayList<DISKSTORES> DISKS=new ArrayList<DISKSTORES>();
	ArrayList<String> USB_DEVICES=new ArrayList<String>();
	ArrayList<String> DISPLAYS=new ArrayList<String>();
	ArrayList<NETWORKIF> NETWORK=new ArrayList<NETWORKIF>();
	ArrayList<FILESYSX> FILESYS=new ArrayList<FILESYSX>();
	public String HOST_NAME;
	public String DOMAIN_NAME;
	public String DNS_SERVERS;
	public String IPV4_GATEWAY;
	public String IPV6_GATEWAY;
	long FILEDESCRIPTOR;
	long MAX_FILE_DESCRIPTOR;
	public StatusData( ) {
		
		
		
	}

	 
	
	
	
		
		

		public void add_filesysx(long USABLESPACE,
				long TOTALSPACE,
				String NAME,
				String LOGICALVOLUME,
				String TYPE,
				String DESCRIPTION,
				String VOLUME,
				String MOUNT) {
			FILESYS.add(new FILESYSX(
					  USABLESPACE,
					  TOTALSPACE,
					  NAME,
					  LOGICALVOLUME,
					  TYPE,
					  DESCRIPTION,
					  VOLUME,
					  MOUNT
					));
			
		}
		public class FILESYSX{
			long USABLESPACE;
			long TOTALSPACE;
			String NAME;
			String LOGICALVOLUME;
			String TYPE;
			String DESCRIPTION;
			String VOLUME;
			String MOUNT;
			public FILESYSX(long USABLESPACE,
			long TOTALSPACE,
			String NAME,
			String LOGICALVOLUME,
			String TYPE,
			String DESCRIPTION,
			String VOLUME,
			String MOUNT) {
				this.USABLESPACE=USABLESPACE;
				this.TOTALSPACE=TOTALSPACE;
				this.NAME=NAME;
				this.LOGICALVOLUME=LOGICALVOLUME;
				this.TYPE=TYPE;
				this.DESCRIPTION=DESCRIPTION;
				this.VOLUME=VOLUME;
				this.MOUNT=MOUNT;
			}
			
		}
		
	
	
	
	

	public void ADD_POWER_SOURCE(double time,String status,String name,String remaining_capacity) {
		
		this.POWERSOURCES.add(new POWERSOURCE(time, status, name, remaining_capacity));
		
	}


	public void ADD_DISKSTORE(HWDiskStore disk) {
		

		
		 
	            boolean readwrite = disk.getReads() > 0 || disk.getWrites() > 0;
	            String disze = disk.getSize() > 0 ? FormatUtil.formatBytesDecimal(disk.getSize()) : "?";
	           String reads= readwrite ? disk.getReads()+"" : "?"+"   "+(readwrite ? FormatUtil.formatBytes(disk.getReadBytes()) : "?");
 	               String writes=     readwrite ? disk.getWrites()+"" : "?"+"    "+ (readwrite ? FormatUtil.formatBytes(disk.getWriteBytes()) : "?");
 	                   String transfertime= readwrite ? disk.getTransferTime()+"" : "?";
	            
	            DISKSTORES d=new DISKSTORES(disk.getName(), disk.getModel(), disk.getSerial(),
	            		 disze,reads,writes,transfertime);
	            
	           
	            HWPartition[] partitions = disk.getPartitions();
	            if (partitions != null) {
	            	   for (HWPartition part : partitions) {
	            		   d.ADD_PARTITION( part.getName(), part.getType(), part.getMajor()+"", part.getMinor()+"",
	            				   FormatUtil.formatBytesDecimal(part.getSize()),
	            				   part.getMountPoint().isEmpty() ? "" : " @ " + part.getMountPoint());
	   	               
	   	            }
	   	        
	            }
	         
	    
	            	DISKS.add(d);
		
		
		
	}
	public void ADD_NETWORKIF( String NAME,
	 String DISPLAYNAME,
	 String MAC,
	 String MTU,
	 String IPV4,
	 String IPV6,
	 String PACKET_RCV,
	 String BYTES_RCV,
	 String PACKET_SENT,
	 String BYTES_SENT,
	 String RCV_ERRORS,
	 String SENT_ERRORS,
	 String SPEED) {
		NETWORK.add(new NETWORKIF(NAME, DISPLAYNAME, MAC, MTU, IPV4, 
				IPV6, PACKET_RCV, BYTES_RCV, PACKET_SENT, BYTES_SENT, RCV_ERRORS, SENT_ERRORS,SPEED));

		
	}
	public class NETWORKIF {
		 String NAME;
		 String DISPLAYNAME;
		 String MAC;
		 String MTU;
		 String IPV4;
		 String IPV6;
		 String PACKET_RCV;
		 String BYTES_RCV;
		 String PACKET_SENT;
		 String BYTES_SENT;
		 String RCV_ERRORS;
		 String SENT_ERRORS;
		 String SPEED;
		 public NETWORKIF( String NAME,
 String DISPLAYNAME,
 String MAC,
 String MTU,
 String IPV4,
 String IPV6,
 String PACKET_RCV,
 String BYTES_RCV,
 String PACKET_SENT,
 String BYTES_SENT,
 String RCV_ERRORS,
 String SENT_ERRORS,
 String SPEED) {
			 
			 this.NAME=NAME;
			 this.DISPLAYNAME=DISPLAYNAME;
			 this.MAC=MAC;
			 this.MTU=MTU;
			 this.IPV4=IPV4;
			 this.IPV6=IPV6;
			 this.PACKET_RCV=PACKET_RCV;
			 this.PACKET_SENT=PACKET_SENT;
			 this.BYTES_RCV=BYTES_RCV;
			 this.BYTES_SENT=BYTES_SENT;
			 this.RCV_ERRORS=RCV_ERRORS;
			 this.SENT_ERRORS=SENT_ERRORS;
			 this.SPEED=SPEED;
		 }
		
		
		
	}
	
	public class DISKSTORES  {
		String NAME;
		String MODEL;
		String SERIAL;
		String SIZE;
		String READS;
		String WRITES;
		String TRANSFERTIME;
		public DISKSTORES(String NAME,
		String MODEL,
		String SERIAL,
		String SIZE,
		String READS,
		String WRITES,
		String TRANSFERTIME) {
			this.NAME=NAME;
			this.MODEL=MODEL;
			this.SERIAL=SERIAL;
			this.SIZE=SIZE;
			this.READS=READS;
			this.WRITES=WRITES;
			this.TRANSFERTIME=TRANSFERTIME;
			
		}
		ArrayList< Partition> parts=new ArrayList< Partition>();
		public void ADD_PARTITION(String NAME,
		String TYPE,
		String MAJOR,
		String MINOR,
		String SIZE,
		String MOUNTPOINT) {
			parts.add(new Partition(NAME,TYPE,MAJOR,MINOR,SIZE,MOUNTPOINT));
			
		}
		public class Partition{
			String NAME;
			String TYPE;
			String MAJOR;
			String MINOR;
			String SIZE;
			String MOUNTPOINT;
			public Partition(String NAME,
		String TYPE,
		String MAJOR,
		String MINOR,
		String SIZE,
		String MOUNTPOINT) {
				this.NAME=NAME;
				this.TYPE=TYPE;
				this.MAJOR=MAJOR;
				this.MINOR=MINOR;
				this.SIZE=SIZE;
				this.MOUNTPOINT=MOUNTPOINT;
			}
		}
		
	}
	public class POWERSOURCE{
		double TIME_REMAINING;
		String STATUS;
		String NAME;
		String REMAINING_CAPACITY;
		public POWERSOURCE(double time,String status,String name,String remaining_capacity) {
			this.TIME_REMAINING=time;
			this.STATUS=status;
			this.NAME=name;
			this.REMAINING_CAPACITY=remaining_capacity;
		}
	}

	
}
