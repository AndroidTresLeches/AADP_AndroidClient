package com.tresleches.aadp.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.RelativeLayout;

import com.easyandroidanimations.library.PuffOutAnimation;
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
	
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
	}
	

	/**
	 * Called on Fragment after the Activity is setup 
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_story_board, container,false);

		getActivity().getActionBar().setTitle(StoryTitle.STORIES);
		
		
		
		
		
		
		
		//4 Image Views represents 4 story types.
		//these ImagesViews can be changed to some other views 
		RelativeLayout rlSearching = (RelativeLayout)view.findViewById(R.id.rlSearching);
		RelativeLayout rlSurvivor = (RelativeLayout)view.findViewById(R.id.rlSurvivor);
		RelativeLayout rlDonor = (RelativeLayout)view.findViewById(R.id.rlDonor);
		RelativeLayout rlInMemory = (RelativeLayout)view.findViewById(R.id.rlInMemory);
		
		//Setting Story Type Tags to each view
		rlSearching.setTag(Story.Type.SEARCHING);
		rlSurvivor.setTag(Story.Type.SURVIVOR);
		rlDonor.setTag(Story.Type.DONOR);
		rlInMemory.setTag(Story.Type.IN_LOVING_MEMORY);
		
	
		//Setting a common listener - which will open a new Fragment passing Story Type
		rlSearching.setOnClickListener(new StoryBoardOnClickListener() );
		rlSurvivor.setOnClickListener(new StoryBoardOnClickListener() );
		rlDonor.setOnClickListener(new StoryBoardOnClickListener() );
		rlInMemory.setOnClickListener(new StoryBoardOnClickListener() );
		
		return view;

	}

	
	/**
	 * Common Listener for the views.
	 * They open up the Fragment based on Story Type tag.
	 * @author sagpa03
	 *
	 */
	class StoryBoardOnClickListener implements OnClickListener {

		/**
		 * Method to start the new Fragment based on the story type
		 */
		@Override
		public void onClick(View view) {
			if(getActivity() instanceof FragmentActivity){
				FragmentTransaction ft = ((FragmentActivity)getActivity()).getSupportFragmentManager().beginTransaction();
				StoryFragment storyFragment = new StoryFragment();
				storyFragment.setStoryType(view.getTag().toString()); //Passing Story type based on the view tag.
				new PuffOutAnimation(view).animate();
				ft.setCustomAnimations(R.anim.fadein, R.anim.fadeout);
				ft.replace(R.id.flContainer,storyFragment,view.getTag().toString()); 
				ft.addToBackStack(view.getTag().toString()); //get the back button working.
				ft.commit();//start the fragment
			}
		}
		
	}	
	
}
