package com.example.smarthomemqtt;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.smarthomemqtt.AddDevices.DeviceChoice;
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

public class CommunicationActivity extends AppCompatActivity implements View.OnClickListener{


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.communication);

        Button send_msg_button = (Button) findViewById(R.id.send_msg_button);
        Button delete_device_button = (Button) findViewById(R.id.delete_device_button);
        send_msg_button.setOnClickListener(this);
        delete_device_button.setOnClickListener(this);



        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()) {
                    case R.id.add_NEW_Device:
                        Intent menuIntent = new Intent(CommunicationActivity.this, DeviceChoice.class);
                        startActivity(menuIntent);
                        break;
                    case R.id.Notification:
                        Intent Notification = new Intent(CommunicationActivity.this, NotificationMessages.class);
                        startActivity(Notification);
                        break;
                    case R.id.Home:
                        Intent Home = new Intent(CommunicationActivity.this, HomeMainActivity.class);
                        startActivity(Home);
                        break;
                }
                return false;
            }
        });
    }
    @Override
    public void onClick(View v) {
        String Group = getGroupName();
        String Device = getDeviceName();

        if(v.getId() == R.id.send_msg_button) {
            EditText comm_message = (EditText) findViewById(R.id.comm_message);

            /*TODO Publish Topic here*/
            String MSG = comm_message.getText().toString().trim();
            String Topic = Group + "/" + Device;
            try {
                Constants.pahoMqttClient.publishMessage(Constants.client, MSG, (int) 1, Topic);
                Toast.makeText(CommunicationActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
            } catch (MqttException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //Toast.makeText(CommunicationActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
        }
        else if(v.getId() == R.id.delete_device_button){
            //delete item and write new data to file
            ArrayList<Item> TempList = new ArrayList<Item>();
            FileInputStream fis = null;
            try {
                fis = openFileInput(Constants.FILENAME);
                InputStreamReader isr = new InputStreamReader(fis);
                BufferedReader br = new BufferedReader(isr);
                String text;
                while ((text = br.readLine()) != null) {
                    String[] line = text.split(",");
                    if(line[0].equals(Group) && line[1].equals(Device)){
                        continue;
                    }
                    else if(line.length == 2){
                        TempList.add(new Item(line[0], line[1]));
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
            FileOutputStream fos = null;
            try {
                fos = openFileOutput(Constants.FILENAME, MODE_PRIVATE);
                for(Item i: TempList){
                    fos.write(i.getGroup().getBytes());
                    fos.write(",".getBytes());
                    fos.write(i.getDevice().getBytes());
                    fos.write("\n".getBytes());
                }
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
            Intent intent = new Intent(CommunicationActivity.this, HomeMainActivity.class);
            startActivity(intent);
        }
    }


    private String getGroupName(){
        String group_text = "";
        if(getIntent().hasExtra("Group") && getIntent().hasExtra("Device")){

            group_text = getIntent().getStringExtra("Group");

            TextView group = (TextView) findViewById(R.id.comm_group);
            group.setText(group_text);
            return group_text;
        }
        return group_text;
    }

    private String getDeviceName(){
        String device_text = "";
        if(getIntent().hasExtra("Device")){

            device_text = getIntent().getStringExtra("Device");

            TextView device = (TextView) findViewById(R.id.comm_device);
            device.setText(device_text);
            return device_text;
        }
        return device_text;
    }
}
