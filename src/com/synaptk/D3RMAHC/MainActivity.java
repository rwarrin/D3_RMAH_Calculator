package com.synaptk.D3RMAHC;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.synaptk.D3RMAHC.helperUtils;

public class MainActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        Button calculateButton = (Button) findViewById(R.id.main_CalculateButton);
        calculateButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				getReceivePrice();				
			}
		});
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    	case R.id.menu_switchToLPC:
    		Intent i = new Intent(MainActivity.this, ReverseActivity.class);
    		startActivity(i);
    	default:
    		return super.onOptionsItemSelected(item);
    	}
    }

    // Gets the text entered into the input box and converts it to cents (int).
    // returns the input text as an int (cents).
    private int getInputPrice() {
    	EditText inputPrice = (EditText) findViewById(R.id.listingPriceEditView);
    	
    	// check for empty input
    	if(inputPrice.getText().length() < 1)
    		return 0;
    	
    	// get text from input box, convert it to a string then convert it to a double
    	double doubleValue = Double.valueOf(inputPrice.getText().toString());
    	// convert the double to an int
    	int value = helperUtils.convertDollarsToCents(doubleValue);
    	
    	return value;
    }
    
    // Validate the user input by checking out of bounds values
    // @param1: cents - value to check
    // return: if the input is within the bounds then the input is returned as output.
    // If the input is outside of the bounds then the limit value is returned and the
    // input box has its value changed to the limit
    private int validateInput(int cents) {
    	EditText inputBox = (EditText) findViewById(R.id.listingPriceEditView);
    	
    	// check that the listed price is not greater than $250.00
    	if(cents > 25000) {
    		inputBox.setText("250.00");
    		return 25000;  // $250.00
    	}
    	
    	// check that the listed price is not less than $2.50
    	if(cents < 250) {
    		inputBox.setText("2.50");
    		return 250;  // $2.50
    	}
    	
    	// within limits return
    	return cents;
    }
    
    // Sets the text of the output text view to the value of cents after converting
    // it to dollars.
    // @param1: cents - cents value to be converted to dollars to be set as the output text
    private void setOutputReceiveAmount(int cents) {
    	TextView output = (TextView) findViewById(R.id.main_receiveAmountOutView);
    	String outputString = "$";
    	double dollars = helperUtils.convertCentsToDollars(cents);
    	
    	outputString = outputString + dollars;
    	outputString = helperUtils.formatOutputString(outputString);
    	
    	output.setText(outputString);
    }
    
    // Gets the id of the selected radio button.
    // return: returns an int which is the id of the selected radio button from 
    // the deposit to radio group.
    private int getDepositToSelection() {
    	RadioGroup radioGroup = (RadioGroup) findViewById(R.id.sendToRadioGroupMain);
    	int selection = radioGroup.getCheckedRadioButtonId();
    	return selection;
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
    
    // Main function executes on button press.
    private void getReceivePrice() {
    	// get input as cents and check for valid input
    	int inputPriceAsCents = getInputPrice();
    	inputPriceAsCents = validateInput(inputPriceAsCents);
    	
    	// get the selected radio button
    	int radioSelection = getDepositToSelection();
    	
    	// calculate the receive amount as cents
    	int receivePriceAsCents = calculateReceiveAmount(inputPriceAsCents, radioSelection);
    	
    	// display the receive amount
    	setOutputReceiveAmount(receivePriceAsCents);
    }

    
}
