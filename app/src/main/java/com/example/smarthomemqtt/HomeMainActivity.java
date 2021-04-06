package com.example.smarthomemqtt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeMainActivity extends AppCompatActivity {
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.home_main);


            Button add_device_button = (Button) findViewById(R.id.add_device_button);
            add_device_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeMainActivity.this, AddDeviceActivity.class);
                    startActivity(intent);
                }
            });
        }
}

