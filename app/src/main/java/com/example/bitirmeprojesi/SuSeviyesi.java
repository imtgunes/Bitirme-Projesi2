package com.example.bitirmeprojesi;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Build;
import android.os.Bundle;
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

public class SuSeviyesi extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView textViewSuSeviye;
    private ImageView imageViewSuSeviye,imageViewSuUyari;
    private ProgressBar progressBarSuSeviye;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_su_seviyesi);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_bg_su_seviye, this.getTheme()));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusbar_bg_su_seviye, this.getTheme()));
        } else {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_bg_su_seviye));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusbar_bg_su_seviye));
        }

        toolbar = findViewById(R.id.toolbar8);
        imageViewSuSeviye = findViewById(R.id.imageViewSuSeviye);
        imageViewSuUyari = findViewById(R.id.imageViewSuUyari);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textViewSuSeviye = findViewById(R.id.textViewSs);
        progressBarSuSeviye = findViewById(R.id.progressBarSuSeviyesi);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefSs = database.getReference("su_seviye");

        myRefSs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                int suSeviyeYüzde = (int) (Integer.parseInt(value)*100/520);
                if(suSeviyeYüzde >100){
                    progressBarSuSeviye.setProgress(100);
                    textViewSuSeviye.setText(100+"%");
                }
                else{
                    progressBarSuSeviye.setProgress(suSeviyeYüzde);
                    textViewSuSeviye.setText("%"+String.valueOf((int) (Integer.parseInt(value)*100/520)));
                    if(suSeviyeYüzde <= 0){
                        imageViewSuSeviye.setImageResource(R.drawable.su0);
                        imageViewSuUyari.setVisibility(View.VISIBLE);
                    }
                    else if(suSeviyeYüzde <= 20){
                        imageViewSuSeviye.setImageResource(R.drawable.su20);
                        imageViewSuUyari.setVisibility(View.VISIBLE);
                    }
                    else if(suSeviyeYüzde <= 40){
                        imageViewSuSeviye.setImageResource(R.drawable.su40);
                        imageViewSuUyari.setVisibility(View.INVISIBLE);
                    }
                    else if(suSeviyeYüzde <= 60){
                        imageViewSuSeviye.setImageResource(R.drawable.su60);
                        imageViewSuUyari.setVisibility(View.INVISIBLE);
                    }
                    else if(suSeviyeYüzde <= 80){
                        imageViewSuSeviye.setImageResource(R.drawable.su80);
                        imageViewSuUyari.setVisibility(View.INVISIBLE);
                    }
                    else if(suSeviyeYüzde <= 100){
                        imageViewSuSeviye.setImageResource(R.drawable.su100);
                        imageViewSuUyari.setVisibility(View.INVISIBLE);
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