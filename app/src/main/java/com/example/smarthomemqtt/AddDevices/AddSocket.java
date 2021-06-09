package com.example.smarthomemqtt.AddDevices;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
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
import java.util.ArrayList;

public class AddSocket extends AppCompatActivity {

    public EditText group_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_socket);

        Button save_device_button = (Button) findViewById(R.id.ownDeviceButton);
        save_device_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                group_text = findViewById(R.id.group_text);

                //Switch if device is already saved (True = exist, False = not
                //boolean DeviceAvailable = true;
                ArrayList<String> Sockets = new ArrayList<String>();

                String Group = group_text.getText().toString();
                String Device = Constants.SocketName + "1";

                /*TODO Check if device is already saved*/
                FileInputStream fis = null;
                try {
                    fis = openFileInput(Constants.DeviceFile);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr);
                    String text;
                    while ((text = br.readLine()) != null) {
                        String[] line = text.split(",");
                        if (line[0].equals(Group) && line[1].startsWith("Socket")) {
                            Sockets.add(line[1]);
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

                //Only 3 Sockets added until now
                //FIXME Should be obsolete with spinner
                if(Sockets.size() < 4) {

                    for(int i = 0; i < Sockets.size(); i++){
                        if(Sockets.contains(Device)){
                            Device = "Socket" + (2 + i);
                        }
                    }

                    // Save device and groupname to file
                    FileOutputStream fos = null;
                    try {
                        fos = openFileOutput(Constants.DeviceFile, MODE_APPEND);
                        fos.write(Group.getBytes());
                        fos.write(",".getBytes());
                        fos.write(Device.getBytes());
                        fos.write("\n".getBytes());


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

                    Intent intent = new Intent(AddSocket.this, HomeMainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Device already exists", Toast.LENGTH_SHORT).show();
                }


            }
        });

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.Home:
                        Intent HomeIntent = new Intent(AddSocket.this, HomeMainActivity.class);
                        startActivity(HomeIntent);
                        break;
                    case R.id.Notification:
                        Intent Notification = new Intent(AddSocket.this, NotificationMessages.class);
                        startActivity(Notification);
                        break;
                    case R.id.add_NEW_Device:
                        Intent add_NEW_Device = new Intent(AddSocket.this, DeviceChoice.class);
                        startActivity(add_NEW_Device);
                        break;
                }
                return false;
            }
        });

    }
}


