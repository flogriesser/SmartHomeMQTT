package com.example.smarthomemqtt.AddDevices;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarthomemqtt.R;

public class DeviceChoice extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.device_choice);

        Button save_device_button = (Button) findViewById(R.id.ownDeviceButton);
        save_device_button.setOnClickListener(this); // calling onClick() method
        Button save_ControlDeviceButton = (Button) findViewById(R.id.save_ControlDeviceButton);
        save_ControlDeviceButton.setOnClickListener(this);
        Button saveDeviceToControlDevice = (Button) findViewById(R.id.saveDeviceToControllDevice);
        saveDeviceToControlDevice.setOnClickListener(this);
    }

        @Override
        public void onClick(View v){
            switch (v.getId()) {
                case R.id.ownDeviceButton:
                    Intent intent1 = new Intent(DeviceChoice.this, AddDeviceActivity.class);
                    startActivity(intent1);
                    break;
                case R.id.save_ControlDeviceButton:
                    Intent intent2 = new Intent(DeviceChoice.this, AddControlDevice.class);
                    startActivity(intent2);
                    break;
                case R.id.saveDeviceToControllDevice:
                    Intent intent3 = new Intent(DeviceChoice.this, AddSocket.class);
                    startActivity(intent3);
                    break;
                default:
                    break;
            }
        }
}


