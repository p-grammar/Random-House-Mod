package com.example.examplemod.capability.randHouse;

public class RandHouseImpl implements RandHouseI {
	
	public static final boolean DEFAULT_PARTICIPATING = false;
	public static final boolean DEFAULT_COUNTDOWN_READY = false;
	public static final int DEFAULT_MAX_TIME = 600;
	public static final int DEFAULT_TIME_LEFT = DEFAULT_MAX_TIME;
	public static final int DEFAULT_POINTS = 0;
	
	private boolean participating;
	private boolean countdownReady;
	private int maxTime;
	private int timeLeft;
	private int points;
	
	public RandHouseImpl() {
		reset();
	}
	
	public void reset() {
		participating = DEFAULT_PARTICIPATING;
		countdownReady = DEFAULT_COUNTDOWN_READY;
		maxTime = DEFAULT_MAX_TIME;
		timeLeft = DEFAULT_TIME_LEFT;
		points = DEFAULT_POINTS;
	}
	
	@Override
	public void copy(RandHouseI other) {
		participating = other.getParticipating();
		countdownReady = other.getCountdownReady();
		maxTime = other.getMaxTime();
		timeLeft = other.getTimeLeft();
		points = other.getPoints();
	}
	
	@Override
	public boolean getParticipating() {
		return participating;
	}
	
	@Override
	public void setParticipating(boolean participating) {
		this.participating = participating;
	}
	
	@Override
	public int getMaxTime() {
		return maxTime;
	}
	
	@Override
	public void setMaxTime(int maxTime) {
		this.maxTime = maxTime;
	}
	
	@Override
	public int getTimeLeft() {
		return timeLeft;
	}
	
	@Override
	public void setTimeLeft(int timeLeft) {
		this.timeLeft = timeLeft;
	}
	
	@Override
	public void changeTimeLeft(int timeLeft) {
		this.timeLeft += timeLeft;
	}
	
	@Override
	public int getPoints() {
		return points;
	}
	
	@Override
	public void setPoints(int points) {
		this.points = points;
	}
	
	@Override
	public void changePoints(int points) {
		this.points += points;
	}
	
	@Override
	public boolean getCountdownReady() {
		return countdownReady;
	}
	
	@Override
	public void setCountdownReady(boolean countdownReady) {
		this.countdownReady = countdownReady;
	}

}
