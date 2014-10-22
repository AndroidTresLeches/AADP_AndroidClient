package com.tresleches.aadp.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cuubonandroid.sugaredlistanimations.GNowListAdapter;
import com.cuubonandroid.sugaredlistanimations.SpeedScrollListener;
import com.tresleches.aadp.R;
import com.tresleches.aadp.model.KnowledgeBase;

public class NowListAdapter extends GNowListAdapter {
	private Context _context;
	private ArrayList<KnowledgeBase> _list;

	private static class ViewHolder {
		ImageView ivAboutImage;
		TextView tvAboutTitle;
		TextView tvAboutContent;
	}

	public NowListAdapter(Context context, SpeedScrollListener scrollListener,
			ArrayList<KnowledgeBase> list) {
		super(context, scrollListener, list);
		this._context = context;
		this._list = list;
	}

	@Override
	protected View getRowView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		KnowledgeBase knowledge = _list.get(position);

		if (convertView == null) {
			convertView = LayoutInflater.from(context).inflate(
					R.layout.about_list_item, parent, false);

			holder = new ViewHolder();
			holder.tvAboutTitle = (TextView) convertView
					.findViewById(R.id.tvAboutTitle);
			holder.tvAboutContent = (TextView) convertView
					.findViewById(R.id.tvAboutContent);

			convertView.setTag(holder);
		} else
			holder = (ViewHolder) convertView.getTag();

		holder.tvAboutTitle.setText(knowledge.getSubCategory());
		holder.tvAboutContent.setText(knowledge.getContentText());

		return convertView;
	}
}
