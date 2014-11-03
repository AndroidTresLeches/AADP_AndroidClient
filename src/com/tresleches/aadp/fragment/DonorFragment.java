package com.tresleches.aadp.fragment;

import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.cuubonandroid.sugaredlistanimations.SpeedScrollListener;
import com.tresleches.aadp.R;
import com.tresleches.aadp.activity.DonorActivity;
import com.tresleches.aadp.adapter.PlusListAdapter;
//import com.tresleches.aadp.adapter.NowListAdapter;

public class DonorFragment extends Fragment {
	private SpeedScrollListener listener;
	private PlusListAdapter plusAdapter;
	//private NowListAdapter nowAdapter;
	private List<Bitmap> donorImageList;
	private List<String> donorTitles;

	@Override
	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		Bitmap[] bList = {
				BitmapFactory.decodeResource(getResources(),
						R.drawable.donor_num6_new2),
				BitmapFactory.decodeResource(getResources(),
						R.drawable.donor_2percent_new_small),
				BitmapFactory.decodeResource(getResources(),
						R.drawable.donor_6n2_small) };
		String[] titles = { "1", "2", "3" };
		donorImageList = Arrays.asList(bList);
		donorTitles = Arrays.asList(titles);

		listener = new SpeedScrollListener();
		plusAdapter = new PlusListAdapter(getActivity(), listener, donorTitles,
				donorImageList);
	}

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_donor, container, false);
		ListView lvDonor = (ListView)v.findViewById(R.id.lvDonor);
		lvDonor.setAdapter(plusAdapter);
		
		lvDonor.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent i = new Intent(getActivity(), DonorActivity.class);
				startActivity(i);
				getActivity().overridePendingTransition(R.anim.right_in, R.anim.left_out);
			}
		});
		
		
		return v;
	}

}
