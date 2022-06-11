package com.example.bitirmeprojesi;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ImageButton imageButtonToprakNem,imageButtonToprakSicaklik,imageButtonHavaNem,imageButtonHavaSicaklik,imageButtonSuSeviye,imageButtonIsik,imageButtonHavaKalite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.navigationView);
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,0,0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.genel_item) {
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                    Intent intent = new Intent(MainActivity.this,GenelGorunum.class);
                    startActivity(intent);
                }
                else if (itemId == R.id.hava_nem_item) {
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                    Intent intent = new Intent(MainActivity.this,HavaNem.class);
                    startActivity(intent);
                }
                else if (itemId==R.id.toprak_nem_item)
                {
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                    Intent intent = new Intent(MainActivity.this,ToprakNem.class);
                    startActivity(intent);
                }
                else if (itemId==R.id.hava_sicaklik_item)
                {
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                    Intent intent = new Intent(MainActivity.this,HavaSicaklik.class);
                    startActivity(intent);
                }
                else if (itemId==R.id.toprak_sicaklik_item)
                {
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                    Intent intent = new Intent(MainActivity.this,ToprakSicaklik.class);
                    startActivity(intent);
                }
                else if (itemId==R.id.hava_kalite_item)
                {
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                    Intent intent = new Intent(MainActivity.this,HavaKalitesi.class);
                    startActivity(intent);
                }
                else if (itemId==R.id.su_seviye_item)
                {
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                    Intent intent = new Intent(MainActivity.this,SuSeviyesi.class);
                    startActivity(intent);
                }
                else if (itemId==R.id.isik_degeri_item)
                {
                    if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                        drawerLayout.closeDrawer(GravityCompat.START);
                    }
                    Intent intent = new Intent(MainActivity.this,IsikDegeri.class);
                    startActivity(intent);
                }

                return false;
            }
        });
        imageButtonToprakNem = findViewById(R.id.imageButtonToprakNem);
        imageButtonToprakSicaklik = findViewById(R.id.imageButtonToprakSicaklik);
        imageButtonHavaNem = findViewById(R.id.imageButtonHavaNem);
        imageButtonHavaSicaklik = findViewById(R.id.imageButtonHavaSicaklik);
        imageButtonSuSeviye = findViewById(R.id.imageButtonSuSeviye);
        imageButtonHavaKalite = findViewById(R.id.imageButtonHavaKalite);
        imageButtonIsik = findViewById(R.id.imageButtonIsik);

        imageButtonToprakNem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ToprakNem.class);
                startActivity(intent);
            }
        });
        imageButtonHavaNem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,HavaNem.class);
                startActivity(intent);
            }
        });
        imageButtonToprakSicaklik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ToprakSicaklik.class);
                startActivity(intent);
            }
        });
        imageButtonHavaSicaklik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,HavaSicaklik.class);
                startActivity(intent);
            }
        });
        imageButtonSuSeviye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,SuSeviyesi.class);
                startActivity(intent);
            }
        });
        imageButtonHavaKalite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,HavaKalitesi.class);
                startActivity(intent);
            }
        });
        imageButtonIsik.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,IsikDegeri.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setMessage("Uygulamadan Çıkıyorsunuz.").setCancelable(true).setPositiveButton("Evet", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finishAffinity();
                finish();
            }
        }).setNegativeButton("Hayır", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }

        });
        AlertDialog alert = builder.create();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else{
            alert.show();
        }
    }

}