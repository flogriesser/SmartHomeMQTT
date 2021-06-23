package com.example.smarthomemqtt;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    public ArrayList<Item> ItemList;

    public Button edit_item_button;
    public Context mContext;


    public ItemAdapter(Context context, ArrayList<Item> itemList) {
        ItemList = itemList;
        mContext = context;
    }

    @Override
    public ItemAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.dashboard_item, parent, false);
        return new ViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Item item = ItemList.get(position);
        TextView textView = holder.group_text;
        textView.setText(item.getGroup());
        TextView textView2 = holder.device_text;
        textView2.setText(item.getDevice());
        holder.setIsRecyclable(false);

        // OnClickListener
        edit_item_button.setOnClickListener(view -> {
            Intent intent = new Intent(mContext, CommunicationActivity.class);
            intent.putExtra("Group", item.getGroup());
            intent.putExtra("Device", item.getDevice());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return ItemList.size();
    }

    public void updateItemList(ArrayList<Item> newList){
        this.ItemList = newList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView group_text;
        public TextView device_text;

        public ViewHolder(View itemView) {
            super(itemView);
            group_text =  itemView.findViewById(R.id.group_name);
            device_text =  itemView.findViewById(R.id.device_name);
            edit_item_button =  itemView.findViewById(R.id.edit_item_button);
        }
    }
}


