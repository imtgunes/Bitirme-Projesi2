package com.example.bitirmeprojesi;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;


import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HavaNem extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView textViewHavaNem;
    private ProgressBar progressBarHavaNem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hava_nem);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_bg_hava_nem, this.getTheme()));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusbar_bg_hava_nem, this.getTheme()));
        } else {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_bg_hava_nem));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusbar_bg_hava_nem));
        }

        toolbar = findViewById(R.id.toolbar5);
        toolbar.setTitle("");

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textViewHavaNem = findViewById(R.id.textViewHn);
        progressBarHavaNem = findViewById(R.id.progressBarHavaNem);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefHn = database.getReference("hava_nem");

        myRefHn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (!TextUtils.isEmpty(value) && value.length()>2 && value.length()<9 && !value.contains("NAN")) {
                    progressBarHavaNem.setProgress((int) Float.parseFloat(value));
                    textViewHavaNem.setText("%"+String.format("%.02f", Float.parseFloat(value)));
                    Log.d(TAG, "Value is: " + value);
                } else {

                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
}