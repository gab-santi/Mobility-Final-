package com.example.gab.mobility_final;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class IPActivity extends AppCompatActivity {
    // widgets
    EditText et_ip;
    Button btn_ok;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ip);

        // Instantiate widgets
        et_ip = (EditText) findViewById(R.id.et_ip);
        btn_ok = (Button) findViewById(R.id.btn_ok);

        // click ok, pass IP address as extra, move to MainActivity
        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validIP(et_ip.getText().toString())) {
                    AlertDialog alertDialog = new AlertDialog.Builder(IPActivity.this).create();
                    alertDialog.setTitle("Oops");
                    alertDialog.setMessage("Enter your IP address to begin.");
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                } else {
                    Intent i = new Intent();
                    i.setClass(getBaseContext(), SelectActivity.class);
                    i.putExtra("ip", et_ip.getText().toString());

                    // Send both the connect message and the IP Address as a comma separated value
                    send(null, "connect", et_ip.getText().toString());
                    startActivity(i);
                }

            }
        });
    }

    public static boolean validIP (String ip) {
        try {
            if ( ip == null || ip.isEmpty() ) {
                return false;
            }

            String[] parts = ip.split( "\\." );
            if ( parts.length != 4 ) {
                return false;
            }

            for ( String s : parts ) {
                int i = Integer.parseInt( s );
                if ( (i < 0) || (i > 255) ) {
                    return false;
                }
            }
            if ( ip.endsWith(".") ) {
                return false;
            }

            return true;
        } catch (NumberFormatException nfe) {
            return false;
        }
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
