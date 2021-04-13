package com.example.smarthomemqtt;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class CommunicationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.communication);

        getIncomingIntent();

        EditText comm_message = (EditText) findViewById(R.id.comm_message);
        Button send_msg_button = (Button) findViewById(R.id.send_msg_button);
        send_msg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CommunicationActivity.this, "Message sent", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getIncomingIntent(){

        if(getIntent().hasExtra("Group") && getIntent().hasExtra("Device")){

            String group_text = getIntent().getStringExtra("Group");
            String device_text = getIntent().getStringExtra("Device");

            TextView group = (TextView) findViewById(R.id.comm_group);
            group.setText(group_text);
            TextView device = (TextView) findViewById(R.id.comm_device);
            device.setText(device_text);
        }
    }
}
