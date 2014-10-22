package com.tresleches.aadp.adapter;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cuubonandroid.sugaredlistanimations.GPlusListAdapter;
import com.cuubonandroid.sugaredlistanimations.SpeedScrollListener;
import com.tresleches.aadp.R;

public class PlusListAdapter extends GPlusListAdapter {
	  private Context _context;
	  private List<Bitmap> _imageList;
	  private List<String> _titles;
	  
	  private static class ViewHolder {
		    ImageView ivDImage;
		    TextView tvDRow;
		  }

	  public PlusListAdapter(Context context, SpeedScrollListener scrollListener, 
			  List<String> titles,
			  List<Bitmap> bmpList) {
	    super(context, scrollListener, titles);
	    this._context = context;
	    _imageList = bmpList;
	    _titles = titles;
	    }
	  
	  @Override
	  protected View getRowView(int position, View convertView, ViewGroup parent) {
	    ViewHolder holder;

	    if (convertView == null) {
	      convertView = LayoutInflater.from(context).inflate(R.layout.donor_item, parent, false);

	      holder = new ViewHolder();
	      holder.ivDImage = (ImageView) convertView.findViewById(R.id.ivDimage);
	      holder.tvDRow = (TextView) convertView.findViewById(R.id.tvDRow);

	      convertView.setTag(holder);
	    } else{
	      holder = (ViewHolder) convertView.getTag();
	    }

	    holder.ivDImage.setImageBitmap(_imageList.get(position));
	    holder.tvDRow.setText(_titles.get(position));

	    return convertView;
	  }

	
	}

