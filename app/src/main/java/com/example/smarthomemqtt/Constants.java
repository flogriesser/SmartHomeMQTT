package com.example.smarthomemqtt;

import org.eclipse.paho.android.service.MqttAndroidClient;

import java.util.Timer;


public class Constants {

    public static MqttAndroidClient client;
    public static PahoMqttClient pahoMqttClient;
    public static String clientid = "";
    public static Timer myTimer;
    public static boolean ConnectionStatus;
    public static boolean LoggedIn;

    public static int NotificationCounter = 0;


    public static final String ChannelID = "1";

    public static final String DeviceFile = "added_devices.txt";
    public static final String MessageFile = "added_devices.txt";

    public static final String ControlDeviceSettings = "ControlDeviceSettings";

    public static final String SocketName = "Socket";
}

