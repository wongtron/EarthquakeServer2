package com.leetron;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import java.sql.*;

public class EarthquakeDataUtil {
	
	private DataSource dataSource;
	private String sql;

	public EarthquakeDataUtil(DataSource theDataSource) {
		dataSource = theDataSource;
	}
	
	public List<Earthquake> getEarthquakes(
			String startYear,
			String startMonth,
			String  startDate,
			String endYear,
			String endMonth,
			String endDate,
			String minMagnitude,
			String maxMagnitude,
			String searchLocLat, 
			String searchLocLong, 
			String radius
			) throws Exception {
		
		List<Earthquake> earthquakes = new ArrayList<>();
		
		Connection myConn = null;
		Statement myStmt = null;
		ResultSet myRs = null;
		
		try {
			// get a connection
			myConn = dataSource.getConnection();
			
			// create sql statement
			// sql = "select * from eq1 where `event_date` >= '1950-01-01' and event_date <= '2016-01-01' and mag >= 8.0";
			/*
			sql = "SELECT * FROM eq1 where `event_date` >= '";
			sql += startYear + "-" + startMonth + "-" + startDate + "'";
			sql += " AND `event_date` <= '";
			sql += endYear + "-" + endMonth + "-" + endDate + "'";
			sql += " AND mag >=" + minMagnitude + " AND mag <=" + maxMagnitude;  
			*/
			/* Tried the following query on MySQL and it worked 
			SELECT _id,event_date,event_time, latitude,longitude,depth,mag, magtype, 
			nst, gap, dmin,rms,net,id,updated_date,updated_time, place,type,
			horizontalerror,deptherror,magerror,magnst,status, locationsource, 
			magsource, 
			(3959*acos(cos(radians('63.438')) * cos(radians(latitude)) * 
			cos(radians('-147.5496')-radians(longitude)) +sin(radians('63.438')) * 
			sin(radians(latitude)))) AS distance 
			FROM eq1 where `event_date` >= '2015-01-31' AND 
			`event_date` <= '2015-12-02' AND mag >='0.0' AND mag <='10.0' 
			HAVING distance <='100'
			*/
			sql = "SELECT " +
					"_id,event_date,event_time, latitude,longitude,depth,mag," +
					"magtype, nst, gap, dmin,rms,net,id,updated_date,updated_time," +
					"place,type,horizontalerror,deptherror,magerror,magnst,status," +
					"locationsource, magsource"; 
					
			
			if ((searchLocLat != null) &&  (!searchLocLat.equals(""))) {
				// add the radius search in kilometers by using the Haversine formula
				sql += ", (1.609344*3959*acos(cos(radians('" + searchLocLat +
						"')) * cos(radians(latitude)) * cos(radians('" + searchLocLong +
						"')-radians(longitude)) +sin(radians('" + searchLocLat + 
						"')) * sin(radians(latitude)))) AS distance ";
			}
			sql += " FROM eq1 where `event_date` >= '";
			sql += startYear + "-" + startMonth + "-" + startDate + "'";
			sql += " AND `event_date` <= '";
			sql += endYear + "-" + endMonth + "-" + endDate + "'";
			sql += " AND mag >=" + minMagnitude + " AND mag <=" + maxMagnitude;
			
			if ((searchLocLat != null) &&  (!searchLocLat.equals(""))) {
				sql += " HAVING distance <=" + radius;
			}
			
			sql += " ORDER BY `event_date`, `event_time`;"; 
			
			myStmt = myConn.createStatement();
			
			// execute query
			myRs = myStmt.executeQuery(sql);
			
			// process result set
			while (myRs.next()) {
				
				// retrieve data from result set row
				String _id = myRs.getInt("_id")+"";
				String event_date = myRs.getDate("event_date")+"";
				String event_time = myRs.getTime("event_time")+"";
				String latitude  = myRs.getDouble("latitude")+"";
				String longitude  = myRs.getDouble("longitude")+"";
				String depth  = myRs.getDouble("depth")+"";
				String mag  = myRs.getDouble("mag")+"";
				String magtype = myRs.getString("magtype");
				String nst = myRs.getInt("nst")+"";
				String gap  = myRs.getDouble("gap")+"";
				String dmin  = myRs.getDouble("dmin")+"";
				String rms  = myRs.getDouble("rms")+"";
				String net = myRs.getString("net");
				String id = myRs.getString("id");
				String updated_date = myRs.getDate("updated_date")+"";
				String updated_time = myRs.getTime("updated_time")+"";
				String place = myRs.getString("place");
				String type = myRs.getString("type");
				String horizontalerror  = myRs.getDouble("horizontalerror")+"";
				String deptherror  = myRs.getDouble("deptherror")+"";
				String magerror  = myRs.getDouble("magerror")+"";
				String magnst = myRs.getInt("magnst")+"";
				String status = myRs.getString("status");
				String locationsource = myRs.getString("locationsource");
				String magsource = myRs.getString("magsource");
				
				// create new student object
				Earthquake tempEarthquake = new Earthquake (
						_id, event_date, event_time,
						latitude, longitude, depth,mag,magtype, nst, gap,dmin,
						rms,net,id,updated_date,updated_time,place,type, horizontalerror, deptherror, magerror,
						magnst, status,locationsource,magsource
						);
				
				// add it to the list of students
				earthquakes.add(tempEarthquake);				
			}
			
			return earthquakes;		
		}
		finally {
			// close JDBC objects
			close(myConn, myStmt, myRs);
		}		
	}

	private void close(Connection myConn, Statement myStmt, ResultSet myRs) {

		try {
			if (myRs != null) {
				myRs.close();
			}
			
			if (myStmt != null) {
				myStmt.close();
			}
			
			if (myConn != null) {
				myConn.close();   // doesn't really close it ... just puts back in connection pool
			}
		}
		catch (Exception exc) {
			exc.printStackTrace();
		}
	}
}
