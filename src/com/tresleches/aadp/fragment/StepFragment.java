package com.tresleches.aadp.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.tresleches.aadp.R;
import com.tresleches.aadp.adapter.KnowledgeArrayAdapter;
import com.tresleches.aadp.helper.NetworkUtils;
import com.tresleches.aadp.model.Contact;
import com.tresleches.aadp.model.KnowledgeBase;

//modified from PagerSlidingTabStrip sample
public class StepFragment extends Fragment {

	private static final String ARG_POSITION = "position";
	private int position;
	private KnowledgeArrayAdapter aKnowledge;
	private ArrayList<KnowledgeBase> knowledgeList;
	private Contact oneContact;
	private RadioButton rbAgeYes, rbAgeNo;
	private RadioButton rbMinorityYes, rbMinorityNo;
	private RadioButton rbHeartYes, rbHeartNo;
	private RadioButton rbHipYes, rbHipNo;
	private OnClickListener rb_listener;
	private TextView tvStep2Send;
	private Spinner spLanguage;
	private EditText etNotes;
	

	public static StepFragment newInstance(int position) {
		StepFragment f = new StepFragment();
		Bundle b = new Bundle();
		b.putInt(ARG_POSITION, position);
		f.setArguments(b);
		return f;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		position = getArguments().getInt(ARG_POSITION);
		knowledgeList = new ArrayList<KnowledgeBase>();
		aKnowledge = new KnowledgeArrayAdapter(getActivity(), knowledgeList, KnowledgeBase.KEYWORD2, false);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

		FrameLayout fl = new FrameLayout(getActivity());
		fl.setLayoutParams(params);

		final int margin = (int) TypedValue.applyDimension(
				TypedValue.COMPLEX_UNIT_DIP, 8, getResources()
						.getDisplayMetrics());
		View v;
		
		rb_listener = new OnClickListener (){
			 public void onClick(View v) {
				 int id = v.getId();
				 switch( id) {
			        case R.id.rbAgeYes:
			        	rbAgeYes.setChecked(true);
			        	rbAgeNo.setChecked(false);
			        	break;
			        case R.id.rbAgeNo:
			        	rbAgeYes.setChecked(false);
			        	rbAgeNo.setChecked(true);
			        	break;
			        case R.id.rbHeartYes:
			        	rbHeartYes.setChecked(true);
			        	rbHeartNo.setChecked(false);
			        	break;
			        case R.id.rbHeartNo:
			        	rbHeartYes.setChecked(false);
			        	rbHeartNo.setChecked(true);
			        	break;
			        case R.id.rbHipYes:
			        	rbHipYes.setChecked(true);
			        	rbHipNo.setChecked(false);
			        	break;
			        case R.id.rbHipNo:
			        	rbHipYes.setChecked(false);
			        	rbHipNo.setChecked(true);
			        	break;
			        case R.id.rbMinorityYes:
			        	rbMinorityYes.setChecked(true);
			        	rbMinorityNo.setChecked(false);
			        	break;
			        case R.id.rbMinorityNo:
			        	rbMinorityYes.setChecked(false);
			        	rbMinorityNo.setChecked(true);
			        	break;
			         default: 
			         	break;
			    }
				 RadioButton rb = (RadioButton)v;
				 rb.setChecked(true);
			 }
		};
			

		switch (position) {
		case 0:
			v = inflater.inflate(R.layout.fragment_step_card, container, false);
			ListView lvKnowledge = (ListView)v.findViewById(R.id.lvKnowledge);
			lvKnowledge.setAdapter(aKnowledge);
			getKnowledge("subCategory", "Am I eligible");
			fl.addView(v);
			break;
		case 1:
			v = inflater.inflate(R.layout.fragment_step2_card, container, false);
			rbAgeYes = (RadioButton) v.findViewById(R.id.rbAgeYes);
			rbAgeYes.setOnClickListener(rb_listener);
			rbAgeNo = (RadioButton) v.findViewById(R.id.rbAgeNo);
			rbAgeNo.setOnClickListener(rb_listener);
			rbMinorityYes = (RadioButton) v.findViewById(R.id.rbMinorityYes);
			rbMinorityYes.setOnClickListener(rb_listener);
			rbMinorityNo = (RadioButton) v.findViewById(R.id.rbMinorityNo);
			rbMinorityNo.setOnClickListener(rb_listener);
			rbHeartYes = (RadioButton) v.findViewById(R.id.rbHeartYes);
			rbHeartYes.setOnClickListener(rb_listener);
			rbHeartNo = (RadioButton) v.findViewById(R.id.rbHeartNo);
			rbHeartNo.setOnClickListener(rb_listener);
			rbHipYes = (RadioButton) v.findViewById(R.id.rbHipYes);
			rbHipYes.setOnClickListener(rb_listener);
			rbHipNo = (RadioButton) v.findViewById(R.id.rbHipNo);
			rbHipNo.setOnClickListener(rb_listener);
			spLanguage = (Spinner) v.findViewById(R.id.spStep2Lang);
			etNotes = (EditText) v.findViewById(R.id.etNotes);
			
			tvStep2Send = (TextView)v.findViewById(R.id.tvStep2Send);
			tvStep2Send.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent intent = new Intent(Intent.ACTION_SEND);
					intent.setType("plain/text");
					intent.putExtra(Intent.EXTRA_EMAIL,
							new String[] { "info@aadp.org" });
					intent.putExtra(Intent.EXTRA_SUBJECT,
							"Request for homekit ");
					intent.putExtra(Intent.EXTRA_TEXT, getTextForEamil());
					startActivity(Intent.createChooser(intent, ""));
					
				}
			});
			
			fl.addView(v);
			break;
		case 2:
		case 3:
		default:
			TextView tv = new TextView(getActivity());
			params.setMargins(margin, margin, margin, margin);
			tv.setLayoutParams(params);
			tv.setLayoutParams(params);
			tv.setGravity(Gravity.CENTER);
			tv.setBackgroundResource(R.drawable.background_card);
			tv.setText("CARD " + (position + 1));
			fl.addView(tv);
			break;
		}

		return fl;
	}
	
	private String getTextForEamil(){
		String requestStr = "";
		requestStr += "1. " + getResources().getString(R.string.step2_age);
		requestStr += (rbAgeYes.isChecked()? " Yes\n" : " No\n");
		requestStr += "2. " + getResources().getString(R.string.step2_minority);
		requestStr += (rbMinorityYes.isChecked()? " Yes\n" : " No\n");
		requestStr += "3. " + getResources().getString(R.string.step2_hip_problem);
		requestStr += (rbHeartYes.isChecked()? " Yes \n" : " No\n");
		requestStr += "4. " + getResources().getString(R.string.step2_heart_problem);
		requestStr += (rbHipYes.isChecked()? " Yes\n" : " No\n");
		requestStr += "5. " + getResources().getString(R.string.step2_language);
		requestStr += " " + spLanguage.getSelectedItem().toString() + "\n";
		requestStr += getResources().getString(R.string.step2_notes);
		String notes = etNotes.getText().toString();
		requestStr += " " + (notes.length() > 0 ? notes : "None.");
		
		return requestStr;
	}
	
	public void getContact() {
		if (NetworkUtils.isNetworkAvailable(getActivity())) {
			// Define the class we would like to query

			ParseUser currentUser = ParseUser.getCurrentUser();
			if (currentUser != null) {
				ParseQuery<Contact> query = ParseQuery.getQuery(Contact.class);
				// Define our query conditions
				String user = ParseUser.getCurrentUser().getUsername();
				if (user != null) {
					query.whereEqualTo("firstName", user);
					query.findInBackground(new FindCallback<Contact>() {
						public void done(List<Contact> results,
								ParseException e) {
							if (e == null) {
								oneContact = results.get(0);
							} else {
								// There was an error
							}
						}
					});
				} else {
					Toast.makeText(getActivity(),
							getResources().getString(R.string.no_network),
							Toast.LENGTH_SHORT).show();
				}
			}
		}
	}

	
	public void getKnowledge(String queryField, String queryValue) {
		// Define the class we would like to query
		ParseQuery<KnowledgeBase> query = ParseQuery
				.getQuery(KnowledgeBase.class);
		// Define our query conditions
		query.whereEqualTo(queryField, queryValue);

		query.findInBackground(new FindCallback<KnowledgeBase>() {
			public void done(List<KnowledgeBase> objects, ParseException e) {
				if (e == null) {
					// The query was successful.
					addAll(objects);
				} else {
					// Something went wrong.
					Log.d("ERROR", e.toString());
				}
			}
		});
	}

	public void addAll(List<KnowledgeBase> list) {
		aKnowledge.addAll(list);
	}
}