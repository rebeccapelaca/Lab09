package it.polito.tdp.metrodeparis.dao;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.mchange.v2.c3p0.DataSources;

public class DBConnect {

	static private final String jdbcUrl = "jdbc:mysql://localhost/metroparis?user=root";
	private static DataSource ds;
	
	public static Connection getConnection() {
		
		if(ds==null) {
			try {
				ds = DataSources.pooledDataSource(
						DataSources.unpooledDataSource(jdbcUrl));
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		try {
			Connection c = ds.getConnection() ;
			return c ;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
}
