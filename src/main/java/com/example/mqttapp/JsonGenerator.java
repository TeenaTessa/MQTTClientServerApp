package com.example.mqttapp;
import java.util.*;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonGenerator {
	private static final Random random=new Random();
	private static final ObjectMapper MAPPER = new ObjectMapper();
	private static final String[] LOCATIONS = {"kitchen", "bedroom", "living room", "bathroom"};
	
	public  JSONObject generateJSON(String clientId) {
				JSONObject jsonObject = generateRandomJsonObject(clientId);
				System.out.println(jsonObject.toString());
				return jsonObject;
	}
    private static JSONObject generateRandomJsonObject(String clientId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("messageId", clientId);
        jsonObject.put("isActive", getRandomBoolean());
        jsonObject.put("batteryLevel", getRandomInt(0, 100));
        jsonObject.put("location", getRandomLocation());
        return jsonObject;
    }
    
    private static boolean getRandomBoolean() {
        return random.nextBoolean();
    }
    
    private static int getRandomInt(int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }
    
    private static String getRandomLocation() {
        return LOCATIONS[random.nextInt(LOCATIONS.length)];
    }


}
