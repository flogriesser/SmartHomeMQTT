package com.example.smarthomemqtt;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.eclipse.paho.client.mqttv3.MqttException;

import java.io.UnsupportedEncodingException;

public class CommunicationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.communication);

        //getIncomingIntent();
        String Group = getGroupName();
        String Device = getDeviceName();



        EditText comm_message = (EditText) findViewById(R.id.comm_message);
        Button send_msg_button = (Button) findViewById(R.id.send_msg_button);
        send_msg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*TODO Publish Topic here*/
                String MSG = comm_message.getText().toString().trim();;
                String Topic = Group + "/" + Device;
                try {
                    Constants.pahoMqttClient.publishMessage(Constants.client, MSG, (int)1, Topic);
                    Toast.makeText(CommunicationActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
                } catch (MqttException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                Toast.makeText(CommunicationActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
            }
        });
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
