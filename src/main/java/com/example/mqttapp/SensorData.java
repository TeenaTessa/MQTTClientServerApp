package com.example.mqttapp;

public class SensorData {
	
	private boolean isActive;
    private int batteryLevel;
    private String location;
	public SensorData(boolean isActive, int batteryLevel, String location) {
		super();
		this.isActive = isActive;
		this.batteryLevel = batteryLevel;
		this.location = location;
	}
	public SensorData() {
		super();
		// TODO Auto-generated constructor stub
	}
	public boolean isActive() {
		return isActive;
	}
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}
	public int getBatteryLevel() {
		return batteryLevel;
	}
	public void setBatteryLevel(int batteryLevel) {
		this.batteryLevel = batteryLevel;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	@Override
	public String toString() {
		return "SensorData [isActive=" + isActive + ", batteryLevel=" + batteryLevel + ", location=" + location + "]";
	}
    
    

}
