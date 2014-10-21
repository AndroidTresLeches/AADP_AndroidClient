package com.tresleches.aadp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.tresleches.aadp.R;

public class LoginActivity extends Activity {

	private EditText etUserName;
	private EditText etPassword;
	private Button btnLogin;
	private TextView tvSignUp;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		loadUI();
	}

	public void loadUI() {
		etUserName = (EditText) findViewById(R.id.etUserName);
		etPassword = (EditText) findViewById(R.id.etPassword);
		btnLogin = (Button) findViewById(R.id.btLogIn);
		tvSignUp = (TextView) findViewById(R.id.tvSignUp);
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String username = etUserName.getText().toString();
				String password = etPassword.getText().toString();
				
				ParseUser.logInInBackground(username, password, new LogInCallback() {
					  public void done(ParseUser user, ParseException e) {
					    if (user != null) {
					    	Intent i = new Intent(getApplicationContext(), HomeActivity.class);
							startActivity(i);
					    } else {
					      // Signup failed. Look at the ParseException to see what happened.
					    	Log.d("ERROR", "User login failed");
					    	Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT).show();
					    }
					  }
					});
			}
		});
		
		tvSignUp.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent i = new Intent(getApplicationContext(), SignUpActivity.class);
				startActivity(i);
			}
		});
	}
}
