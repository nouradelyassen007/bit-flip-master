package com.epicodus.bitflip.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.bitflip.Constants;
import com.epicodus.bitflip.model.Item;
import com.epicodus.bitflip.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewItemActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.newItemDescription) EditText mNewItemDescription;
    @Bind(R.id.newItemName) EditText mNewItemName;
    @Bind(R.id.newItemPrice) EditText mNewItemPrice;
    @Bind(R.id.spinnerCategory) Spinner mNewItemCategory;
    @Bind(R.id.imageUrlEditText) EditText mImageUrl;
    @Bind(R.id.newItemButton) Button mNewItemButton;
    @Bind(R.id.comparePricesButton) Button mComparePricesButton;
    @Bind(R.id.addCategoryButton) Button mAddCategoryButton;

    private SharedPreferences mSharedPreferences;
    private DatabaseReference ref;
    private String mNewCategory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_item);
        ButterKnife.bind(this);

        ref = FirebaseDatabase.getInstance().getReference();
        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mNewCategory = mSharedPreferences.getString(Constants.PREFERENCES_CATEGORY_KEY, null);


        mNewItemButton.setOnClickListener(this);
        mComparePricesButton.setOnClickListener(this);
        mAddCategoryButton.setOnClickListener(this);

        ref.child(Constants.FIREBASE_CHILD_CATEGORIES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final List<String> categories = new ArrayList<String>();
                if(mNewCategory != null) {
                    categories.add(mNewCategory);
                }
                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String category = snapshot.getKey();
                    categories.add(category);
                }

                ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(NewItemActivity.this, android.R.layout.simple_spinner_item, categories);
                mNewItemCategory.setAdapter(categoryAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == mNewItemButton) {
            String newItemCategory = mNewItemCategory.getSelectedItem().toString();
            String newItemDescription = mNewItemDescription.getText().toString();
            String newItemName = mNewItemName.getText().toString();
            String newItemPrice = mNewItemPrice.getText().toString();
            String newItemImageUrl = mImageUrl.getText().toString();

            boolean validDescription = isValid(newItemDescription, mNewItemDescription);
            boolean validName = isValid(newItemName, mNewItemName);
            boolean validImageUrl = isValid(newItemImageUrl, mImageUrl);
            boolean validPrice = isValid(newItemPrice, mNewItemPrice);

            if(validDescription && validName && validImageUrl && validImageUrl && validPrice) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String uid = user.getUid();
                String ownerEmail = user.getEmail();
                String ownerName = user.getDisplayName();
                Item newItem = new Item(newItemCategory, newItemName, newItemDescription, newItemPrice, newItemImageUrl, ownerEmail, ownerName);
                saveItemToCategory(newItemCategory, newItem);
                saveItemToUser(uid, newItem);
                saveItemToDatabase(newItem);
                Intent intent = new Intent(NewItemActivity.this, MainActivity.class);
                Toast.makeText(NewItemActivity.this, "Item saved.", Toast.LENGTH_SHORT).show();
                startActivity(intent);
            }
        } else if(v == mComparePricesButton) {
            String newItemName = mNewItemName.getText().toString();
            if(isValid(newItemName, mNewItemName)) {
                Intent intent = new Intent(NewItemActivity.this, ComparePricesActivity.class);
                intent.putExtra("itemName", newItemName);
                startActivity(intent);
            }
        } else if(v == mAddCategoryButton) {
            Intent intent = new Intent(NewItemActivity.this, NewCategoryActivity.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mSharedPreferences.edit().clear().commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mSharedPreferences.edit().clear().commit();
    }

    private void saveItemToDatabase(Item item) {
        ref.child(Constants.FIREBASE_CHILD_ITEMS).push().setValue(item);
    }

    private void saveItemToCategory(String category, Item item) {
        DatabaseReference categoryRef = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_CATEGORIES)
                .child(category);
        categoryRef.push().setValue(item);
    }

    private void saveItemToUser(String userId, Item item) {
        DatabaseReference userRef = FirebaseDatabase
                .getInstance()
                .getReference(Constants.FIREBASE_CHILD_USERS)
                .child(userId);
        DatabaseReference pushRef = userRef.push();
        String pushId = pushRef.getKey();
        item.setPushId(pushId);
        pushRef.setValue(item);
    }

    private boolean isValid(String text, EditText editText) {
        if(text.equals("")) {
            editText.setError("Please fill out this field.");
            return false;
        }
        return true;
    }
}
