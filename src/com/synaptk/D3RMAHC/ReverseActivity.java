package com.synaptk.D3RMAHC;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

public class ReverseActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reverse);
        
        Button calculateButton = (Button) findViewById(R.id.reverse_CalculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getListPrice();				
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_reverse, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    	case R.id.menu_switchToRMC:
    		finish();
    		return true;
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }
    
    // Converts dollars (double) to cents (int).
    // @param1: dollars - value to be converted to cents.
    // return: returns the cents value of the input dollars as an int.
    private int convertDollarsToCents(double dollars) {
    	dollars = dollars * 100;
    	int cents = (int)dollars;
    	return cents;
    }
    
    // Converts cents (int) to dollars (double).
    // @param1: cents - value to be converted to dollars.
    // return: returns the dollar value of the input cents as an int.
    private double convertCentsToDollars(int cents) {
    	double dollars = cents / 100.0;
    	return dollars;
    }
    
 // Gets the text entered into the input box and converts it to cents (int).
    // returns the input text as an int (cents).
    private int getInputPrice() {
    	EditText inputPrice = (EditText) findViewById(R.id.receiveAmountEditView);
    	
    	// check for empty input
    	if(inputPrice.getText().length() < 1)
    		return 0;
    	
    	// get text from input box, convert it to a string then convert it to a double
    	double doubleValue = Double.valueOf(inputPrice.getText().toString());
    	// convert the double to an int
    	int value = convertDollarsToCents(doubleValue);
    	
    	return value;
    }
    
    // Validate the user input by checking out of bounds values
    // @param1: cents - value to check
    // return: if the input is within the bounds then the input is returned as output.
    // If the input is outside of the bounds then the limit value is returned and the
    // input box has its value changed to the limit
    private int validateListingPrice(int cents, int radioGroupSelection) {
    	EditText inputBox = (EditText) findViewById(R.id.receiveAmountEditView);
    	
    	// check that the listed price is not greater than $250.00
    	if(cents > 25000) {
    		int newReceiveAmountAsCents = calculateReceiveAmount(25000, radioGroupSelection);
    		inputBox.setText(String.valueOf(convertCentsToDollars(newReceiveAmountAsCents)));
    		return 25000;  // $250.00
    	}
    	
    	// check that the listed price is not less than $2.50
    	if(cents < 250) {
    		int newReceiveAmountAsCents = calculateReceiveAmount(250, radioGroupSelection);
    		inputBox.setText(String.valueOf(convertCentsToDollars(newReceiveAmountAsCents)));
    		return 250;  // $2.50
    	}
    	
    	// within limits return
    	return cents;
    }
    
    // Performs the calculation for the receive amount.
    // @param1: priceAsCents - value to calculate the receive amount from
    // @param2: radioSelection - determines how the amount will be calculated
    // return: returns the calculated receive amount as cents (int).
    private int calculateReceiveAmount(int priceAsCents, int radioSelection) {
    	int receivePriceAsCents = 0;
    	
    	if(radioSelection == R.id.sendToBattlenet) {
    		receivePriceAsCents = priceAsCents - 100;
    	}
    	else if(radioSelection == R.id.sendtoPaypal) {
    		priceAsCents = priceAsCents - 100;
    		priceAsCents = priceAsCents * 85;
    		receivePriceAsCents = priceAsCents / 100;
    	}
    	return receivePriceAsCents;
    }
    
    // Formats a string to look like a money value.
    // @param1: string - the string to format as currency.
    // return: if the input string doesn't have two numbers after the decimal then the string
    // is returned with an extra '0' appended to the end, otherwise the original string is
    // returned.
    private String formatOutputString(String string) {
    	if(string.charAt(string.length() - 2) == '.') {
    		string += "0";
    	}
    	
    	return string;
    }
    
    // Sets the text of the output text view to the value of cents after converting
    // it to dollars.
    // @param1: cents - cents value to be converted to dollars to be set as the output text
    private void setOutputReceiveAmount(int cents) {
    	TextView output = (TextView) findViewById(R.id.listAmountOutputView);
    	String outputString = "$";
    	double dollars = convertCentsToDollars(cents);
    	
    	outputString = outputString + dollars;
    	outputString = formatOutputString(outputString);
    	
    	output.setText(outputString);
    }
    
    // Gets the id of the selected radio button.
    // return: returns an int which is the id of the selected radio button from 
    // the deposit to radio group.
    private int getDepositToSelection() {
    	RadioGroup radioGroup = (RadioGroup) findViewById(R.id.sendToRadioGroupReverse);
    	int selection = radioGroup.getCheckedRadioButtonId();
    	return selection;
    }
    
    // Performs the calculation for the receive amount.
    // @param1: priceAsCents - value to calculate the receive amount from
    // @param2: radioSelection - determines how the amount will be calculated
    // return: returns the calculated receive amount as cents (int).
    private int calculateListPrice(int priceAsCents, int radioSelection) {
    	int listPriceAsCents = 0;
    	
    	if(radioSelection == R.id.sendToBattlenet) {
    		listPriceAsCents = priceAsCents + 100;
    	}
    	else if(radioSelection == R.id.sendtoPaypal) {
    		priceAsCents = (int)(priceAsCents / 0.85);
    		listPriceAsCents = priceAsCents + 100;
    	}
    	return listPriceAsCents;
    }
    
    // Main function executes on button press.
    private void getListPrice() {
    	// get input as cents and check for valid input
    	int inputReceiveAmountAsCents = getInputPrice();
    	
    	// get the selected radio button
    	int radioSelection = getDepositToSelection();
    	
    	// calculate the receive amount as cents
    	int receivePriceAsCents = calculateListPrice(inputReceiveAmountAsCents, radioSelection);
    	receivePriceAsCents = validateListingPrice(receivePriceAsCents, radioSelection);
    	
    	// display the receive amount
    	setOutputReceiveAmount(receivePriceAsCents);
    }
    
}
