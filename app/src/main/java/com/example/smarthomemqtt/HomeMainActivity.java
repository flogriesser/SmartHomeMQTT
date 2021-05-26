package com.example.smarthomemqtt;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class HomeMainActivity extends AppCompatActivity {


    ArrayList<Item> ItemList, UpdatedItemList;
    ArrayList<String> GroupList;

    RecyclerView dashboard_rec_view;
    RecyclerView.LayoutManager layout_manager;
    ItemAdapter RecyclerViewAdapter;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        dashboard_rec_view = (RecyclerView)findViewById(R.id.connectionStatus);
        spinner = (Spinner)findViewById(R.id.spinner);

        ItemList = new ArrayList<Item>();
        GroupList = new ArrayList<String>();
        GroupList.add("All");


        // fill ItemList with data from file
        FileInputStream fis = null;
        try {
            fis = openFileInput(Constants.FILENAME);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr);
            String text;
            while ((text = br.readLine()) != null) {
                String[] line = text.split(",");
                if(line.length == 2){
                    ItemList.add(new Item(line[0], line[1]));
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
        RecyclerViewAdapter = new ItemAdapter(this, ItemList);
        dashboard_rec_view.setHasFixedSize(true);
        layout_manager = new LinearLayoutManager(this);
        dashboard_rec_view.setLayoutManager(layout_manager);
        dashboard_rec_view.setAdapter(RecyclerViewAdapter);
        dashboard_rec_view.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


        ArrayAdapter<String> StringArrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, GroupList);
        StringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(StringArrayAdapter);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                UpdatedItemList = new ArrayList<Item>();
                if (parent.getSelectedItem().toString().equals("All")) {
                    RecyclerViewAdapter.updateItemList(ItemList);
                } else {
                    for (Item i : ItemList) {
                        if (i.getGroup().equals(parent.getSelectedItem().toString())) {
                            UpdatedItemList.add(i);
                        }
                    }
                    RecyclerViewAdapter.updateItemList(UpdatedItemList);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        Button add_device_button = (Button) findViewById(R.id.add_device_button);
        add_device_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(HomeMainActivity.this, AddDeviceActivity.class);
                Intent intent = new Intent(HomeMainActivity.this, DeviceChoice.class);
                startActivity(intent);
            }
        });
    }

}

