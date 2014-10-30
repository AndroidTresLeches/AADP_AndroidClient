package com.tresleches.aadp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
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
import com.tresleches.aadp.helper.AADPTaskManager;
import com.tresleches.aadp.interfaces.AADPTask;
import com.tresleches.aadp.model.StoryTitle;

public class LoginActivity extends Activity implements AADPTask{

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
		getActionBar().setTitle(getResources().getString(R.string.login));
		loadUI();
	}

	public void loadUI() {
		etUserName = (EditText) findViewById(R.id.etUserName);
		etPassword = (EditText) findViewById(R.id.etPassword);
		btnLogin = (Button) findViewById(R.id.btLogIn);
		tvSignUp = (TextView) findViewById(R.id.tvSignUp);
		ivAadpIcon = (ImageView) findViewById(R.id.ivAadpIcon);
		ivAadpText = (ImageView) findViewById(R.id.ivAadpText);
		Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/OpenSans-Light.ttf");
		etUserName.setTypeface(font);
		etPassword.setTypeface(font);
		btnLogin.setTypeface(font);
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				username = etUserName.getText().toString();
				password = etPassword.getText().toString();
				
				new AADPTaskManager(LoginActivity.this, LoginActivity.this).execute(); 
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
	

	/**
	 * Method To log In
	 */
	private void login() {
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
	
	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.left_in, R.anim.right_out);
	}

	@Override
	public void performTask() {
		login() ;
		
	}

	@Override
	public void performOfflineTask() {
		//Offline login? 
	}
}
