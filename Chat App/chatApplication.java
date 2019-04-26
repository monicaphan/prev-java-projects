// Run me after ServerTest.java- this runs the Client.java code
// Make sure other device has Client.java and chatApplication.java
// ipv4 address of my mac on resNet: 10.122.172.184
// local loopback 127.0.0.1


import javax.swing.JFrame;

// test client 
public class chatApplication{
    public static void main (String[] args){
        Client lunch;
        lunch = new Client("10.122.172.184"); //local host for testing since using same cpu as server for now
        lunch.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // launch server
        lunch.runApp(); // from Client.java

    } // end main
} // end class