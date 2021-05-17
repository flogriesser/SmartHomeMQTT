package com.example.smarthomemqtt;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import static java.sql.DriverManager.println;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder>{

    //ArrayList<String> ItemList;
    ArrayList<Item> ItemList;

    public Button edit_item_button;
    private Context mContext;

    //public ItemAdapter(ArrayList<String> itemList) {
    public ItemAdapter(Context context, ArrayList<Item> itemList) {
        ItemList = itemList;
        mContext = context;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.temp_layout, parent, false);
        //return new ViewHolder(view);
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        //View itemView = inflater.inflate(R.layout.temp_layout, parent, false);
        View itemView = inflater.inflate(R.layout.dashboard_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //holder.group_text.setText(ItemList.get(position));
        Item item = ItemList.get(position);
        TextView textView = holder.group_text;
        textView.setText(item.getGroup());
        TextView textView2 = holder.device_text;
        textView2.setText(item.getDevice());
        println("Den kleine Position: " + position);

        // OnClickListener
        edit_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CommunicationActivity.class);
                intent.putExtra("Group", item.getGroup());
                intent.putExtra("Device", item.getDevice());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView group_text;
        public TextView device_text;

        public ViewHolder(View itemView) {
            super(itemView);
            //group_text = (TextView) itemView.findViewById(R.id.random_name);
            group_text = (TextView) itemView.findViewById(R.id.group_name);
            device_text = (TextView) itemView.findViewById(R.id.device_name);
            edit_item_button = (Button) itemView.findViewById(R.id.edit_item_button);
        }
    }
}


