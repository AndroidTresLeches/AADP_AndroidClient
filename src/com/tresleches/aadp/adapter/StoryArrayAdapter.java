package com.tresleches.aadp.adapter;

/**
 * An Array Adapter for Story Object
 */

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.tresleches.aadp.R;
import com.tresleches.aadp.activity.StoryDetailActivity;
import com.tresleches.aadp.helper.Utils;
import com.tresleches.aadp.interfaces.Shareable;
import com.tresleches.aadp.model.Story;

public class StoryArrayAdapter extends ArrayAdapter<Story> {

	private ViewHolder viewHolder = null;
	private int lastPosition = -1;

	public StoryArrayAdapter(Context context, int resource, List<Story> objects) {
		super(context, resource, objects);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Story story = getItem(position);

		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.story_list_item, parent, false);
			viewHolder.ivStoryImage = (CircularImageView) convertView.findViewById(R.id.ivStoryImage);
			viewHolder.tvStoryName = (TextView) convertView.findViewById(R.id.tvStoryName);
			viewHolder.tvStoryDetail = (TextView) convertView.findViewById(R.id.tvStoryDetail);
			viewHolder.ivTwitter = (ImageView) convertView.findViewById(R.id.ivTwitter);
			viewHolder.ivFacebook = (ImageView) convertView.findViewById(R.id.ivFacebook);
			viewHolder.ivShare = (ImageView) convertView.findViewById(R.id.ivShare);
			
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
			viewHolder.ivStoryImage.setImageResource(0);
		}

		ImageLoader imgLoader = ImageLoader.getInstance();
		imgLoader.displayImage(story.getPicUrl(), viewHolder.ivStoryImage);
		viewHolder.tvStoryName.setText(story.getName());
		viewHolder.tvStoryDetail.setText(story.getDetail());
		viewHolder.ivStoryImage.setTag(story);
		viewHolder.ivFacebook.setTag(story);
		viewHolder.ivTwitter.setTag(story);
		viewHolder.ivShare.setTag(story);

		imgLoader.displayImage(story.getPicUrl(), viewHolder.ivStoryImage);
		
		setupListeners(convertView);
		setShareListeners(viewHolder);
		Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
		convertView.startAnimation(animation);
	    lastPosition = position;
		return convertView;
	}
	
	
/**
 * Method to add listeners to the Story Item.
 * @param view
 */
	private void setupListeners(View view) {
		
		
		//Setting View to Open up the Story Detail Activity 
		view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				Story story = (Story) view.findViewById(R.id.ivStoryImage).getTag();
				Intent i = new Intent(getContext(),StoryDetailActivity.class);
				i.putExtra("story_id", story.getObjectId());
				getContext().startActivity(i);
				((Activity)getContext()).overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});
		
		
		
		
	}

	private void setShareListeners(ViewHolder viewHolder) {
		
		viewHolder.ivShare.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.startShareIntent((Activity)getContext(), (Shareable)v.getTag());

			}
		});
		
		viewHolder.ivTwitter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.startShareIntentTwitter((Activity)getContext(),(Shareable)v.getTag());

			}
		});

		
		viewHolder.ivFacebook.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Utils.startShareIntentFaceBook((Activity)getContext(),(Shareable)v.getTag());

			}
		});

	}

	// View holder pattern for fast accessing (Caching) of the widgets
	public class ViewHolder {

		public ImageView ivShare;
		public ImageView ivFacebook;
		public ImageView ivTwitter;
		public TextView tvStoryName;
		public TextView tvStoryDetail;
		public CircularImageView ivStoryImage;
	}

}