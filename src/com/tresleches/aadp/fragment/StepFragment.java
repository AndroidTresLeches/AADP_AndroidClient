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
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.tresleches.aadp.R;
import com.tresleches.aadp.activity.LoginActivity;
import com.tresleches.aadp.adapter.KnowledgeArrayAdapter;
import com.tresleches.aadp.helper.NetworkUtils;
import com.tresleches.aadp.model.Contact;
import com.tresleches.aadp.model.Favorite;
import com.tresleches.aadp.model.KnowledgeBase;

//modified from PagerSlidingTabStrip sample
public class StepFragment extends Fragment {

	private static final String ARG_POSITION = "position";
	private int position;
	private KnowledgeArrayAdapter aKnowledge;
	private ArrayList<KnowledgeBase> knowledgeList;
	private Contact oneContact;

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