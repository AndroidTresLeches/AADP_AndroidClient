package com.tresleches.aadp.adapter;

import java.util.List;

import com.nostra13.universalimageloader.core.ImageLoader;

import com.tresleches.aadp.R;
import com.tresleches.aadp.model.Story;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class StoryArrayAdapter extends ArrayAdapter<Story> {
	
	ViewHolder viewHolder = null;
	public StoryArrayAdapter(Context context, int resource, List<Story> objects) {
		super(context, resource, objects);
		
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		final Story story = getItem(position);
		
		
		if(view == null)
		{
			viewHolder = new ViewHolder();
			view = LayoutInflater.from(getContext()).inflate(R.layout.story_list_item, parent,false);
			
			viewHolder.ivStoryImage = (ImageView) view.findViewById(R.id.ivStoryImage);
			viewHolder.tvStoryName		= (TextView) view.findViewById(R.id.tvStoryName);
			viewHolder.tvStoryDetail	= (TextView) view.findViewById(R.id.tvStoryDetail);
			view.setTag(viewHolder);
		}
		else
		{
			viewHolder = (ViewHolder)view.getTag();
			viewHolder.ivStoryImage.setImageResource(0);
		}
		

		ImageLoader imgLoader = ImageLoader.getInstance();
		imgLoader.displayImage(story.getPicUrl(), viewHolder.ivStoryImage );
		viewHolder.tvStoryName.setText( story.getName());
		viewHolder.tvStoryDetail.setText( story.getDetail());
		
		imgLoader.displayImage(story.getPicUrl(), viewHolder.ivStoryImage );  
		
		
		
		return view;
	}	
	
	
	//View holder pattern for fast accessing (Caching) of the widgets
	public class ViewHolder{
		
		public TextView tvStoryName;
		public TextView tvStoryDetail;
		public ImageView ivStoryImage;
	}	

}
