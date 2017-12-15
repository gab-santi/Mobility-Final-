package com.example.gab.mobility_final;

import android.os.AsyncTask;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

/*
** IMPORTANT!!
** Make sure the AndroidManifest file has
** <uses-permission android:name="android.permission.INTERNET"></uses-permission>
** for this to work!
 */

public class MessageSender extends AsyncTask<String, Void, Void> {
    // Sockets and Streams
    Socket s;
    PrintWriter pw;

    // This recevies all parameters from the .execute() method in MainActivity
    @Override
    protected Void doInBackground(String... params) {
        // Get the message
        String message = params[0];
        String ip_address = params[1];

        try {
            // Connect to the socket. Make sure you know your IP!
            s = new Socket(ip_address, 8080);
            pw = new PrintWriter(s.getOutputStream());

            // The message is sent to the server via the socket
            pw.write(message);

            // Flush and close to avoid errors
            pw.flush();
            pw.close();
            s.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
