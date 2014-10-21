package com.tresleches.aadp.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalService;
import com.tresleches.aadp.R;
import com.tresleches.aadp.helper.PayPalManager;
import com.tresleches.aadp.interfaces.Donatable;
import com.tresleches.aadp.model.Donation;

public class DonateFragment extends Fragment {
	
	private EditText etDonationName;
	private EditText etDonationEmail;
	private EditText etDonationAmount;
	private PayPalConfiguration config;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	
		super.onCreate(savedInstanceState);
	}

	
	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		
//		return super.onCreateView(inflater, container, savedInstanceState);
		View view = inflater.inflate(R.layout.fragment_donate,container, false);
		etDonationName = (EditText)view.findViewById(R.id.etDonationName);
		etDonationEmail = (EditText)view.findViewById(R.id.etDonationEmail);
		etDonationAmount = (EditText)view.findViewById(R.id.etDonationAmount);
		Button btnDonate = (Button)view.findViewById(R.id.btnDonate);
		
		etDonationName.setText("Parag Sagar");
		etDonationEmail.setText("ParagSagar@Yahoo.com");
		
		
		btnDonate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				donate();
				
			}
		});
		
		return view;
	}
	
	/**
	 * Method Which take care of Donation 
	 */
	private void donate() {
		// TODO Auto-generated method stub
		
		Donation donation = new Donation();
		Double donationAmount =  Double.valueOf(etDonationAmount.getText().toString()); 
		donation.setName(etDonationName.getText().toString());
		donation.setEmail(etDonationEmail.getText().toString());
		donation.setAmount(donationAmount);
		Toast.makeText(getActivity(), "Donating : $"+etDonationAmount.getText(), Toast.LENGTH_SHORT).show();
		Donatable donatableActivity = (Donatable)getActivity();
		donatableActivity.setDonation(donation);
		Intent ppIntent = PayPalManager.getFundTreatmentIntent(getActivity(), donationAmount, "AADP");
		getActivity().startActivityForResult(ppIntent, 40);
	}
}
