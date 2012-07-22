package com.synaptk.D3RMAHC;

public class helperUtils {
	
	// Converts dollars (double) to cents (int)
	// @param: dollars is the value to be converted to cents
	// return: returns the input value as an int
	public static int convertDollarsToCents(double dollars) {
		dollars = dollars * 100;
		int cents = (int)dollars;
		return cents;
	}
	
	// Converts cents (int) to dollars (double)
	// @param: cents is the number of cents to convert to dollars
	// return: returns the input as a double
	public static double convertCentsToDollars(int cents) {
		double dollars = cents / 100.00;
		return dollars;
	}
	
}
