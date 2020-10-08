package com.database

import com.kms.katalon.core.annotation.Keyword
import groovy.sql.Sql

public class ConnectionManager {
	private static Sql sql = null;

	/**
	 * Open and return a connection to database
	 *
	 * @param dataFile absolute file path
	 * @return an instance of groovy.sql.Sql
	 */

	//Establishing a connection to the DataBase
	@Keyword
	def connectDB(String url, String dbname, String port, String username, String password) {
		//Load driver class for your specific database type
		String conn = "jdbc:mysql://" + url + ":" + port + "/" + dbname
		Class.forName("com.mysql.jdbc.Driver");
		if (sql != null && !sql.isClosed()) {

			sql.close()
		}
		sql = Sql.newInstance(conn, username, password)
		sql.connection.autoCommit = false
		return sql
	}

	/**
	 * execute a SQL query on database
	 *
	 * @param queryString SQL query string
	 * @return a reference to returned data collection, an instance of java.sql.ResultSet
	 */

	//Executing the constructed Query and Saving results in resultSet
	@Keyword
	def retrievingDataFromQuery(String queryString, String value) {
		String data = null
		sql.query(queryString) { resultSet ->
			while (resultSet.next()) {
				data = resultSet.getString(value)
			}
		}
		return data
	}

	//Closing the connection
	@Keyword
	def closeDatabaseConnection() {
		if (sql.close() != null && !sql.close().isClosed()) {
			sql.close().close()
		}
	}
}