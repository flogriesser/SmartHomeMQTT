package com.example.smarthomemqtt;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarthomemqtt.AddDevices.DeviceChoice;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NotificationMessages extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);


        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.add_NEW_Device:
                        Intent menuIntent = new Intent(NotificationMessages.this, DeviceChoice.class);
                        startActivity(menuIntent);
                        break;
                    case R.id.Home:
                        Intent Home = new Intent(NotificationMessages.this, HomeMainActivity.class);
                        startActivity(Home);
                        break;
                }
                return false;
            }
        });
        
        
        
    }

}

