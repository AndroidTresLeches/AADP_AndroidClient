package com.tresleches.aadp.fragment;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.tresleches.aadp.R;
import com.tresleches.aadp.activity.HomeActivity;
import com.tresleches.aadp.activity.SignUpActivity;

public class LoginFragment extends Fragment {

	private EditText etUserName;
	private EditText etPassword;
	private Button btnLogin;
	private TextView tvSignUp;
	private ImageView ivAadpIcon;
	private ImageView ivAadpText;
	private String username;
	private String password;
	private String objectId;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		objectId = getActivity().getIntent().getStringExtra("objectId");
		getActivity().getActionBar().setTitle(getResources().getString(R.string.login));
		//loadUI();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_login, container, false);

		etUserName = (EditText) view.findViewById(R.id.etUserName);
		etPassword = (EditText) view.findViewById(R.id.etPassword);
		btnLogin = (Button) view.findViewById(R.id.btLogIn);
		tvSignUp = (TextView) view.findViewById(R.id.tvSignUp);
		ivAadpIcon = (ImageView) view.findViewById(R.id.ivAadpIcon);
		ivAadpText = (ImageView) view.findViewById(R.id.ivAadpText);
		Typeface font = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/Roboto-Regular.ttf");
		Typeface fontBtn = Typeface.createFromAsset(getActivity().getAssets(),
				"fonts/Roboto-Bold.ttf");
		etUserName.setTypeface(font);
		etPassword.setTypeface(font);
		btnLogin.setTypeface(fontBtn);
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				username = etUserName.getText().toString();
				password = etPassword.getText().toString();
				login();
				//new AADPTaskManager(LoginActivity.this, LoginActivity.this).execute(); 
			}
		});
		tvSignUp.setTypeface(fontBtn);
		tvSignUp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getActivity(), SignUpActivity.class);
				startActivity(i);
				getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});
		return view;
	}

	/**
	 * Method To log In
	 */
	private void login() {
		ParseUser.logInInBackground(username, password, new LogInCallback() {
			  public void done(ParseUser user, ParseException e) {
			    if (user != null) {
			    	Intent data = new Intent(getActivity(), HomeActivity.class);
			   	  // Pass relevant data back as a result
			   	  data.putExtra("username", username);
			   	  data.putExtra("objectId", objectId);
			   	  startActivity(data);
			    } else {
			      // Signup failed. Look at the ParseException to see what happened.
			    	Log.d("ERROR", "User login failed");
			    	Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
			    }
			  }
			});
	}	
}