package com.epicodus.bitflip.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.epicodus.bitflip.R;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class ItemDetailFragment extends Fragment {
    @Bind(R.id.itemImageView) ImageView mItemImageView;
    private String mImageUrl;

    public static ItemDetailFragment newInstance(String imageUrl) {
        Bundle args = new Bundle();
        ItemDetailFragment fragment = new ItemDetailFragment();
        args.putString("imageUrl", imageUrl);
        fragment.setArguments(args);
        return fragment;
    }
    public ItemDetailFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mImageUrl = getArguments().getString("imageUrl");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_detail, container, false);
        ButterKnife.bind(this, view);

        Picasso.with(view.getContext()).load(mImageUrl).into(mItemImageView);
        return view;
    }

}
