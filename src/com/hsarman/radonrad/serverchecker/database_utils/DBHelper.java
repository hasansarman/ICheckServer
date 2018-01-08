package com.hsarman.radonrad.serverchecker.database_utils;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.hsarman.radonrad.serverchecker.database_utils.models.Stat_Logs;
import com.hsarman.radonrad.serverchecker.webservice.StatusWebServiceSoap;



public class DBHelper {
	static Logger logger = Logger.getLogger( DBHelper.class.getName() );
	private static  DBHelper instance;
	private static DBModel model;

	public static DBHelper get_instance() {
		if(instance==null) {
			new DBHelper();
		}
		return instance;
	}
	public DBHelper() {
		instance=this;
		try {
			DBModel model=new DBModel();
			this.model=model;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.log(Level.SEVERE, e.getMessage());
			e.printStackTrace();
		} 
	}
public void TEST() {
	try {
		List<Stat_Logs> cars = model.getObjectModel(Stat_Logs.class)
				// Call get all cars with a milege > 50000
						.getAll("id > ?", 0);
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		logger.log(Level.SEVERE, e.getMessage());
		e.printStackTrace();
	}

}
}
