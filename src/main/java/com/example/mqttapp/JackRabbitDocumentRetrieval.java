package com.example.mqttapp;

import javax.jcr.*;
import org.apache.jackrabbit.commons.JcrUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class JackRabbitDocumentRetrieval {
	private static final String REPOSITORY_URL =  AppConfig.getProperty("repository.url");;
	private static final String USERNAME = AppConfig.getProperty("repository.username");
	private static final String PASSWORD = AppConfig.getProperty("repository.password");
	public static void main(String[] args)throws RepositoryException {
		    // Initialize the JCR repository session
		    Repository repository = JcrUtils.getRepository(REPOSITORY_URL);
		    Session session = repository.login(new SimpleCredentials(USERNAME, PASSWORD.toCharArray()), "default");
		    System.out.println("Logged in to repository");

		    try {
		    	JSONArray jsonArray = new JSONArray();
		        // Traverse the repository to find all document nodes
		        Node rootNode = session.getRootNode();
		        NodeIterator nodeIterator = rootNode.getNodes();
		        System.out.println("Number of nodes in the repository: " + rootNode.getNodes().getSize());
		        while (nodeIterator.hasNext()) {
		            Node node = nodeIterator.nextNode();
		            System.out.println("Node name: " + node.getName());
		            if(node.getName().equals("jcr:system") || node.getName().equals("rep:policy")) {
		            	System.out.println("jcr");
		            	continue;
		            }
		            if(node.getName().equals("documents")) {
		                // Remove the node
		                node.remove();
		                // Save the changes
		                session.save();
		                continue;
		            }
		            Node sensordata=node.getNode("sensordata");
		           // System.out.println(sensordata.getPrimaryNodeType());
//		            if (sensordata.isNodeType("nt:file")) { // Check if node is a document
		            	 System.out.println("Found a document node: " + node.getPath());
		                // Retrieve properties from the document node
		                String messageId = node.getName(); // Assuming messageId is the node name
		                String location = sensordata.getProperty("location").getString();
		                boolean isActive = sensordata.getProperty("isActive").getBoolean();
		                int batteryLevel = (int) sensordata.getProperty("batteryLevel").getLong();

		                // Process retrieved properties as needed
		                JSONObject jsonObject = new JSONObject();
		                jsonObject.put("messageId", messageId);
		                jsonObject.put("location", location);
		                jsonObject.put("isActive", isActive);
		                jsonObject.put("batteryLevel", batteryLevel);

		                // Add the JSONObject to the JSON array
		                jsonArray.put(jsonObject);
//		            }
		       
		        }   System.out.println("JSON array: " + jsonArray.toString());
		    } finally {
		        // Clean up resources
		        if (session != null) {
		            session.logout();
		        }
		    }
		}

	}


