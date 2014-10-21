package com.tresleches.aadp.helper;

import java.math.BigDecimal;

import android.app.Activity;
import android.content.Intent;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.tresleches.aadp.R;
import com.tresleches.aadp.model.Donation;

public class PayPalManager {
	
	public static Intent getFundTreatmentIntent(Activity activity,Double donationAmount,String donationTo) {
		
		PayPalPayment payment = new PayPalPayment(new BigDecimal(donationAmount),"USD", donationTo, PayPalPayment.PAYMENT_INTENT_SALE);
		Intent intent = new Intent(activity, PaymentActivity.class);
		intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
		return intent;
	}
	
	public static PayPalConfiguration getPayPalConfiguration(Activity activity)
	{
		//Pay pal Integration
		PayPalConfiguration config = new PayPalConfiguration()
        // Start with mock environment -ENVIRONMENT_NO_NETWORK.  When ready, switch to sandbox (ENVIRONMENT_SANDBOX)
        // or live (ENVIRONMENT_PRODUCTION)
        .environment(PayPalConfiguration.ENVIRONMENT_NO_NETWORK)
        .clientId(activity.getString(R.string.paypalClientId));
		
		return config;
	}

}
