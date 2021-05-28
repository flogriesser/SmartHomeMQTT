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

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class AddSocket extends AppCompatActivity {

    public EditText group_text, device_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_device);

        Button save_device_button = (Button) findViewById(R.id.ownDeviceButton);
        save_device_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                group_text = findViewById(R.id.group_text);
                device_text = findViewById(R.id.device_text);;

                String Group = group_text.getText().toString();
                String Device = device_text.getText().toString();

                /*TODO Check if device is already saved*/

                FileOutputStream fos = null;

                EditText comm_message = (EditText) findViewById(R.id.comm_message);

                /*TODO Publish Topic here*/
                String MSG = "Test";
                String Topic = Group + "/" + Device;
                try {
                    Constants.pahoMqttClient.publishMessage(Constants.client, MSG, (int) 1, Topic);
                    Toast.makeText(AddSocket.this, "Message sent", Toast.LENGTH_SHORT).show();
                } catch (MqttException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                //Toast.makeText(AddSocket.this, "Message sent", Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(AddSocket.this, HomeMainActivity.class);
                startActivity(intent);
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


