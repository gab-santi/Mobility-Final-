import java.awt.*;
import java.io.*;
import java.awt.event.KeyEvent;
import javax.swing.*;
import java.net.URL;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.InputEvent;
import java.net.InetAddress;

public class Main {
    // Sockets
    static Socket s, as;
    static PrintWriter pw;
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
        boolean isConnected = false;
        String ip_address = "";
        try {
            ip_address = InetAddress.getLocalHost().getHostAddress();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("Starting the server...");
        try {
            // Establish the socket connection and listen for incoming
            ss = new ServerSocket(8080);
            System.out.println("Server has started! Accepting connections.");

            /* ----------------
            ** SWING COMPONENTS
            ** --------------*/
            JFrame frame = new JFrame("Mobility Connect");
            frame.setSize(400, 300);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setResizable(false);
            
            URL iconURL = Main.class.getResource("web_hi_res_512_orange.png");
            URL logoURL = Main.class.getResource("web_hi_res_512_white.png");

			// iconURL is null when not found
			ImageIcon icon = new ImageIcon(iconURL);
			frame.setIconImage(icon.getImage());

            JPanel contentPane = new JPanel();
            contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
            contentPane.setBackground(Color.decode("#ff5722"));

            ImageIcon logo = new ImageIcon(logoURL);

            Image image = logo.getImage(); // transform it 
			Image newimg = image.getScaledInstance(120, 120,  java.awt.Image.SCALE_AREA_AVERAGING); // scale it the smooth way  
			logo = new ImageIcon(newimg); 
            JLabel logoLabel = new JLabel(logo, JLabel.CENTER);
            logoLabel.setSize(60, 60);
            logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPane.add(logoLabel);

            // TODO: Add disconnected icon to the Frame here

            JLabel title = new JLabel("Mobility");
            title.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            title.setForeground(Color.white);
            title.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPane.add(title);

            JLabel info = new JLabel("Your IP address is:");
            info.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            info.setForeground(Color.white);
            info.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPane.add(info);

            JLabel ip = new JLabel(ip_address);
            ip.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            ip.setForeground(Color.white);
            ip.setAlignmentX(Component.CENTER_ALIGNMENT);
           	contentPane.add(ip);

            JLabel serverStatus = new JLabel("No phone connected.");
            serverStatus.setFont(new Font("Segoe UI", Font.PLAIN, 18));
            serverStatus.setForeground(Color.white);
            serverStatus.setAlignmentX(Component.CENTER_ALIGNMENT);
            contentPane.add(serverStatus);

            frame.add(contentPane);
            frame.setVisible(true);

            // If the socket was started
            while (true) {
                // Accept a packet
                s = ss.accept();
                if (s != null) {
                    System.out.println("[SERVER] Packet received!");
                }

                // Read the line
                isr = new InputStreamReader(s.getInputStream());
                br = new BufferedReader(isr);
                message = br.readLine();
                System.out.println("[" + ip_address + "] MESSAGE: " + message);

                // Toggle connected status if needed
                if (!isConnected) {
                    if (message.equals("connect")) {
                        serverStatus.setText("Phone connected.");
                        // TODO: Change the icon here

                        isConnected = true;
                    }
                }
                if (isConnected) {
                    if (message.equals("disconnect")) {
                        serverStatus.setText("No phone connected.");
                        // TODO: Change the icon here

                        isConnected = false;
                    }
                }

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
            } else if (message.equals("bsp")) { //backspace key
            	bot.keyPress(KeyEvent.VK_BACK_SPACE);
            	bot.keyRelease(KeyEvent.VK_BACK_SPACE);
            } else if (message.equals("ent")) {
            	bot.keyPress(KeyEvent.VK_ENTER);
            	bot.keyRelease(KeyEvent.VK_ENTER);
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
            e.printStackTrace();
        }
    }
}
