package com.example.gab.mobility_final;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Keyboard extends AppCompatActivity {

    TextView status;
    EditText et_input;
    String ip;
    Button bt_bckspc;
    Button bt_enter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);

        // init
        status = (TextView) findViewById(R.id.tv_status);
        et_input = (EditText) findViewById(R.id.et_input);
        ip = getIntent().getExtras().getString("ip");
        bt_bckspc = (Button) findViewById(R.id.btn_bspc);
        bt_enter = (Button) findViewById(R.id.btn_enter);


        // set connection status
        status.setText("Connected to " + ip);

        et_input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String s = charSequence.toString();

                if (s == null)
                    send(null, "bck", ip);
                else if (!s.isEmpty())
                    send(null, s, ip);


            }

            @Override
            public void afterTextChanged(Editable editable) {

                et_input.getText().clear();

            }
        });
        // show keyboard

        InputMethodManager imm = ((InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE));
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);

        bt_bckspc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send(null, "bsp", ip);
            }
        });

        bt_enter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                send(null, "ent", ip);
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
        send(null, "disconnect", ip);
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
