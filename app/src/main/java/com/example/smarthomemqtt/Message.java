package com.example.smarthomemqtt;

import androidx.annotation.NonNull;

public class Message {
    private String group_str, device_str, time_str, message_str;

    public Message(@NonNull final String group_str,
                   @NonNull final String device_str,
                   @NonNull final String time_str,
                   @NonNull final String message_str) {
        setGroup(group_str);
        setDevice(device_str);
        setTime(time_str);
        setMessage(message_str);
    }

    @NonNull
    public String getGroup() {
        return group_str;
    }
    @NonNull
    public String getDevice() {
        return device_str;
    }
    @NonNull
    public String getTime() {
        return time_str;
    }
    @NonNull
    public String getMessage() {
        return message_str;
    }

    public void setGroup(@NonNull final String group_str) {
        this.group_str = group_str;
    }
    public void setDevice(@NonNull final String device_str) {
        this.device_str = device_str;
    }
    public void setTime(@NonNull final String time_str) {
        this.time_str = time_str;
    }
    public void setMessage(@NonNull final String message_str) {
        this.message_str = message_str;
    }

    public String toString() {
        return group_str;
    }
}

