package com.example.mqttapp;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.json.JSONObject;
import java.util.*;

public class MQTTClient {
	private static final String MQTT_BROKER = AppConfig.getProperty("mqtt.broker");
	private static final String MQTT_TOPIC = AppConfig.getProperty("mqtt.topic");
	public static void main(String[] args) throws MqttException, InterruptedException {
		MqttClient client = new MqttClient(MQTT_BROKER,  MqttClient.generateClientId());
		client.connect();

		Timer timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				
				String messageId=generatemessageId();
				JSONObject jsonObject = new JsonGenerator().generateJSON(messageId);
				String json = jsonObject.toString();
				try {
					client.publish(MQTT_TOPIC, new MqttMessage(json.getBytes()));
				} catch (MqttPersistenceException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (MqttException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}, 0, 2000);
	}
	
	public static String generatemessageId()
	{
		int length=10;
		String uuid = UUID.randomUUID().toString().replace("-", "");
	    return uuid.substring(0, Math.min(length, uuid.length()));
	}

}
