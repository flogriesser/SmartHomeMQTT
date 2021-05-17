package com.example.smarthomemqtt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class AddDeviceActivity extends AppCompatActivity {

    public EditText group_text, device_text;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_device);

        Button save_device_button = (Button) findViewById(R.id.save_device_button);
        save_device_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                group_text = findViewById(R.id.group_text);
                device_text = findViewById(R.id.device_text);


                // Save device and groupname to file
                FileOutputStream fos = null;
                try {
                    fos = openFileOutput(Constants.FILENAME,  MODE_APPEND);
                    fos.write(group_text.getText().toString().getBytes());
                    fos.write(",".getBytes());
                    fos.write(device_text.getText().toString().getBytes());
                    fos.write("\n".getBytes());
                    Toast.makeText(getApplicationContext(),"Device saved",Toast.LENGTH_SHORT).show();
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
            }
        });
    }
}


