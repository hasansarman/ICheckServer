package com.hsarman.radonrad.serverchecker.database_utils.models;

import java.util.Date;

import za.co.neilson.sqlite.orm.annotations.ForeignKey;
import za.co.neilson.sqlite.orm.annotations.PrimaryKey;

public class Stat_Logs {
	
	@PrimaryKey(autoIncrement = true)
	private  int id;
	private String stat_type;
	private String status_data;
	
	private String whois;
	private Date current_timestamp;
	
	
	
	
	
	    public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getStat_type() {
		return stat_type;
	}
	public void setStat_type(String stat_type) {
		this.stat_type = stat_type;
	}
	public String getStatus_data() {
		return status_data;
	}
	public void setStatus_data(String status_data) {
		this.status_data = status_data;
	}
	
	public String getWhois() {
		return whois;
	}
	public void setWhois(String whois) {
		this.whois = whois;
	}
	public Date getCurrent_timestamp() {
		return current_timestamp;
	}
	public void setCurrent_timestamp(Date current_timestamp) {
		this.current_timestamp = current_timestamp;
	}
		
}
 