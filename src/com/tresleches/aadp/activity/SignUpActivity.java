package com.tresleches.aadp.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;
import com.tresleches.aadp.R;

public class SignUpActivity extends Activity {

	private EditText etUserName;
	private EditText etEmail;
	private EditText etPassword;
	private EditText etConfirmPassword;
	private Button btnRegister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);
		getActionBar().setTitle(getResources().getString(R.string.signup));
		loadUI();
	}

	public void loadUI() {
		etUserName = (EditText) findViewById(R.id.etUserName);
		etEmail = (EditText) findViewById(R.id.etEmail);
		etPassword = (EditText) findViewById(R.id.etPassword);
		etConfirmPassword = (EditText) findViewById(R.id.etConfirmPassword);
		btnRegister = (Button) findViewById(R.id.btnRegister);
		
		Typeface font = Typeface.createFromAsset(getAssets(),
				"fonts/Roboto-Regular.ttf");
		etUserName.setTypeface(font);
		etEmail.setTypeface(font);
		etConfirmPassword.setTypeface(font);
		etConfirmPassword.setTypeface(font);
		btnRegister.setTypeface(font);
		btnRegister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				registerUser();
			}
		});
	}

	private void registerUser() {
		String username = etUserName.getText().toString();
		String password = etPassword.getText().toString();
		String confirmPassword = etConfirmPassword.getText().toString();
		String email = etEmail.getText().toString();

		if (password.equals(confirmPassword)) {

			ParseUser user = new ParseUser();
			// Set core properties
			user.setUsername(username);
			user.setPassword(password);
			user.setEmail(email);

			// Invoke signUpInBackground
			user.signUpInBackground(new SignUpCallback() {
				public void done(ParseException e) {
					if (e == null) {
						// Hooray! Let them use the app now.
						Intent i = new Intent(getApplicationContext(), LoginActivity.class);
						startActivity(i);
					} else {
						Toast.makeText(getApplicationContext(),
								"Registration Failed", Toast.LENGTH_SHORT)
								.show();
					}
				}
			});
		} else {
			Toast.makeText(getApplicationContext(),
					"Password does not match", Toast.LENGTH_SHORT)
					.show();
		}
	}
	
	@Override
	public void onBackPressed() {
		finish();
		overridePendingTransition(R.anim.left_in, R.anim.right_out);
	}
}