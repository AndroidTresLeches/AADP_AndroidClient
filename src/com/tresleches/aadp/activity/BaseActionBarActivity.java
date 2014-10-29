package com.tresleches.aadp.activity;

import org.json.JSONException;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;
import com.tresleches.aadp.R;
import com.tresleches.aadp.helper.PayPalManager;
import com.tresleches.aadp.interfaces.Donatable;
import com.tresleches.aadp.model.Donation;

public class BaseActionBarActivity extends ActionBarActivity implements Donatable {
	
	private Donation donation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		PayPalConfiguration config = PayPalManager.getPayPalConfiguration(this);
		Intent intent = new Intent(this, PayPalService.class);
	    intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, config);
	    startService(intent);// Start Paypal services
	}

	@Override
	public void onDestroy() {
		//Stop Paypal services
		stopService(new Intent(this, PayPalService.class));
		super.onDestroy();
	}
	
	@Override
	public void setDonation(Donation donation) {
		this.donation = donation;
	}
	
	
	//For the activity Result of Paypal Donations.
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
	    if (resultCode == Activity.RESULT_OK) {
	        PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
	        if (confirm != null) {
	            try {
	                Log.i("paymentExample", confirm.toJSONObject().toString(4));
	                donation.saveInBackground();
	                showThanks();
	                //Start Donation Activity.
	                Intent i = new Intent(this,HomeActivity.class);
	                startActivity(i);
	            } catch (JSONException e) {
	                Log.e("paymentExample", "an extremely unlikely failure occurred: ", e);
	            }
	        }
	    }
	    else if (resultCode == Activity.RESULT_CANCELED) {
	        Log.i("paymentExample", "The user canceled.");
	    }
	    else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
	        Log.i("paymentExample", "An invalid Payment or PayPalConfiguration was submitted. Please see the docs.");
	    }

	}
	
	private  void showThanks() {
		LayoutInflater inflater = this.getLayoutInflater();
		View view = inflater.inflate(R.layout.toast_thanks_for_donation,(ViewGroup) this.findViewById(R.id.thank_you_note));
		Toast toast = new Toast(this);
		toast.setView(view);
		toast.setGravity(Gravity.CENTER, 0, 0);
		toast.setDuration(Toast.LENGTH_LONG);
		toast.show();

	}	
	

}
