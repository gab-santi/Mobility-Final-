package com.example.gab.mobility_final;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.Buffer;

/*
** IMPORTANT!!
** Make sure the AndroidManifest file has
** <uses-permission android:name="android.permission.INTERNET"></uses-permission>
** for this to work!
 */

public class MessageSender extends AsyncTask<String, Void, Integer> {
    // Sockets and Streams
    Socket s, as;
    PrintWriter pw;

    // This receives all parameters from the .execute() method in MainActivity
    @Override
    protected Integer doInBackground(String... params) {
        // Get the message
        String message = params[0];
        String ip_address = params[1];

        try {
            // Connect to the socket. Make sure you know your IP!
            s = new Socket(ip_address, 8080);
            pw = new PrintWriter(s.getOutputStream());

            // The message is sent to the server via the socket
            pw.write(message);
            System.out.println("[MessageSender] Message " + message + " has been sent!");

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
