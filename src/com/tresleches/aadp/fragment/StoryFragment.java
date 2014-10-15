package com.tresleches.aadp.fragment;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;
import com.tresleches.aadp.R;
import com.tresleches.aadp.adapter.StoryArrayAdapter;
import com.tresleches.aadp.model.Story;


public class StoryFragment extends Fragment {

	ArrayList<Story> stories;
	StoryArrayAdapter aStory;
	private ListView lvStories;
	
	public void onAttach(Activity activity) {
		// TODO Auto-generated method stub
		super.onAttach(activity);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		stories = new ArrayList<Story>();
		aStory = new StoryArrayAdapter(getActivity(), R.layout.story_list_item,stories);
		
	}

	/**
	 * Called on Fragment after the Activity is setup 
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_story_list, container,false);
		lvStories = (ListView) view.findViewById(R.id.lvStories);
		lvStories.setAdapter(aStory);

		getStories();
		
		return view;

	}
	
	/**
	 * Get the Stories from net and fill in our List.
	 */
	public void getStories(){
		
		{
			// Define the class we would like to query
			ParseQuery<Story> query = ParseQuery.getQuery(Story.class);
			// Define our query conditions
			query.findInBackground(new FindCallback<Story>() {
			    public void done(List<Story> results, ParseException e) {
			      if (e == null) {
			        // results have all the Story
			    	
			    	  stories.addAll(results);
			    	  aStory.notifyDataSetChanged();
			    	  
			      } else {
			        // There was an error
			      }
			    }
			});
			
		}
	}


}
