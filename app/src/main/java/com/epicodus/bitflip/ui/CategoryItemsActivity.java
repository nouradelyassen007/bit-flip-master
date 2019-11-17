package com.epicodus.bitflip.ui;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.epicodus.bitflip.Constants;
import com.epicodus.bitflip.R;
import com.epicodus.bitflip.adapters.FirebaseItemViewHolder;
import com.epicodus.bitflip.model.Item;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class CategoryItemsActivity extends AppCompatActivity {
    public static final String TAG = CategoryItemsActivity.class.getSimpleName();
    @Bind(R.id.searchItemsRecyclerView) RecyclerView mSearchItemsRecyclerView;

    private DatabaseReference mItemReference;
    private FirebaseRecyclerAdapter mFirebaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_items);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String category = intent.getStringExtra("category");
        Log.v(TAG, category);

        mItemReference = FirebaseDatabase.getInstance().getReference(Constants.FIREBASE_CHILD_CATEGORIES).child(category);
        setUpFirebaseAdapter();

    }


    private void setUpFirebaseAdapter() {
        mFirebaseAdapter = new FirebaseRecyclerAdapter<Item, FirebaseItemViewHolder>(Item.class, R.layout.bitflip_list_item, FirebaseItemViewHolder.class, mItemReference) {
            @Override
            protected void populateViewHolder(FirebaseItemViewHolder viewHolder, Item model, int position) {
                viewHolder.bindItem(model);
            }
        };
        mSearchItemsRecyclerView.setHasFixedSize(true);
        mSearchItemsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mSearchItemsRecyclerView.setAdapter(mFirebaseAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mFirebaseAdapter.cleanup();
    }
}
