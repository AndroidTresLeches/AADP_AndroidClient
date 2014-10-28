package com.tresleches.aadp.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tresleches.aadp.R;
import com.tresleches.aadp.fragment.PageFragment;
import com.tresleches.aadp.interfaces.IconPagerAdapter;


public class DonorFragmentPagerAdapter extends FragmentPagerAdapter implements IconPagerAdapter {
    protected static final String[] CONTENT = new String[] 
    		{ "Do you know ?", "Here is the fact ...", "Where is hope?", "Let's help by becoming a donor", };
    protected static final int[] ICONS = new int[] {
//            R.drawable.perm_group_calendar,
//            R.drawable.perm_group_camera,
//            R.drawable.perm_group_device_alarms,
//            R.drawable.perm_group_location
    };
    
    private int[] mImageRes = {
			R.drawable.donor_num6_new2,
			R.drawable.donor_2percent_new_small,
			R.drawable.donor_6n2_small,
			R.drawable.ic_bird};
    
    private Context _context = null;

    private int mCount = CONTENT.length;

    public DonorFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    
    public DonorFragmentPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        this._context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return PageFragment.newInstance(
        		CONTENT[position % CONTENT.length], 
        		mImageRes[position],
        	    position);
    }

    @Override
    public int getCount() {
        return mCount;
    }

    @Override
    public CharSequence getPageTitle(int position) {
      return DonorFragmentPagerAdapter.CONTENT[position % CONTENT.length];
    }

    @Override
    public int getIconResId(int index) {
      return ICONS[index % ICONS.length];
    }

    public void setCount(int count) {
        if (count > 0 && count <= 10) {
            mCount = count;
            notifyDataSetChanged();
        }
    }
}
