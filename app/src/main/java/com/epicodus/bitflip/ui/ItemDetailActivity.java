package com.epicodus.bitflip.ui;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.epicodus.bitflip.R;
import com.epicodus.bitflip.adapters.ImagePagerAdapter;
import com.epicodus.bitflip.model.Item;
import com.google.firebase.database.ThrowOnExtraProperties;

import org.parceler.Parcels;
import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ItemDetailActivity extends AppCompatActivity implements View.OnClickListener {
    @Bind(R.id.itemNameTextView) TextView mItemNameTextView;
    @Bind(R.id.itemCategoryTextView) TextView mItemCategoryTextView;
    @Bind(R.id.itemDescriptionTextView) TextView mItemDescriptionTextView;
    @Bind(R.id.itemPriceTextView) TextView mItemPriceTextView;
    @Bind(R.id.ownerTextView) TextView mOwnerTextView;
    @Bind(R.id.emailTextView) TextView mEmailTextView;
    @Bind(R.id.emailOwnerButton) Button mEmailOwnerButton;
    @Bind(R.id.viewPager) ViewPager mViewPager;

    private Item mItem;
    private ImagePagerAdapter adapterViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_detail);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        mItem = Parcels.unwrap(intent.getParcelableExtra("item"));

        mItemNameTextView.setText(mItem.getName());
        mItemCategoryTextView.setText(mItem.getCategory());
        mItemDescriptionTextView.setText(mItem.getDescription());
        mItemPriceTextView.setText("$" + mItem.getPrice());
        mOwnerTextView.setText(mItem.getOwnerName());
        mEmailTextView.setText(mItem.getOwnerEmail());

        adapterViewPager = new ImagePagerAdapter(getSupportFragmentManager(), mItem.getImageUrls());
        mViewPager.setAdapter(adapterViewPager);
        mViewPager.setCurrentItem(0);

        mEmailOwnerButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mEmailOwnerButton) {
            Intent emailIntent = new Intent(Intent.ACTION_SEND);
            String title = "Email with: ";
            emailIntent.setType("text/plain");
            emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {mItem.getOwnerEmail()});
            Intent chooser = Intent.createChooser(emailIntent, title);
            if (emailIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(chooser);
            }
        }
    }
}
