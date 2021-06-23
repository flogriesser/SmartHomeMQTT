package com.example.smarthomemqtt;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;





public class MainActivity extends AppCompatActivity {


    EditText broker_text, username_text, password_text;
    Button login_button;
    TextView login_error_text;
    CheckBox remember_checkbox;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private void loginButtonListener(){
        String urlBroker    = broker_text.getText().toString().trim();
        String username     = username_text.getText().toString().trim();
        String password     = password_text.getText().toString().trim();


        //Generate unique client id for MQTT broker connection
        //Random r = new Random();
        //int i1 = r.nextInt(5000 - 1) + 1;
        Constants.clientid = "mqtt" + username;


        // TODO: scrollable > login site (text )
        if(Constants.pahoMqttClient.mqttAndroidClient.isConnected() ) {
            //Disconnect and Reconnect to  Broker
            try {
                //Disconnect from Broker
                Constants.pahoMqttClient.disconnect(Constants.client);
                //Connect to Broker
                Constants.client = Constants.pahoMqttClient.getMqttClient(getApplicationContext(), urlBroker, Constants.clientid, username, password);
                //Set Mqtt Message Callback
                mqttCallback();
            }
            catch (MqttException e) {
                e.printStackTrace();
            }
        }
        else {
            //Connect to Broker
            Constants.client = Constants.pahoMqttClient.getMqttClient(getApplicationContext(), urlBroker, Constants.clientid, username, password);
            //Set Mqtt Message Callback
            mqttCallback();
        }

        if(remember_checkbox.isChecked()){
            editor.putString(getString(R.string.checkbox), "True");
            editor.apply();
            String broker = broker_text.getText().toString();
            editor.putString(getString(R.string.broker_url), broker);
            editor.commit();
            String username_str = username_text.getText().toString();
            editor.putString(getString(R.string.username), username_str);
            editor.commit();
            String password_str = password_text.getText().toString();
            editor.putString(getString(R.string.password), password_str);
            editor.commit();
        }
        else{
            editor.putString(getString(R.string.checkbox), "False");
            editor.commit();
            editor.putString(getString(R.string.username), "");
            editor.commit();
            editor.putString(getString(R.string.password), "");
            editor.commit();
        }

        /*TODO This is maybe obsolete. The server needs to much time to set the state to connected*/
        // Login without Mqtt connection for developer
        if(Constants.pahoMqttClient.mqttAndroidClient.isConnected() || (username.equals("pi") && password.equals("pi"))){
            Intent intent = new Intent(MainActivity.this, HomeMainActivity.class);
            startActivity(intent);

        }
        /*
        else{
            login_error_text.setText("Wrong Credentials");
        }
        */

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Constants.ConnectionStatus = false;
        Constants.LoggedIn = false;

        //Generate unique client id for MQTT broker connection
        Random r = new Random();
        int i1 = r.nextInt(5000 - 1) + 1;
        Constants.clientid = "mqtt" + i1;

        //Add the Notification Channel
        createNotificationChannel();

        broker_text = findViewById(R.id.loginBrokerUrl);
        username_text = findViewById(R.id.username);
        password_text = findViewById(R.id.password);
        login_button = findViewById(R.id.login_button);
        login_error_text =  findViewById(R.id.login_error_text);
        remember_checkbox =  findViewById(R.id.remember_checkbox);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        String urlBroker    = broker_text.getText().toString().trim();
        String username     = username_text.getText().toString().trim();
        String password     = password_text.getText().toString().trim();

        Constants.pahoMqttClient = new PahoMqttClient();
        Constants.client = Constants.pahoMqttClient.getMqttClient(  getApplicationContext(),                        // Connect to MQTT Broker
                urlBroker,
                Constants.clientid,
                username,
                password
        );
        //Register Bottom Navigation Callback

        if(Constants.pahoMqttClient.mqttAndroidClient.isConnected()){
            Constants.ConnectionStatus = true;
        }

        checkSharedPreferences();

        //Create listener for MQTT messages.
        mqttCallback();

        login_button.setOnClickListener(v -> loginButtonListener());




        //Create Timer to report MQTT connection status every 1 second
        Constants.myTimer = new Timer();
        Constants.myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                ScheduleTasks();
            }

        }, 0, 1000);
    }


    private void checkSharedPreferences(){
        String checkbox_str = preferences.getString(getString(R.string.checkbox), "false");
        String broker_url = preferences.getString(getString(R.string.broker_url), "");
        String username_str = preferences.getString(getString(R.string.username), "");
        String password_str = preferences.getString(getString(R.string.password), "");

        broker_text.setText(broker_url);
        username_text.setText(username_str);
        password_text.setText(password_str);

        remember_checkbox.setChecked(checkbox_str.equals("True"));
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(Constants.ChannelID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    private void ScheduleTasks(){
        //This method is called directly by the timer
        //and runs in the same thread as the timer.

        //We call the method that will work with the UI
        //through the runOnUiThread method.
        this.runOnUiThread(RunScheduledTasks);
    }


    private Runnable RunScheduledTasks = () -> {
        //This method runs in the same thread as the UI.
        //Check MQTT Connection Status

        if(Constants.pahoMqttClient.mqttAndroidClient.isConnected() ) {
            setTitle("Connected");
            if(!Constants.LoggedIn){
                Constants.LoggedIn = true;
            Intent intent = new Intent(MainActivity.this, HomeMainActivity.class);
            startActivity(intent);
            }

            //Constants.pahoMqttClient.publishMessage(Constants.client, testTopic, 1, testTopic);
        }else{
            setTitle("Disconnected");
        }
    };


    // Called when a subscribed message is received
    protected void mqttCallback() {
        Constants.client.setCallback(new MqttCallback() {
            @Override
            public void connectionLost(Throwable cause) {
                //msg("Connection lost...");
            }

            @Override
            public void messageArrived(String topic, MqttMessage message) throws Exception {


                if(topic.equals("control-device/settings")){
                    //TODO Add custom setting handling here
                }
                else{
                    //TODO
                    String[] Topic = topic.split("/");
                    String Group = "";
                    String Device = "";

                    if (true) {
                        Group = Topic[0];
                        Device = Topic[1];
                    } else{
                        Group = "";
                        Device = topic;
                    }



                    String Time = Calendar.getInstance().getTime().toString().split("GMT")[0];


                    FileOutputStream fos = null;
                    try {
                        fos = openFileOutput(Constants.MessageFile, MODE_APPEND);
                        fos.write(Group.getBytes());
                        fos.write(",".getBytes());
                        fos.write(Device.getBytes());
                        fos.write(",".getBytes());
                        fos.write(Time.getBytes());
                        fos.write(",".getBytes());
                        fos.write(message.toString().getBytes());
                        fos.write("\n".getBytes());
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        if (fos != null) {
                            try {
                                fos.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    Toast.makeText(getApplicationContext(), "Message arrived", Toast.LENGTH_SHORT).show();

                    // Create an explicit intent for an Activity in your app
                    Intent intent = new Intent(MainActivity.this, NotificationMessages.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, intent, 0);

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this, Constants.ChannelID)
                            .setSmallIcon(R.drawable.homeicon)
                            .setContentTitle(topic)
                            .setContentText(message.toString())
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            // Set the intent that will fire when the user taps the notification
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);
                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(MainActivity.this);

                    // notificationId is a unique int for each notification that you must define
                    notificationManager.notify(Constants.NotificationCounter, builder.build());
                    if( Constants.NotificationCounter < 10) {
                        Constants.NotificationCounter++;
                    }else{
                        Constants.NotificationCounter = 0;
                    }
                }

                //Toast.makeText(getApplicationContext(), "Message arrived", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void deliveryComplete(IMqttDeliveryToken token) {

            }
        });
    }
}
