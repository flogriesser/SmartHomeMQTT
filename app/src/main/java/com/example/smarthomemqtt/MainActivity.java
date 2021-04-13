package com.example.smarthomemqtt;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    EditText username_text, password_text;
    Button login_button;
    TextView login_error_text;
    CheckBox remember_checkbox;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username_text = (EditText)findViewById(R.id.username);
        password_text = (EditText)findViewById(R.id.password);
        login_button = (Button) findViewById(R.id.login_button);
        login_error_text = (TextView) findViewById(R.id.login_error_text);
        remember_checkbox = (CheckBox) findViewById(R.id.remember_checkbox);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        checkSharedPreferences();

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(username_text.getText().toString().equals("admin") && password_text.getText().toString().equals("admin")) {
                    if(remember_checkbox.isChecked()){
                        editor.putString(getString(R.string.checkbox), "True");
                        editor.apply();
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

                    Intent intent = new Intent(MainActivity.this, HomeMainActivity.class);
                    startActivity(intent);
                }
                else{
                    login_error_text.setText("Wrong Credentials");
                }
            }
        });
    }
    private void checkSharedPreferences(){
        String checkbox_str = preferences.getString(getString(R.string.checkbox), "false");
        String username_str = preferences.getString(getString(R.string.username), "");
        String password_str = preferences.getString(getString(R.string.password), "");

        username_text.setText(username_str);
        password_text.setText(password_str);

        if(checkbox_str.equals("True")){
            remember_checkbox.setChecked(true);
        }
        else{
            remember_checkbox.setChecked(false);
        }
    }
}
