package com.hsarman.radonrad.serverchecker.database_utils;


import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Logger;

import com.hsarman.radonrad.serverchecker.database_utils.models.Stat_Logs;


import com.hsarman.radonrad.serverchecker.utils.Statics;

import za.co.neilson.sqlite.orm.DatabaseDriverInterface;
import za.co.neilson.sqlite.orm.DatabaseInfo;
import za.co.neilson.sqlite.orm.DatabaseModel;
import za.co.neilson.sqlite.orm.ObjectModel;
import za.co.neilson.sqlite.orm.jdbc.JdbcObjectModel;
import za.co.neilson.sqlite.orm.jdbc.JdbcSqliteDatabaseDriverInterface;
 

public class DBModel extends
		DatabaseModel<ResultSet, HashMap<String, Object>> {
	static Logger logger = Logger.getLogger( DBModel.class.getName() );
	public DBModel() throws SQLException, ClassNotFoundException,
			NoSuchFieldException {
		super((Object[]) null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * za.co.neilson.sqlite.orm.DatabaseModel#onInitializeDatabaseDriverInterface
	 * ()
	 */
	@Override
	protected DatabaseDriverInterface<ResultSet, HashMap<String, Object>> onInitializeDatabaseDriverInterface(
			Object... args) {
		return new JdbcSqliteDatabaseDriverInterface(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.co.neilson.sqlite.orm.DatabaseModel#onCreateDatabaseInfoModel()
	 */
	@Override
	public ObjectModel<DatabaseInfo, ResultSet, HashMap<String, Object>> onCreateDatabaseInfoModel()
			throws ClassNotFoundException, NoSuchFieldException {
		return new JdbcObjectModel<DatabaseInfo>(this) {
		};
	}

	
	@Override
	protected void onRegisterObjectModels(
			HashMap<Type, ObjectModel<?, ResultSet, HashMap<String, Object>>> objectModels)
			throws ClassNotFoundException, NoSuchFieldException {
		

		
		objectModels.put(Stat_Logs.class, new JdbcObjectModel<Stat_Logs>(this) {
		});

	
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.co.neilson.sqlite.orm.DatabaseModel#getDatabaseName()
	 */
	@Override
	public String getDatabaseName() {
		return Statics.DB_NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.co.neilson.sqlite.orm.DatabaseModel#getDatabaseVersion()
	 */
	@Override
	public int getDatabaseVersion() {
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.co.neilson.sqlite.orm.DatabaseModel#onCreate()
	 */
	@Override
	protected void onCreate() throws SQLException {
		super.onCreate();
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.co.neilson.sqlite.orm.DatabaseModel#onUpgrade(int)
	 */
	@Override
	protected void onUpgrade(int previousVersion) throws SQLException {
		super.onUpgrade(previousVersion);
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see za.co.neilson.sqlite.orm.DatabaseModel#onInsertDefaultValues()
	 */
	@Override
	protected void onInsertDefaultValues() {
}
}
