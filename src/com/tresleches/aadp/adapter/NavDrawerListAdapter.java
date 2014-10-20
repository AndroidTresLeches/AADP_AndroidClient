package com.tresleches.aadp.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.tresleches.aadp.R;
import com.tresleches.aadp.model.NavDrawerItem;

public class NavDrawerListAdapter extends BaseAdapter {

	private Context context;
    private ArrayList<NavDrawerItem> navDrawerItems;
     
    public NavDrawerListAdapter(Context context, ArrayList<NavDrawerItem> navDrawerItems){
        this.context = context;
        this.navDrawerItems = navDrawerItems;
    }
 
    @Override
    public int getCount() {
        return navDrawerItems.size();
    }
 
    @Override
    public Object getItem(int position) {       
        return navDrawerItems.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = LayoutInflater.from(this.context);
            convertView = mInflater.inflate(R.layout.drawer_nav_item, null);
        }
          
        ImageView ivIcon = (ImageView) convertView.findViewById(R.id.ivIcon);
        TextView tvTitle = (TextView) convertView.findViewById(R.id.tvTitle);
          
        NavDrawerItem item = navDrawerItems.get(position);
        ivIcon.setImageResource(item.getIcon());        
        tvTitle.setText(item.getTitle());
         
        return convertView;
    }

}
