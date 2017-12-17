package com.example.gab.mobility_final;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.TextView;

public class SelectActivity extends AppCompatActivity {

    // Initialize buttons
    Button bt_disconnect;
    ImageButton bt_trackpad;
    ImageButton bt_keyboard;
    ImageButton bt_presenter;
    TextView tv_ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(_pay_box_helper.getWindowToken(), 0);

        //setTheme(R.style.DarkTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        final String ip = getIntent().getExtras().getString("ip");

        bt_disconnect = (Button) findViewById(R.id.bt_disconnect);
        bt_trackpad = (ImageButton) findViewById(R.id.bt_trackpad);
        bt_keyboard = (ImageButton) findViewById(R.id.bt_keyboard);
        bt_presenter = (ImageButton) findViewById(R.id.bt_presenter);
        tv_ip = (TextView) findViewById(R.id.tv_ipAddress);

        tv_ip.setText(ip);

        bt_disconnect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(getBaseContext(), IPActivity.class);
                // Send a disconnect message to the desktop app
                send(null, "disconnect", ip);
                startActivity(i);
            }
        });

        bt_trackpad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(getBaseContext(), MainActivity.class);
                i.putExtra("ip", ip);
                startActivity(i);
            }
        });

        bt_keyboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(getBaseContext(), Keyboard.class);
                i.putExtra("ip", ip);
                startActivity(i);
            }
        });

        bt_presenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.setClass(getBaseContext(), Presenter.class);
                i.putExtra("ip", ip);
                startActivity(i);
            }
        });
    }

    public void send(View v, String message, String ip_address) {
        // Get the string message
        System.out.println("Message sent: \"" + message + "\" to IP Address [" + ip_address + "]");

        // Send the message using the MessageSender AsyncTask class
        // the parameters of .execute() is passed to the doInBackground() method in MessageSender
        // message is parameter 0, ip_address is now parameter 1
        MessageSender messageSender = new MessageSender();
        messageSender.execute(message, ip_address);
    }
}
