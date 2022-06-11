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

public class ToprakSicaklik extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView textViewToprakSicaklik;
    private ProgressBar progressBarToprakSicaklik;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toprak_sicaklik);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_bg_toprak_sicaklik, this.getTheme()));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusbar_bg_toprak_sicaklik, this.getTheme()));
        } else {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_bg_toprak_sicaklik));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusbar_bg_toprak_sicaklik));
        }

        toolbar = findViewById(R.id.toolbar7);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textViewToprakSicaklik = findViewById(R.id.textViewTs);
        progressBarToprakSicaklik=findViewById(R.id.progressBarToprakSicaklik);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefTs = database.getReference("toprak_sicaklik");

        myRefTs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (!TextUtils.isEmpty(value)) {
                    progressBarToprakSicaklik.setProgress((int) Float.parseFloat(value));
                    textViewToprakSicaklik.setText(String.valueOf(Float.parseFloat(value))+"°C");
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }
}