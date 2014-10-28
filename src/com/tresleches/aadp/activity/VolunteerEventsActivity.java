package com.tresleches.aadp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;

import com.tresleches.aadp.R;
import com.tresleches.aadp.fragment.VounteerFragment;

public class VolunteerEventsActivity extends FragmentActivity {

	private String contactName;
	private VounteerFragment volFrag;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_volunteer_events);
		contactName = getIntent().getStringExtra("contact");
		initFragment();
	}
	
	protected void initFragment() {
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		volFrag = VounteerFragment.newInstance(contactName);
		transaction.replace(R.id.frVolunteer, volFrag);
		transaction.commit();
	}
}
