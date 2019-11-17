package com.epicodus.bitflip.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.epicodus.bitflip.Constants;
import com.epicodus.bitflip.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import butterknife.Bind;
import butterknife.ButterKnife;

public class NewCategoryActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.addCategoryEditText) EditText mAddCategoryEditText;
    @Bind(R.id.categoryButton) Button mCategoryButton;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_category);
        ButterKnife.bind(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();

        mCategoryButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mCategoryButton) {
            String category = mAddCategoryEditText.getText().toString();
            addToSharedPreferences(category);
            Intent intent = new Intent(NewCategoryActivity.this, NewItemActivity.class);
            startActivity(intent);
        }
    }

    public void addToSharedPreferences(String category) {
        mEditor.putString(Constants.PREFERENCES_CATEGORY_KEY, category).apply();
    }
}
