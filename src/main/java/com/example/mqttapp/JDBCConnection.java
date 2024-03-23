package com.example.mqttapp;

import java.sql.*;

import org.json.JSONObject;

public class JDBCConnection {

//	private static String jdbcUrl = "jdbc:mysql://localhost:3306/statuses?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
//	private static String username = "root";
//	private static String password = "admin";
	private static String jdbcUrl = AppConfig.getProperty("jdbc.url");
	private static String username = AppConfig.getProperty("jdbc.username");
	private static String password = AppConfig.getProperty("jdbc.password");

	public void sendJSONAttributesToDatabase(JSONObject sensordata) {
		String messageId = sensordata.getString("messageId");
		Boolean isActive = sensordata.getBoolean("isActive");
		int batteryLevel = sensordata.getInt("batteryLevel");
		String location = sensordata.getString("location");
		try {
			// Establish JDBC connection
			Connection conn = DriverManager.getConnection(jdbcUrl, username, password);

			// Prepare SQL INSERT statement
			String sql = "INSERT INTO sensordata (isActive, batteryLevel, location, messageId) VALUES (?, ?, ?, ?)";
			PreparedStatement statement = conn.prepareStatement(sql);
			statement.setString(4, messageId);
			statement.setBoolean(1, isActive);
			statement.setInt(2, batteryLevel);
			statement.setString(3, location);

			// Execute INSERT statement
			int rowsInserted = statement.executeUpdate();
			if (rowsInserted > 0) {
				System.out.println("Data inserted successfully.");
			} else {
				System.out.println("Failed to insert data.");
			}
			statement.close();
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
