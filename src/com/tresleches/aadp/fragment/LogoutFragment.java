package com.tresleches.aadp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.parse.ParseUser;
import com.tresleches.aadp.R;
import com.tresleches.aadp.activity.HomeActivity;

public class LogoutFragment extends Fragment {

	public LogoutFragment() {
		ParseUser.logOut();
		//Intent i = new Intent(getActivity(), HomeActivity.class);
		//getActivity().startActivity(i);
	}
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = new Intent(getActivity(), HomeActivity.class);
		getActivity().startActivity(i);
		getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
	}
}
