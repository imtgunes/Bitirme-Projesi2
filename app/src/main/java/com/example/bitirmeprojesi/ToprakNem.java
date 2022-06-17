package com.example.bitirmeprojesi;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ToprakNem extends AppCompatActivity {
    private ImageView imageView;
    private ProgressBar progressBarToprakNem,progressBarSuSeviye;
    private TextView textViewToprakNem;
    private Toolbar toolbar;
    private TextView textViewSuSeviye,textViewToprakNemDegeri;
    private TextView textViewDurum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toprak_nem);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_bg_toprak_nem, this.getTheme()));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusbar_bg_toprak_nem, this.getTheme()));
        } else {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_bg_toprak_nem));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusbar_bg_toprak_nem));
        }

        toolbar = findViewById(R.id.toolbar2);
        imageView = findViewById(R.id.toprakNemGif);
        textViewToprakNem = findViewById(R.id.textViewTn);
        progressBarToprakNem = findViewById(R.id.progressBarToprakNem);
        progressBarSuSeviye = findViewById(R.id.progressBarTSuSeviye);
        textViewSuSeviye = findViewById(R.id.textViewSuGenelSeviyesi);
        textViewDurum = findViewById(R.id.textViewDurum);
        textViewToprakNemDegeri = findViewById(R.id.textViewGenelToprakNemDegeri);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.icon_back);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefTn = database.getReference("toprak_nem");
        DatabaseReference myRefSs = database.getReference("su_seviye");

        myRefSs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if(!TextUtils.isEmpty(value)){
                    textViewSuSeviye.setText(value);
                    int suSeviyeYüzde = (int) (Integer.parseInt(value)*100/520);
                    progressBarSuSeviye.setProgress(suSeviyeYüzde);
                    if(Integer.parseInt(String.valueOf(textViewToprakNemDegeri.getText()))>512 && Integer.parseInt(String.valueOf(textViewSuSeviye.getText())) > 10){
                        imageView.setImageResource(R.drawable.sulama);
                        textViewDurum.setText("Bitki şuanda sulanıyor");
                    }
                    else{
                        imageView.setImageResource(R.drawable.sulama_durma);
                        textViewDurum.setText("Bitki şuanda sulanmıyor");

                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        myRefTn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if(!TextUtils.isEmpty(value)){
                    textViewToprakNemDegeri.setText(value);
                    progressBarToprakNem.setProgress(100-(int) (Integer.parseInt(value)*100/1023));
                    textViewToprakNem.setText("%"+String.valueOf(100-(int) (Integer.parseInt(value)*100/1023)));
                    if(Integer.parseInt(value)>512 && Integer.parseInt(String.valueOf(textViewSuSeviye.getText())) > 10){
                        textViewDurum.setText("Bitki şuanda sulanıyor");
                        imageView.setImageResource(R.drawable.sulama);
                    }
                    else{
                        textViewDurum.setText("Bitki şuanda sulanmıyor");
                        imageView.setImageResource(R.drawable.sulama_durma);
                    }
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

    }
}