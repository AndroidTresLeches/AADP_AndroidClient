package com.tresleches.aadp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
	private ImageView ivAadpIcon;
	private ImageView ivAadpText;
	private String username;
	private String password;
	private String objectId;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		objectId = getIntent().getStringExtra("objectId");
		loadUI();
	}

	public void loadUI() {
		etUserName = (EditText) findViewById(R.id.etUserName);
		etPassword = (EditText) findViewById(R.id.etPassword);
		btnLogin = (Button) findViewById(R.id.btLogIn);
		tvSignUp = (TextView) findViewById(R.id.tvSignUp);
		ivAadpIcon = (ImageView) findViewById(R.id.ivAadpIcon);
		ivAadpText = (ImageView) findViewById(R.id.ivAadpText);
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				username = etUserName.getText().toString();
				password = etPassword.getText().toString();
				
				ParseUser.logInInBackground(username, password, new LogInCallback() {
					  public void done(ParseUser user, ParseException e) {
					    if (user != null) {
					    	Intent data = new Intent();
							//startActivity(i);
					    	// Intent data = new Intent();
					   	  // Pass relevant data back as a result
					   	  data.putExtra("username", username);
					   	  data.putExtra("objectId", objectId);
					   	  // Activity finished ok, return the data
					   	  setResult(RESULT_OK, data); // set result code and bundle data for response
					   	  finish(); // closes the activity, pass data to parent
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
				overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		finish();
		overridePendingTransition(R.anim.left_in, R.anim.right_out);
	}
}
