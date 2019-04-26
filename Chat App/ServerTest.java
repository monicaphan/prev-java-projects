// Run me first!! for VS code: ctrl+alt+n

import javax.swing.JFrame;

public class ServerTest {
    public static void main(String [] args){
        Server breakfast = new Server();
        breakfast.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        breakfast.runChatApp();
    }
}
