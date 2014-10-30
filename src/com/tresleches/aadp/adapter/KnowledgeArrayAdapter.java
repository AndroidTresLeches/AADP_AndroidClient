package com.tresleches.aadp.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import android.view.animation.Animation;
import com.tresleches.aadp.R;
import com.tresleches.aadp.model.KnowledgeBase;

public class KnowledgeArrayAdapter extends ArrayAdapter<KnowledgeBase> {
    private List<KnowledgeBase> _list;
	private final Activity _context;
	private String _titleField;
	private boolean _hasImage;
	private int lastPosition = -1;

	public KnowledgeArrayAdapter(Activity context, List<KnowledgeBase> list, String titleField, boolean hasImage) {
		super(context, 0, list);
		this._context = context;
		this._list = list;
		this._titleField = titleField;
		this._hasImage = hasImage;
	}
	
	 private static class ViewHolder {
		    ImageView ivAboutImage;
		    TextView tvAboutTitle;
		    TextView tvAboutContent;
		  }

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = null;
		// Get the data item for this position
		KnowledgeBase knowledge = getItem(position);
		// Check if an existing view is being reused, otherwise inflate the view
		ViewHolder viewHolder;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(getContext());
			view = inflater.inflate(R.layout.about_list_item, parent, false);
			viewHolder.ivAboutImage = (ImageView) view.findViewById(R.id.ivAboutImage);
			viewHolder.tvAboutTitle = (TextView) view.findViewById(R.id.tvAboutTitle);
			viewHolder.tvAboutContent = (TextView) view.findViewById(R.id.tvAboutContent);
		  
			view.setTag(viewHolder);
			    	
		} else {
			view = convertView;	  
			viewHolder = (ViewHolder) view.getTag();
		}
		
		String caption = "";
		String content = knowledge.getContentText();
		if (this._titleField.equals(KnowledgeBase.SUB_CATEGORY)){
			caption = knowledge.getSubCategory();
		}
		else if (this._titleField.equals(KnowledgeBase.KEYWORD1)){
			caption = knowledge.getKeyword1();
		}
		else if (this._titleField.equals(KnowledgeBase.KEYWORD2)){
			caption = knowledge.getKeyword2();
		}
		else if (this._titleField.equals(KnowledgeBase.KEYWORD3)){
			caption = knowledge.getKeyword3();
		}	
		
		if (!this._hasImage){
			viewHolder.ivAboutImage.setVisibility(View.GONE);
		}
		else
		{
			viewHolder.ivAboutImage.setVisibility(View.VISIBLE);
		}
		viewHolder.tvAboutTitle.setText(caption);
		viewHolder.tvAboutContent.setText(content);

		Animation animation = AnimationUtils.loadAnimation(getContext(), (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);

		view.startAnimation(animation);
		lastPosition = position;
		
		return view;
	}

}
