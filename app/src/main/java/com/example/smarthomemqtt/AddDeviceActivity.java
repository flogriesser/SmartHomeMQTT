package com.example.smarthomemqtt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class AddDeviceActivity extends AppCompatActivity {
    public TextView group_text, device_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_device);

        Button save_device_button = (Button) findViewById(R.id.save_device_button);
        save_device_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                group_text = (TextView)findViewById(R.id.group_text);
                device_text = (TextView)findViewById(R.id.device_text);

                //////
                Intent intent = new Intent();//AddDeviceActivity.this, HomeMainActivity.class);
                intent.putExtra("Group", group_text.getText().toString());
                intent.putExtra("Device", device_text.getText().toString());
                setResult(Activity.RESULT_OK, intent);
                Toast.makeText(getApplicationContext(),"Device saved",Toast.LENGTH_SHORT).show();
            }
        });
    }
}


