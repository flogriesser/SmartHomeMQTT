package com.example.smarthomemqtt;

import androidx.annotation.NonNull;

public class Item {
    private String group_str, device_str;

    public Item(@NonNull final String group_str, @NonNull final String device_str) {
        setGroup(group_str);
        setDevice(device_str);
    }

    @NonNull
    public String getGroup() {
        return group_str;
    }
    @NonNull
    public String getDevice() {
        return device_str;
    }

    public void setGroup(@NonNull final String group_str) {
        this.group_str = group_str;
    }
    public void setDevice(@NonNull final String device_str) {
        this.device_str = device_str;
    }

    public String toString() {
        return group_str;
    }
}

