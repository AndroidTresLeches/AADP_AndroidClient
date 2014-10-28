package com.tresleches.aadp.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tresleches.aadp.R;
import com.tresleches.aadp.adapter.DonorFragmentPagerAdapter;
import com.tresleches.aadp.interfaces.PageIndicator;
import com.tresleches.aadp.ui.CirclePageIndicator;

public class DonorPagerFragment extends Fragment {

	DonorFragmentPagerAdapter mAdapter;
	ViewPager mPager;
	CirclePageIndicator mIndicator;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_donor_pager, container,
				false);
		mPager = (ViewPager) v.findViewById(R.id.pager);

		mAdapter =  buildAdapter();
		mPager.setAdapter(mAdapter);

		mIndicator = (CirclePageIndicator)v.findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);
        mIndicator.setCurrentItem(0);
        mIndicator.setSnap(true);
		
		return v;
	}

	private DonorFragmentPagerAdapter buildAdapter() {
		return (new DonorFragmentPagerAdapter(getActivity(), getChildFragmentManager()));
	}
}
