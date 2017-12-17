package com.example.gab.mobility_final;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    // widgets
    TextView status;
    Button btnLeftClick;
    Button btnRightClick;
    TextView mouseArea;
    String ip;


    float initx = 0;
    float inity = 0;
    float disx = 0;
    float disy = 0;
    boolean mouseMoved = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // getSupportActionBar().setTitle("Mobility (Beta)");

        // initialize widgets
        status = (TextView) findViewById(R.id.tv_status);
        btnLeftClick = (Button) findViewById(R.id.leftClick);
        btnRightClick = (Button) findViewById(R.id.rightClick);
        mouseArea = (TextView) findViewById(R.id.mouseArea);
        ip = getIntent().getExtras().getString("ip");

        send(null, "Phone connect", ip);

        // set connection status
        status.setText("Connected to " + ip);

        // left click
        btnLeftClick.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                send(v, "ml", ip);
            }
        });

        btnRightClick.setOnClickListener(new View.OnClickListener(){
           @Override
            public void onClick(View v) {
               send(v, "mr", ip);
           }
        });

        mouseArea.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch(event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initx = event.getX();
                        inity = event.getY();
                        mouseMoved = false;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        disx = event.getX() - initx;
                        disy = event.getY() - inity;

                        initx = event.getX();
                        inity = event.getY();
                        if (disx != 0 || disy != 0) {
                            String dis = disx + "," + disy;
                            System.out.println(disx + "," + disy);
                            send(null, dis, ip);
                        }
                        mouseMoved = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        if (!mouseMoved)
                            send(null, "l", ip);
                 }

                return true;
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
        Intent myIntent = new Intent(getApplicationContext(), SelectActivity.class);
        startActivityForResult(myIntent, 0);
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
