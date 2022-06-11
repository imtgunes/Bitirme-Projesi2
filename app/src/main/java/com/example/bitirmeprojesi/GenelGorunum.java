package com.example.bitirmeprojesi;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
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

public class GenelGorunum extends AppCompatActivity {
    private Toolbar toolbar;
    private ProgressBar progressBarSuSeviye;
    private TextView textViewToprakNem,textViewHavaNem,textViewToprakSicakligi,textViewHavaSicakligi,textViewSuSeviye,textViewUyari;
    private ImageView imageViewIsik,imageViewIsitma,imageViewHavaKalite,imageViewIsikDurum,imageViewIsitmaDurum,imageViewUyari;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_genel_gorunum);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_bg_genel, this.getTheme()));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusbar_bg_genel, this.getTheme()));
        } else {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_bg_genel));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusbar_bg_genel));
        }

        imageViewHavaKalite = findViewById(R.id.imageViewGenelHavaKalite);
        imageViewIsik = findViewById(R.id.imageViewGeneIsik);
        imageViewIsitma = findViewById(R.id.imageViewGenelIsitma);
        imageViewIsikDurum = findViewById(R.id.imageViewGenelIsikDurum);
        imageViewIsitmaDurum = findViewById(R.id.imageViewGenelIsitmaDurum);
        imageViewUyari = findViewById(R.id.imageViewGenelUyari);
        textViewToprakNem = findViewById(R.id.textViewGenelToprakNem);
        textViewHavaNem = findViewById(R.id.textViewGenelHavaNem);
        textViewToprakSicakligi = findViewById(R.id.textViewGenelToprakSicaklik);
        textViewHavaSicakligi = findViewById(R.id.textViewGenelHavaSicakligi);
        textViewSuSeviye = findViewById(R.id.textViewGenelSuSeviye);
        textViewUyari = findViewById(R.id.textViewGenelUyari);
        progressBarSuSeviye = findViewById(R.id.progressBarGenelSuSeviye);

        toolbar = findViewById(R.id.toolbar3);
        toolbar.setTitle("");
        toolbar.setBackgroundColor(Color.parseColor("#31a05f"));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefHk = database.getReference("hava_kalitesi");
        DatabaseReference myRefTn = database.getReference("toprak_nem");
        DatabaseReference myRefSs = database.getReference("su_seviye");
        DatabaseReference myRefTs = database.getReference("toprak_sicaklik");
        DatabaseReference myRefHn = database.getReference("hava_nem");
        DatabaseReference myRefHs = database.getReference("hava_sicaklik");
        DatabaseReference myRefIsik = database.getReference("isik_deger");

        myRefHk.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);

                if(Integer.parseInt(value)  < 100){
                    imageViewHavaKalite.setImageResource(R.drawable.hava_kalite_iyi);

                }
                else if (Integer.parseInt(value)  < 300){
                    imageViewHavaKalite.setImageResource(R.drawable.hava_kalite_orta);

                }
                else if (Integer.parseInt(value)  > 400){
                    imageViewHavaKalite.setImageResource(R.drawable.hava_kalite_kotu);
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
                textViewToprakNem.setText(String.valueOf(100-(int) (Integer.parseInt(value)*100/1023))+"%");
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

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
                    textViewSuSeviye.setText(String.valueOf((int) (Integer.parseInt(value)*100/520))+"%");
                    if(suSeviyeYüzde < 10){
                        imageViewUyari.setVisibility(View.VISIBLE);
                        textViewUyari.setVisibility(View.VISIBLE);
                    }else{
                        imageViewUyari.setVisibility(View.INVISIBLE);
                        textViewUyari.setVisibility(View.INVISIBLE);
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        myRefTs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (!TextUtils.isEmpty(value)) {
                    textViewToprakSicakligi.setText(String.valueOf(Float.parseFloat(value))+"°C");
                }

            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        myRefHn.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (!TextUtils.isEmpty(value) && value.length()>2 && value.length()<9 && !value.contains("NAN")) {
                    textViewHavaNem.setText(String.format("%.02f", Float.parseFloat(value))+"%");
                    Log.d(TAG, "Value is: " + value);
                } else {

                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        myRefHs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (!TextUtils.isEmpty(value) && !value.contains("NAN")) {
                    textViewHavaSicakligi.setText(String.valueOf(Float.parseFloat(value))+"°C");
                    if(Float.parseFloat(value) < 21.0){
                        imageViewIsitma.setImageResource(R.drawable.istma_acik);
                        imageViewIsitmaDurum.setVisibility(View.INVISIBLE);
                    }else{
                        imageViewIsitmaDurum.setVisibility(View.VISIBLE);
                        imageViewIsitma.setImageResource(R.drawable.isitma_kapali);
                    }
                } else {

                }
            }
            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });

        myRefIsik.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (TextUtils.isEmpty(value)) {

                }
                else if(value.length()>2){
                    int isikDegeri = Integer.parseInt(value.substring(0,3));
                    if(isikDegeri > 600){
                        imageViewIsikDurum.setVisibility(View.INVISIBLE);
                        imageViewIsik.setImageResource(R.drawable.isiklandirma_on);

                    }
                    else{
                        imageViewIsikDurum.setVisibility(View.VISIBLE);
                        imageViewIsik.setImageResource(R.drawable.isiklandirma_off);
                    }
                }
                else {
                    if(value.length() > 0) {
                        imageViewIsikDurum.setVisibility(View.VISIBLE);
                        imageViewIsik.setImageResource(R.drawable.isiklandirma_off);
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