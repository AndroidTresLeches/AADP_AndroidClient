package com.tresleches.aadp.adapter;

/**
 * An Array Adapter for Story Object
 */

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tresleches.aadp.R;
import com.tresleches.aadp.activity.StoryDetailActivity;
import com.tresleches.aadp.model.Story;

public class StoryArrayAdapter extends ArrayAdapter<Story> {

	private ViewHolder viewHolder = null;

	public StoryArrayAdapter(Context context, int resource, List<Story> objects) {
		super(context, resource, objects);

	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		Story story = getItem(position);

		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(getContext()).inflate(R.layout.story_list_item, parent, false);
			viewHolder.ivStoryImage = (ImageView) convertView.findViewById(R.id.ivStoryImage);
			viewHolder.tvStoryName = (TextView) convertView.findViewById(R.id.tvStoryName);
			viewHolder.tvStoryDetail = (TextView) convertView.findViewById(R.id.tvStoryDetail);
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

		imgLoader.displayImage(story.getPicUrl(), viewHolder.ivStoryImage);
		
		setupListeners(convertView);

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
			}
		});
		
	}



	// View holder pattern for fast accessing (Caching) of the widgets
	public class ViewHolder {

		public TextView tvStoryName;
		public TextView tvStoryDetail;
		public ImageView ivStoryImage;
	}

}