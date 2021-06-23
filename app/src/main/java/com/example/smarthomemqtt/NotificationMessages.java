package com.example.smarthomemqtt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.smarthomemqtt.AddDevices.DeviceChoice;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class NotificationMessages extends AppCompatActivity {

    ArrayList<Message> MessageList, UpdatedMessageList;
    ArrayList<String> GroupList;

    RecyclerView notification_rec_view;
    RecyclerView.LayoutManager layout_manager;
    MessageAdapter RecyclerViewAdapter;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification);

        notification_rec_view = findViewById(R.id.connectionStatus);
        spinner = findViewById(R.id.spinner);

        MessageList = new ArrayList<>();
        GroupList = new ArrayList<>();
        GroupList.add("All");


        // fill MessageList with data from file
        FileInputStream fis = null;
        try {
            fis = openFileInput(Constants.MessageFile);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;
            while ((text = br.readLine()) != null) {
                String[] line = text.split(",");
                if(line.length == 4){
                    MessageList.add(new Message(line[0], line[1], line[2], line[3]));
                    if(!(GroupList.contains(line[0]))){
                        GroupList.add(line[0]);
                    }
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
        RecyclerViewAdapter = new MessageAdapter(this, MessageList);
        notification_rec_view.setHasFixedSize(true);
        layout_manager = new LinearLayoutManager(this);
        notification_rec_view.setLayoutManager(layout_manager);
        notification_rec_view.setAdapter(RecyclerViewAdapter);
        notification_rec_view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        ArrayAdapter<String> StringArrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, GroupList);
        StringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(StringArrayAdapter);


        Spinner spinner = findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UpdatedMessageList = new ArrayList<>();
                if (parent.getSelectedItem().toString().equals("All")) {
                    RecyclerViewAdapter.updateItemList(MessageList);
                } else {
                    for (Message i : MessageList) {
                        if (i.getGroup().equals(parent.getSelectedItem().toString())) {
                            UpdatedMessageList.add(i);
                        }
                    }
                    RecyclerViewAdapter.updateItemList(UpdatedMessageList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });





        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {
                case R.id.add_NEW_Device:
                    Intent menuIntent = new Intent(NotificationMessages.this, DeviceChoice.class);
                    startActivity(menuIntent);
                    break;
                case R.id.Home:
                    Intent Home = new Intent(NotificationMessages.this, HomeMainActivity.class);
                    startActivity(Home);
                    break;
            }
            return false;
        });
        
        
        
    }

}

