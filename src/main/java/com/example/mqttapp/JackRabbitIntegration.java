package com.example.mqttapp;

import org.apache.jackrabbit.JcrConstants;
import org.apache.jackrabbit.commons.JcrUtils;
import org.apache.jackrabbit.jcr2spi.Jcr2spiRepositoryFactory;
import org.apache.jackrabbit.value.StringValue;
import org.json.JSONObject;

import javax.jcr.*;

public class JackRabbitIntegration {

//	private static final String REPOSITORY_URL = "http://localhost:8080/server";
//	private static final String USERNAME = "admin";
//	private static final String PASSWORD = "admin";
	
	private static final String REPOSITORY_URL =  AppConfig.getProperty("repository.url");;
	private static final String USERNAME = AppConfig.getProperty("repository.username");
	private static final String PASSWORD = AppConfig.getProperty("repository.password");

	public void sendtojackrabbitrepository(JSONObject json) throws RepositoryException {
		// Initialize the JCR repository session
		Repository repository = JcrUtils.getRepository(REPOSITORY_URL);
		Session session = repository.login(new SimpleCredentials(USERNAME, PASSWORD.toCharArray()), "default");
		System.out.println("logged in to repository");
		String messageId = json.getString("messageId");
		String location = json.getString("location");
		boolean isActive = json.getBoolean("isActive");
		int batteryLevel = json.getInt("batteryLevel");

		try {
			// Create a new node in the repository
			Node rootNode = session.getRootNode();
			Node documentNode = rootNode.addNode(messageId).addNode("sensordata");
			// Convert JSON object to a document format
			// Set properties for each attribute in the JSON object

			documentNode.setProperty("location", new StringValue(location));
			documentNode.setProperty("isActive", isActive);
			documentNode.setProperty("batteryLevel", batteryLevel);

			// Save the changes
			session.save();
			System.out.println("Document stored in Jackrabbit repository successfully.");

		} finally {
			// Clean up resources
			if (session != null) {
				session.logout();
			}
		}
	}
}
