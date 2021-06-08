package com.example.smarthomemqtt.AddDevices;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarthomemqtt.CommunicationActivity;
import com.example.smarthomemqtt.Constants;
import com.example.smarthomemqtt.HomeMainActivity;
import com.example.smarthomemqtt.NotificationMessages;
import com.example.smarthomemqtt.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

public class AddControlDevice extends AppCompatActivity {

    public EditText group_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_controlldevice);

        Button save_device_button = (Button) findViewById(R.id.ownDeviceButton);
        save_device_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                group_text = findViewById(R.id.group_text);
                String device_text;
                boolean exist = true;

                /*TODO Check if device is already saved*/
                FileInputStream fis = null;
                try {
                    fis = openFileInput(Constants.DeviceFile);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr);
                    String text;
                    while ((text = br.readLine()) != null) {
                        String[] line = text.split(",");
                        if(line[0].equals(group_text.getText().toString())){
                            exist = false;
                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (fis != null) {
                        try {
                            fis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }


                if(exist) {
                    // Save device and groupname to file
                    FileOutputStream fos = null;
                    try {
                        fos = openFileOutput(Constants.DeviceFile, MODE_APPEND);
                        for (int i = 1; i < 5; i++) {
                            device_text = "Pin " + i;
                            fos.write(group_text.getText().toString().getBytes());
                            fos.write(",".getBytes());
                            fos.write(device_text.getBytes());
                            fos.write("\n".getBytes());
                        }

                        device_text = Constants.ControlDeviceSettings;  /*ControlDevice Settings is not printed on HomeMainActivity*/
                        fos.write(group_text.getText().toString().getBytes());
                        fos.write(",".getBytes());
                        fos.write(device_text.getBytes());
                        fos.write("\n".getBytes());

                        //TODO MAX name length!
                        String MSG = group_text.getText().toString();
                        String Topic = "control-device/settings";
                        try {
                            Constants.pahoMqttClient.publishMessage(Constants.client, MSG, (int) 1, Topic);
                        } catch (MqttException e) {
                            e.printStackTrace();
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(getApplicationContext(), "Device saved", Toast.LENGTH_SHORT).show();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (fos != null) {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    Intent intent = new Intent(AddControlDevice.this, HomeMainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Control Device already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });


        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.Home:
                        Intent menuIntent = new Intent(AddControlDevice.this, HomeMainActivity.class);
                        startActivity(menuIntent);
                        break;
                    case R.id.Notification:
                        Intent Notification = new Intent(AddControlDevice.this, NotificationMessages.class);
                        startActivity(Notification);
                        break;
                    case R.id.add_NEW_Device:
                        Intent add_NEW_Device = new Intent(AddControlDevice.this, DeviceChoice.class);
                        startActivity(add_NEW_Device);
                        break;
                }
                return false;
            }
        });



    }
}


