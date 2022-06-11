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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IsikDegeri extends AppCompatActivity {
    private Toolbar toolbar;
    private ImageView imageViewIsitmaOnOff,imageViewLedOnOff,imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isik);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_bg_isik, this.getTheme()));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusbar_bg_isik, this.getTheme()));
        } else {
            getWindow().setStatusBarColor(getResources().getColor(R.color.statusbar_bg_isik));
            getWindow().setNavigationBarColor(getResources().getColor(R.color.statusbar_bg_isik));
        }

        toolbar = findViewById(R.id.toolbar9);
        imageViewIsitmaOnOff = findViewById(R.id.imageViewIsitmaOnOff);
        imageViewLedOnOff = findViewById(R.id.imageViewLedOnOff);
        imageView = findViewById(R.id.imageView2);

        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRefIsik = database.getReference("isik_deger");
        DatabaseReference myRefHs = database.getReference("hava_sicaklik");

        myRefIsik.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                if (TextUtils.isEmpty(value)) {

                }
                else if(value.length()>2 ){
                    int isikDegeri = Integer.parseInt(value.substring(0,3));
                    if(isikDegeri > 600){
                        imageViewLedOnOff.setImageResource(R.drawable.ic_switch_on);
                        imageView.setImageResource(R.drawable.isiklandirma_on);

                    }
                    else{
                        imageViewLedOnOff.setImageResource(R.drawable.ic_switch_off);
                        imageView.setImageResource(R.drawable.isiklandirma_off);
                    }
                }
                else {
                    if(value.length() > 0) {

                    }
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
                    if(Float.parseFloat(value) < 21.0){
                        imageViewLedOnOff.setImageResource(R.drawable.ic_switch_on);
                    }else{
                        imageViewIsitmaOnOff.setImageResource(R.drawable.ic_switch_off);
                    }
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