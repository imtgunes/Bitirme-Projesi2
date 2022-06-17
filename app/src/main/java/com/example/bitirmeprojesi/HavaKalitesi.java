package com.example.bitirmeprojesi;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HavaKalitesi extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView imageView,imageViewHavaKalitesi,imageViewDurum;
    private TextView textViewHavaKalitesiDegeri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hava_kalitesi);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_bg_hava_kalite, this.getTheme()));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusbar_bg_hava_kalite, this.getTheme()));
        } else {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_bg_hava_kalite));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusbar_bg_hava_kalite));
        }

        toolbar = findViewById(R.id.toolbar4);
        imageView = findViewById(R.id.imageViewHavalandırmaGif);
        imageViewHavaKalitesi = findViewById(R.id.imageViewHavaKalite);
        imageViewDurum = findViewById(R.id.imageViewHavalandirmaOnOff);
        textViewHavaKalitesiDegeri = findViewById(R.id.textViewHavaKalitesiDegeri);

        imageView.setImageResource(R.drawable.hava);

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
        DatabaseReference myRefHk = database.getReference("hava_kalitesi");

        myRefHk.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (!TextUtils.isEmpty(value)) {
                    textViewHavaKalitesiDegeri.setText(value);
                    if(Integer.parseInt(value)  < 100){
                        Drawable drawable = imageView.getDrawable();
                        if (drawable instanceof Animatable) {
                            ((Animatable) drawable).stop();
                        }
                        imageViewHavaKalitesi.setImageResource(R.drawable.hava_kalite_iyi);
                        imageViewDurum.setImageResource(R.drawable.ic_switch_off);
                    }
                    else if (Integer.parseInt(value)  < 300){
                        Drawable drawable = imageView.getDrawable();
                        if (drawable instanceof Animatable) {
                            ((Animatable) drawable).stop();
                        }
                        imageViewHavaKalitesi.setImageResource(R.drawable.hava_kalite_orta);
                        imageViewDurum.setImageResource(R.drawable.ic_switch_off);
                    }
                    else if (Integer.parseInt(value)  > 300 && Integer.parseInt(value)  < 400){
                        Drawable drawable = imageView.getDrawable();
                        if (drawable instanceof Animatable) {
                            ((Animatable) drawable).stop();
                        }
                        imageViewHavaKalitesi.setImageResource(R.drawable.hava_kalite_orta);
                        imageViewDurum.setImageResource(R.drawable.ic_switch_off);
                    }
                    else if (Integer.parseInt(value)  > 400){
                        Glide.with(HavaKalitesi.this).load(R.drawable.hava_gifi).into(imageView);
                        Drawable drawable = imageView.getDrawable();
                        if (drawable instanceof Animatable) {
                            ((Animatable) drawable).start();
                        }
                        imageViewHavaKalitesi.setImageResource(R.drawable.hava_kalite_kotu);
                        imageViewDurum.setImageResource(R.drawable.ic_switch_on);
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
