package com.tresleches.aadp.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.etsy.android.grid.StaggeredGridView;
import com.tresleches.aadp.R;
import com.tresleches.aadp.adapter.ImageArrayAdapter;
import com.tresleches.aadp.model.Story;
import com.tresleches.aadp.model.StoryImage;
import com.tresleches.aadp.model.StoryTitle;

/**
 * This class deals with Story board which can open 4 types of story
 * 1. Searching 
 * 2. Survivors
 * 3. Donor Heroes
 * 4. In Loving Memory. 
 * @author paragsagar
 *
 */

public class StoryBoardFragment extends Fragment {
	
	ArrayList<StoryImage> storyImages ; //List of images to be displayed on Grid
	StaggeredGridView gridView ;
	ImageArrayAdapter imageAdapter;

	public void onAttach(Activity activity) {
		super.onAttach(activity);
	
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		storyImages = new ArrayList<StoryImage>();
	
		imageAdapter = new ImageArrayAdapter(getActivity(),storyImages);
		gridView.setAdapter(imageAdapter);

		loadImageData();
	}

	/**
	 * Called on Fragment after the Activity is setup 
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_story_board, container,false);

		getActivity().getActionBar().setTitle(StoryTitle.STORIES);
		
		gridView = (StaggeredGridView) view.findViewById(R.id.gvList);
		
		
		
		
		
		
//		//4 Image Views represents 4 story types.
//		//these ImagesViews can be changed to some other views 
//		ImageView ivSearching = (ImageView)view.findViewById(R.id.ivSearching);
//		ImageView ivSurvivor = (ImageView)view.findViewById(R.id.ivSurvivor);
//		ImageView ivDonor = (ImageView)view.findViewById(R.id.ivDonor);
//		ImageView ivInMemory = (ImageView)view.findViewById(R.id.ivInMemory);
//		
//		//Setting Story Type Tags to each view
//		ivSearching.setTag(Story.Type.SEARCHING);
//		ivSurvivor.setTag(Story.Type.SURVIVOR);
//		ivDonor.setTag(Story.Type.DONOR);
//		ivInMemory.setTag(Story.Type.IN_LOVING_MEMORY);
//		
//	
//		//Setting a common listener - which will open a new Fragment passing Story Type
//		ivSearching.setOnClickListener(new StoryBoardOnClickListener() );
//		ivSurvivor.setOnClickListener(new StoryBoardOnClickListener() );
//		ivDonor.setOnClickListener(new StoryBoardOnClickListener() );
//		ivInMemory.setOnClickListener(new StoryBoardOnClickListener() );
		
		return view;

	}
	
	
	private void loadImageData() {
		// TODO Auto-generated method stub
		
		storyImages.add(new StoryImage(R.drawable.searching, "Searching...",Story.Type.SEARCHING.toString()));
		storyImages.add(new StoryImage(R.drawable.survivor, "Survivor...", Story.Type.SURVIVOR.toString()));
		storyImages.add(new StoryImage(R.drawable.donor, "Donor Heroes...", Story.Type.DONOR.toString()));
		storyImages.add(new StoryImage(R.drawable.inmemory, "In Loving Memory...",Story.Type.IN_LOVING_MEMORY.toString()));
		imageAdapter.notifyDataSetChanged();
	}


	
}
