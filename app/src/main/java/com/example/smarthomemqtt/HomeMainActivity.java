package com.example.smarthomemqtt;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.security.acl.Group;
import java.util.ArrayList;

public class HomeMainActivity extends AppCompatActivity {


    ArrayList<Item> ItemList, ShortItemList;
    ArrayList<String> GroupList;

    RecyclerView dashboard_rec_view;
    RecyclerView.LayoutManager layout_manager;
    RecyclerView.Adapter iadapter;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_main);

        dashboard_rec_view = (RecyclerView)findViewById(R.id.connectionStatus);
        spinner = (Spinner)findViewById(R.id.spinner);

        ItemList = new ArrayList<Item>();
        GroupList = new ArrayList<String>();

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

        ShortItemList = ItemList;
        dashboard_rec_view.setHasFixedSize(true);
        layout_manager = new LinearLayoutManager(this);
        iadapter = new ItemAdapter(this, ShortItemList);
        dashboard_rec_view.setLayoutManager(layout_manager);
        dashboard_rec_view.setAdapter(iadapter);


        ArrayAdapter<String> aadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, GroupList);
        aadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(aadapter);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ShortItemList = new ArrayList<Item>();
                for (Item i:ItemList) {
                    if(i.getGroup().equals(parent.getSelectedItem().toString())){
                        ShortItemList.add(i);
                    }
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
                Intent intent = new Intent(HomeMainActivity.this, AddDeviceActivity.class);
                startActivity(intent);
            }
        });
    }

}

