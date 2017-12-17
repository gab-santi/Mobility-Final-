package com.example.gab.mobility_final;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Presenter extends AppCompatActivity {

    Button bt_prev;
    Button bt_next;
    TextView status;
    String ip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presenter);


        // init
        bt_prev = (Button) findViewById(R.id.bt_prev);
        bt_next = (Button) findViewById(R.id.bt_next);
        ip = getIntent().getExtras().getString("ip");

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        Intent myIntent = new Intent(getApplicationContext(), IPActivity.class);
        startActivityForResult(myIntent, 0);
        send(null, "diconnect", ip);
        return true;

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
