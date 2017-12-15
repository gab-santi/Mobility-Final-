import java.io.BufferedReader;
import java.io.IOException;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.awt.MouseInfo;
import java.awt.Point;
import java.net.InetAddress;

public class Main {
    // Sockets
    static Socket s;
    static ServerSocket ss;
    static InputStreamReader isr;
    static BufferedReader br;
    static String message;
    static Robot bot;

    float initx = 0;
    float inity = 0;
    float disx = 0;
    float disy = 0;

    public static void main(String[] args) {

        System.out.println("Starting the server...");
        try {
            // Establish the socket connection and listen for incoming
            ss = new ServerSocket(8080);
            System.out.println("Server has started! Accepting connections.");

            // Make the window
            JFrame frame = new JFrame("Mobility Connect");
            frame.setSize(300, 100);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

            JLabel serverStatus = new JLabel("Connect your phone to " + InetAddress.getLocalHost().getHostAddress());
            frame.add(serverStatus);

            frame.setVisible(true);    
            // If the socket was started
            while (true) {
                // Accept a packet
                s = ss.accept();
                if (s != null) {
                    System.out.println("Packet received!");
                }

                // Read the line
                isr = new InputStreamReader(s.getInputStream());
                br = new BufferedReader(isr);
                message = br.readLine();

                // Print the message
                System.out.println("Message recevied: " + message);

                // parse message
                messageEvent(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // parses the message to see if there is an event
    public static void messageEvent(String message) {
        try {
            bot = new Robot();
            if (message.equals("ml")) { // left mouse click
                bot.mousePress(InputEvent.BUTTON1_MASK);
                bot.mouseRelease(InputEvent.BUTTON1_MASK);
                System.out.println("LEFT CLICK");
            } else if (message.equals("mr")) { // right mouse click
                bot.mousePress(InputEvent.BUTTON3_MASK);
                bot.mouseRelease(InputEvent.BUTTON3_MASK);
                System.out.println("RIGHT CLICK");
            } else if (message.contains(",")) { // trackpad gesture
                // parse message
                String[] values = message.split("[,]+");
                System.out.println("Values: " + values[0] + ", " + values[1]);
                float disx = Float.valueOf(values[0]);
                float disy = Float.valueOf(values[1]);

                //move mouse
                Point mouseLocation = MouseInfo.getPointerInfo().getLocation();
                bot.mouseMove((int)(mouseLocation.getX()+disx), (int)(mouseLocation.getY()+disy));
            } else if (message.equals("prv")) { // presenter - previous
                bot.keyPress(KeyEvent.VK_LEFT);
                bot.keyRelease(KeyEvent.VK_LEFT);
            } else if (message.equals("nxt")) { // presenter - next
                bot.keyPress(KeyEvent.VK_RIGHT);
                bot.keyRelease(KeyEvent.VK_RIGHT);
            } else {
                char c = message.charAt(0);
                if (Character.isUpperCase(c)) {
                    bot.keyPress(KeyEvent.VK_SHIFT);
                }
                bot.keyPress(Character.toUpperCase(c));
                bot.keyRelease(Character.toUpperCase(c));

                if (Character.isUpperCase(c)) {
                    bot.keyRelease(KeyEvent.VK_SHIFT);
                }
            } 
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
