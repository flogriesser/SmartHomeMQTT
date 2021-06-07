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

        View messageView = inflater.inflate(R.layout.message_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(messageView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Message message = MessageList.get(position);
        TextView group_text = holder.group_text;
        group_text.setText(message.getGroup());
        TextView device_text = holder.device_text;
        device_text.setText(message.getDevice());
        TextView time_text = holder.time_text;
        time_text.setText(message.getTime());
        TextView message_text = holder.message_text;
        message_text.setText(message.getMessage());

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


