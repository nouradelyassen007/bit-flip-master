package com.epicodus.bitflip.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.epicodus.bitflip.Constants;
import com.epicodus.bitflip.R;
import com.epicodus.bitflip.model.Item;
import com.epicodus.bitflip.ui.ItemDetailActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import org.parceler.Parcel;
import org.parceler.Parcels;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by DroAlvarez on 12/8/16.
 */

public class FirebaseItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;

    @Bind(R.id.itemImageView) ImageView mItemImageView;
    @Bind(R.id.itemNameTextView) TextView mNameTextView;
    @Bind(R.id.categoryTextView) TextView mCategoryTextView;
    @Bind(R.id.priceTextView) TextView mPriceTextView;

    private String mCategory;

    View mView;
    Context mContext;

    public FirebaseItemViewHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindItem(Item item) {
        ButterKnife.bind(this, mView);

        Picasso.with(mContext)
                .load(item.getImageUrls().get(0))
                .resize(MAX_WIDTH, MAX_HEIGHT)
                .centerCrop()
                .into(mItemImageView);

        mNameTextView.setText(item.getName());
        mCategoryTextView.setText(item.getCategory());
        mPriceTextView.setText("$" + item.getPrice());
        mCategory = item.getCategory();
    }

    @Override
    public void onClick(View v) {
        final ArrayList<Item> items = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_CATEGORIES).child(mCategory);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    items.add(snapshot.getValue(Item.class));
                }
                int itemPosition = getLayoutPosition();
                Item item = items.get(itemPosition);

                Intent intent = new Intent(mContext, ItemDetailActivity.class);
                intent.putExtra("item", Parcels.wrap(item));

                mContext.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
