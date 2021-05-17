package com.example.smarthomemqtt;

import org.eclipse.paho.android.service.MqttAndroidClient;

import java.util.Timer;

/**
 * Created by brijesh on 20/4/17.
 */

public class Constants {

    public static MqttAndroidClient client;
    public static PahoMqttClient pahoMqttClient;
    public static String clientid = "";
    public static Timer myTimer;
    public static boolean ConnectionStatus;
    public static boolean LoggedIn;

    public static final String FILENAME = "added_devices.txt";
    public static final String MQTT_BROKER_URL = "app_startup_placeholder";
    public static final String MQTT_CLIENT_UN = "app_startup_placeholder";
    public static final String MQTT_CLIENT_PW = "app_startup_placeholder";
    public static final String CLIENT_ID = "app_startup_placeholder";


    public static final String CONNECTED = "Connected";
    public static final String NOTCONNECTED = "Not Connected";
}

