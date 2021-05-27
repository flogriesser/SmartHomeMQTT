package com.example.smarthomemqtt.AddDevices;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.smarthomemqtt.Constants;
import com.example.smarthomemqtt.HomeMainActivity;
import com.example.smarthomemqtt.Item;
import com.example.smarthomemqtt.R;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class AddDeviceActivity extends AppCompatActivity {

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
                String Group = group_text.getText().toString();
                device_text = findViewById(R.id.device_text);
                String Device = device_text.getText().toString();
                boolean exist = true;
                Switch Subscribe = (Switch) findViewById(R.id.SubscribeButton);

                FileInputStream fis = null;
                try {
                    fis = openFileInput(Constants.FILENAME);
                    InputStreamReader isr = new InputStreamReader(fis);
                    BufferedReader br = new BufferedReader(isr);
                    String text;
                    while ((text = br.readLine()) != null) {
                        String[] line = text.split(",");
                        if(line[0].equals(Group) &&
                                line[1].equals(Device)){
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
                        fos = openFileOutput(Constants.FILENAME, MODE_APPEND);
                        fos.write(Group.getBytes());
                        fos.write(",".getBytes());
                        fos.write(Device.getBytes());
                        fos.write("\n".getBytes());

                        if(Subscribe.isChecked()) {
                            try {
                                Constants.pahoMqttClient.subscribe(Constants.client,Group + "/" + Device, (int) 1);
                                Toast.makeText(getApplicationContext(), "Device subscribed", Toast.LENGTH_SHORT).show();
                            } catch (MqttException e) {
                                e.printStackTrace();
                            }
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

                    Intent intent = new Intent(AddDeviceActivity.this, HomeMainActivity.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(getApplicationContext(), "Device already exists", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}


