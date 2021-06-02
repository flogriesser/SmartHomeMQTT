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

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.ViewHolder> {

    public ArrayList<Message> MessageList;

    public Context mContext;


    public MessageAdapter(Context context, ArrayList<Message> itemList) {
        MessageList = itemList;
        mContext = context;
    }

    @Override
    public MessageAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View itemView = inflater.inflate(R.layout.message_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = MessageList.get(position);
        TextView textView = holder.group_text;
        textView.setText(message.getGroup());
        TextView textView2 = holder.device_text;
        textView2.setText(message.getDevice());
        TextView textView3 = holder.time_text;
        textView3.setText(message.getTime());
        TextView textView4 = holder.message_text;
        textView4.setText(message.getMessage());

    }

    @Override
    public int getItemCount() {
        return MessageList.size();
    }

    public void updateItemList(ArrayList<Message> newList){
        this.MessageList = newList;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView group_text;
        public TextView device_text;
        public TextView time_text;
        public TextView message_text;

        public ViewHolder(View itemView) {
            super(itemView);
            group_text = (TextView) itemView.findViewById(R.id.group_name);
            device_text = (TextView) itemView.findViewById(R.id.device_name);
            time_text = (TextView) itemView.findViewById(R.id.time);
            message_text = (TextView) itemView.findViewById(R.id.message);
        }
    }
}


