package com.tresleches.aadp.adapter;

import java.util.List;

import com.easyandroidanimations.library.PuffOutAnimation;
import com.tresleches.aadp.R;
import com.tresleches.aadp.fragment.StoryFragment;
import com.tresleches.aadp.model.StoryImage;




import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ImageArrayAdapter extends ArrayAdapter<StoryImage> {

	public ImageArrayAdapter(Context context, List<StoryImage> objects) {
		super(context, R.layout.story_board_item, objects);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		
		StoryImage image = (StoryImage)getItem(position);
		ViewHolder viewHolder;
		
		if(convertView == null){
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.story_board_item, parent,false);
			
			convertView.setTag(viewHolder);
			viewHolder.ivImage = (ImageView) convertView.findViewById(R.id.ivStoryBoardImage);
			viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tvStoryTitle);
			
		}
		else{
			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.ivImage.setImageResource(0);
		}
			
		//Load Image from Drawable
		
		//int imageResource = getContext().getResources().getIdentifier(uri, null, getContext().getPackageName());
		Drawable res = getContext().getResources().getDrawable(image.imageResource);
		
		viewHolder.ivImage.setImageDrawable(res);
		viewHolder.tvTitle.setText(image.imageTitle);
		viewHolder.ivImage.setTag(image.imageTag);
		
		viewHolder.ivImage.setOnClickListener(new StoryBoardOnClickListener());
		
		return convertView;
	}

	
	public class ViewHolder {
		ImageView ivImage;
		TextView  tvTitle ;
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
			if(getContext() instanceof FragmentActivity){
				FragmentTransaction ft = ((FragmentActivity)getContext()).getSupportFragmentManager().beginTransaction();
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

