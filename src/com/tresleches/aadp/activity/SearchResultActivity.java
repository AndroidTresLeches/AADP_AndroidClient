package com.tresleches.aadp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;

import com.tresleches.aadp.R;
import com.tresleches.aadp.fragment.ContactFragment;

public class SearchResultActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		
		// Replace the container with the new fragment
		FragmentTransaction ft = this.getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.flSearchResult, ContactFragment.newInstance("coordinator"));
		ft.commit();
	}
}
