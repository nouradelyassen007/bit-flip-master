package com.epicodus.bitflip.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.epicodus.bitflip.ui.ItemDetailFragment;

import java.util.List;

/**
 * Created by DroAlvarez on 12/13/16.
 */

public class ImagePagerAdapter extends FragmentPagerAdapter {
    private List<String> mImageUrls;

    public ImagePagerAdapter(FragmentManager fm, List<String> imageUrls) {
        super(fm);
        mImageUrls = imageUrls;
    }

    @Override
    public Fragment getItem(int position) {
        return ItemDetailFragment.newInstance(mImageUrls.get(position));
    }

    @Override
    public int getCount() {
        return mImageUrls.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mImageUrls.get(position);
    }
}
