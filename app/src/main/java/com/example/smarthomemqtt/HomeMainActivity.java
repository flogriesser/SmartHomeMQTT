package com.example.smarthomemqtt;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class HomeMainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1995;

    //ArrayList<String> ItemList;
    ArrayList<Item> ItemList;

    RecyclerView dashboard_rec_view;
    RecyclerView.LayoutManager layout_manager;
    RecyclerView.Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        dashboard_rec_view = (RecyclerView)findViewById(R.id.connectionStatus);
        /*
        ItemList = new ArrayList<String>();
        ItemList.add("Device 1");
        ItemList.add("Device 2");
        ItemList.add("Device 3");
        */
        ItemList = new ArrayList<Item>();
        //ItemList.add(new Item("RasperryPi", "LED1"));
        //ItemList.add(new Item("RasperryPi", "LED2"));


        dashboard_rec_view.setHasFixedSize(true);
        layout_manager = new LinearLayoutManager(this);
        adapter = new ItemAdapter(this, ItemList);
        dashboard_rec_view.setLayoutManager(layout_manager);
        dashboard_rec_view.setAdapter(adapter);

        Button add_device_button = (Button) findViewById(R.id.add_device_button);
        add_device_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMainActivity.this, AddDeviceActivity.class);
                //startActivity(intent);
                startActivityForResult(intent, REQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_CODE:
                if (resultCode == Activity.RESULT_OK){
                    String group_str = data.getStringExtra("Group");
                    String device_str = data.getStringExtra("Device");

                    ItemList.add(new Item(group_str, device_str));
                    //ItemList.add(group_str);
                    dashboard_rec_view.setHasFixedSize(true);
                    layout_manager = new LinearLayoutManager(this);
                    adapter = new ItemAdapter(this, ItemList);
                    dashboard_rec_view.setLayoutManager(layout_manager);
                    dashboard_rec_view.setAdapter(adapter);

                    Toast.makeText(this, group_str, Toast.LENGTH_SHORT).show();
                }
        }
    }
}

