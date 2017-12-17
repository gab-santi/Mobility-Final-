package com.example.gab.mobility_final;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Presenter extends AppCompatActivity {

    Button bt_prev;
    Button bt_next;
    TextView status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presenter);
        final String ip = getIntent().getExtras().getString("ip");

        // init
        bt_prev = (Button) findViewById(R.id.bt_prev);
        bt_next = (Button) findViewById(R.id.bt_next);

        // init
        status = (TextView) findViewById(R.id.tv_status);

        // set connection status
        status.setText("Connected to " + ip);

        bt_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send(null, "prv", ip);
            }
        });

        bt_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send(null, "nxt", ip);
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
