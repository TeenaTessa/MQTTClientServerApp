package com.example.mqttapp;

import javax.jcr.*;
import javax.jcr.lock.LockException;
import javax.jcr.nodetype.ConstraintViolationException;
import javax.jcr.nodetype.NoSuchNodeTypeException;
import javax.jcr.version.VersionException;

import java.sql.*;
import java.util.*;

import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.sling.api.resource.ResourceResolver;
import org.apache.sling.api.resource.ResourceResolverFactory;
//import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
//import org.eclipse.paho.client.mqttv3.MqttCallback;
//import org.eclipse.paho.client.mqttv3.MqttClient;
//import org.eclipse.paho.client.mqttv3.MqttException;
//import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.*;
import org.json.JSONObject;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;

public class MqttServer {

//	private static final String MQTT_BROKER = "tcp://localhost:1883";
//	private static final String MQTT_TOPIC = "iot/device/status";
//	private static final String JACKRABBIT_URL = "http://localhost:8080/repository/default";
	private static final String MQTT_BROKER = AppConfig.getProperty("mqtt.broker");
	private static final String MQTT_TOPIC = AppConfig.getProperty("mqtt.topic");
//	private static final String JACKRABBIT_URL =AppConfig.getProperty("jackrabbit.url");;

	public static void main(String[] args) throws InterruptedException {

		try {
			String clientId = MqttClient.generateClientId();
			MqttClient client = new MqttClient(MQTT_BROKER, clientId);

			// Create MQTT callback to handle incoming messages
			client.setCallback(new MqttCallback() {
				@Override
				public void connectionLost(Throwable cause) {
					cause.printStackTrace();
				}

				@Override
				public void messageArrived(String topic, MqttMessage message) throws Exception {
					JSONObject sensordata = new JSONObject(new String(message.getPayload()));
					new JDBCConnection().sendJSONAttributesToDatabase(sensordata);
					new JackRabbitIntegration().sendtojackrabbitrepository(sensordata);
//					System.out.println("Received JSON: " + json.toString());
//					String messageId = json.getString("messageId");
//					Boolean isActive = json.getBoolean("isActive");
//					int batteryLevel = json.getInt("batteryLevel");
//					String location = json.getString("location");
//
//					String jdbcUrl = "jdbc:mysql://localhost:3306/statuses?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC";
//					String username = "root";
//					String password = "admin";
//
//					try {
//						// Establish JDBC connection
//						Connection conn = DriverManager.getConnection(jdbcUrl, username, password);
//
//						// Prepare SQL INSERT statement
//						String sql = "INSERT INTO sensordata (isActive, batteryLevel, location, messageId) VALUES (?, ?, ?, ?)";
//						PreparedStatement statement = conn.prepareStatement(sql);
//						statement.setString(4, messageId);
//						statement.setBoolean(1, isActive);
//						statement.setInt(2, batteryLevel);
//						statement.setString(3, location);
//
//						// Execute INSERT statement
//						int rowsInserted = statement.executeUpdate();
//						if (rowsInserted > 0) {
//							System.out.println("Data inserted successfully.");
//						} else {
//							System.out.println("Failed to insert data.");
//						}
//						statement.close();
//						conn.close();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}


				}

				@Override
				public void deliveryComplete(IMqttDeliveryToken token) {
					// TODO Auto-generated method stub

				}

			});
			client.connect();
			client.subscribe(MQTT_TOPIC);

			System.out.println("Listening for MQTT messages...");
			while (true) {
				Thread.sleep(1000); // Sleep to keep the program alive
			}

		} catch (MqttException e) {
			e.printStackTrace();
		}

	}

}
