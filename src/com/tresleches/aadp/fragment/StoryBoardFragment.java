package com.tresleches.aadp.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.easyandroidanimations.library.ExplodeAnimation;
import com.easyandroidanimations.library.PuffOutAnimation;
import com.tresleches.aadp.R;
import com.tresleches.aadp.model.Story;
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
		ImageView ivSearching = (ImageView)view.findViewById(R.id.ivSearching);
		ImageView ivSurvivor = (ImageView)view.findViewById(R.id.ivSurvivor);
		ImageView ivDonor = (ImageView)view.findViewById(R.id.ivDonor);
		ImageView ivInMemory = (ImageView)view.findViewById(R.id.ivInMemory);
		
		//Setting Story Type Tags to each view
		ivSearching.setTag(Story.Type.SEARCHING);
		ivSurvivor.setTag(Story.Type.SURVIVOR);
		ivDonor.setTag(Story.Type.DONOR);
		ivInMemory.setTag(Story.Type.IN_LOVING_MEMORY);
		
	
		//Setting a common listener - which will open a new Fragment passing Story Type
		ivSearching.setOnClickListener(new StoryBoardOnClickListener() );
		ivSurvivor.setOnClickListener(new StoryBoardOnClickListener() );
		ivDonor.setOnClickListener(new StoryBoardOnClickListener() );
		ivInMemory.setOnClickListener(new StoryBoardOnClickListener() );
		
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
		 * Method to start the new Fragment based on the Stroy type
		 */
		@Override
		public void onClick(View view) {
			
			FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
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
