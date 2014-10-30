package com.tresleches.aadp.fragment;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cuubonandroid.sugaredlistanimations.SpeedScrollListener;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.tresleches.aadp.R;
import com.tresleches.aadp.adapter.KnowledgeArrayAdapter;
import com.tresleches.aadp.helper.AADPTaskManager;
import com.tresleches.aadp.interfaces.AADPTask;
//import com.tresleches.aadp.adapter.PlusListAdapter;
import com.tresleches.aadp.model.KnowledgeBase;

public class AboutFragment extends Fragment implements AADPTask {

	private SpeedScrollListener listener;
	// private PlusListAdapter plusAdapter;
	private KnowledgeArrayAdapter aboutAdapter;
	private ListView lvAbout;
	private ArrayList<KnowledgeBase> aboutList;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		aboutList = new ArrayList<KnowledgeBase>();
		listener = new SpeedScrollListener();
		aboutAdapter = new KnowledgeArrayAdapter(getActivity(), aboutList,
				KnowledgeBase.SUB_CATEGORY, true);

	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_about, container, false);

		lvAbout = (ListView) v.findViewById(R.id.lvAbout);
		lvAbout.setOnScrollListener(listener);
		lvAbout.setAdapter(aboutAdapter);
		new AADPTaskManager(this, getActivity()).execute();//getAbout();

		return v;
	}

	public void getAbout() {
		// Define the class we would like to query
		ParseQuery<KnowledgeBase> query = ParseQuery
				.getQuery(KnowledgeBase.class);
		// Define our query conditions
		query.whereEqualTo("category", "About AADP");

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
		aboutAdapter.addAll(list);
	}

	@Override
	public void performTask() {
		getAbout();
	}

	@Override
	public void performOfflineTask() {
	}
}
