package com.example.examplemod;

public class Reference {
	public static final String MOD_ID = "randomhousemod";
	
	public static int random(int low, int high) {
		return (int)(Math.random() * (high - low + 1)) + low;
	}
	
	public static <I> I randFromArray(I[] array) {
		return array[(int)(Math.random() * array.length)];
	}
	
}
