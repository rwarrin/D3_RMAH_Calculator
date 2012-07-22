package com.synaptk.D3RMAHC;

public class helperUtils {
	
	public int convertDollarsToCents(double dollars) {
		dollars = dollars * 100;
		int cents = (int)dollars;
		return cents;
	}
	
	public double convertCentsToDollars(int cents) {
		double dollars = cents / 100.00;
		return dollars;
	}
	
}
