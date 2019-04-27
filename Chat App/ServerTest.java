// Run me first!! for VS code: ctrl+alt+n

import javax.swing.JFrame;
import java.net.InetAddress;

public class ServerTest {
    public static void main(String [] args) throws Exception{

        // check ip address 
        InetAddress inetAddress = InetAddress.getLocalHost();
        System.out.println("IP Address:- " + inetAddress.getHostAddress());
        System.out.println("Host Name:- " + inetAddress.getHostName());

        Server breakfast = new Server();
        breakfast.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        breakfast.runChatApp();
    }
}
