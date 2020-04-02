package com.example.examplemod.capability.randHouse;


public interface RandHouseI {
	boolean getParticipating();
	
	void setParticipating(boolean participating);
	
	int getMaxTime();
	
	void setMaxTime(int maxTime);
	
	int getTimeLeft();
	
	void setTimeLeft(int timeLeft);
	
	void changeTimeLeft(int timeLeft);
	
	int getPoints();
	
	void setPoints(int points);
	
	void changePoints(int points);
	
}
