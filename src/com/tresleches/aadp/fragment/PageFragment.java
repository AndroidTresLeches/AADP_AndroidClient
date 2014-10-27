package com.tresleches.aadp.fragment;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;

import com.tresleches.aadp.R;
import com.tresleches.aadp.activity.DonorActivity;

public class PageFragment extends Fragment {
	
	private String mContent = "???";
	private int mImageResId = 0;
	private int mPosition = 0;

	private static final String KEY_CONTENT = "PageFragment:Content";

	public static PageFragment newInstance(String content, int resId, int position) {
		PageFragment fragment = new PageFragment();
		
		fragment.mContent = content.toString();
		fragment.mImageResId = resId;
		fragment.mPosition = position;

		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		if ((savedInstanceState != null)
				&& savedInstanceState.containsKey(KEY_CONTENT)) {
			mContent = savedInstanceState.getString(KEY_CONTENT);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View v = inflater.inflate(R.layout.fragment_nested_page, container, false);
		TextView tvTest = (TextView)v.findViewById(R.id.tvTest);
		tvTest.setText(mContent);
		
		Animator zoomIn = AnimatorInflater.loadAnimator(v.getContext(), R.animator.zoom_in);
		zoomIn.setTarget(tvTest);
		zoomIn.setDuration(1000);
		Animator zoomBack = AnimatorInflater.loadAnimator(v.getContext(), R.animator.zoom_out);
		zoomBack.setTarget(tvTest);
		zoomBack.setDuration(1000);
		AnimatorSet showText = new AnimatorSet();
		showText.play(zoomIn).before(zoomBack);
		showText.start();
	    Button btStart = (Button)v.findViewById(R.id.btStart);
	    
		ImageView ivPageImage = (ImageView)v.findViewById(R.id.ivPageImage);
		if (mPosition ==3){
			ivPageImage.setScaleType(ScaleType.CENTER);
			btStart.setVisibility(View.VISIBLE);
			btStart.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					Intent i = new Intent(getActivity(), DonorActivity.class);
					startActivity(i);
				}
			});
		}
		else{
			ivPageImage.setScaleType(ScaleType.FIT_XY);
			btStart.setVisibility(View.GONE);
		}
		ivPageImage.setImageResource(mImageResId);
		
		
		return v;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		outState.putString(KEY_CONTENT, mContent);
	}
	
	
	
// example of store data in arguments and retrieve it
//	 static EditorFragment newInstance(int position) {
//		    EditorFragment frag=new EditorFragment();
//		    Bundle args=new Bundle();
//
//		    args.putInt(KEY_POSITION, position);
//		    frag.setArguments(args);
//
//		    return(frag);
//int position=getArguments().getInt(KEY_POSITION, -1);
	
	
//		  }
}
