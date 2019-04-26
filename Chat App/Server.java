import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Scanner;
import java.io.File;

import javax.swing.*;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

//  open to access 

public class Server extends JFrame{
    // GUI objects
    private JTextField userTypeHere;
    private JTextArea chatHistoryWindow;
    
    // connection-related objects
    private ObjectOutputStream outputStream; 
    private ObjectInputStream inputStream;
    private ServerSocket server;
    private Socket connectionSocket;

    //constructor  method: GUI
    public Server(){
        super("Coffee ChatApp - HOST");
        userTypeHere = new JTextField();
        userTypeHere.setEditable(false); // can only type if userA is connected to userB
        userTypeHere.addActionListener(
            new ActionListener(){
                //@Override
                public void actionPerformed(ActionEvent e) {
                    sendMessage(e.getActionCommand());
                    userTypeHere.setText(""); // clears out field where user types in msg to prevent clutter
            } //end actionPerformed
        }); // end ActionListener
        add(userTypeHere, BorderLayout.SOUTH); 
        userTypeHere.setFont(userTypeHere.getFont().deriveFont(25f));
        chatHistoryWindow = new JTextArea();
        chatHistoryWindow.setEditable(false);
        chatHistoryWindow.setFont(chatHistoryWindow.getFont().deriveFont(25f));
        add(new JScrollPane(chatHistoryWindow));

        setIconImage(new ImageIcon("C:\\Users\\Monica\\Desktop\\Chat App_Server\\CoffeeIcon.png").getImage());  // find a way to not need the whole path??
        ImageIcon image = new ImageIcon( "C:\\Users\\Monica\\Desktop\\Chat App_Server\\CoffeeIcon.png" ); 

        setSize(850,500); 
        setVisible(true);
    } // end constructor
    
    // server initialization and execution
    public void runChatApp(){
        try{
            server = new ServerSocket(6666, 10);  //may need to change port number
            // app runs forever until termination
            while(true){
                try{
                    waitForConnection();
                    establishIOStreams();
                    chatExchange();
                }catch(EOFException eofException){
                    showMessage("\n The server has terminated the connection. \n");  // not necessarily an error- just means there's no more connection
                }finally{
                    closeApp();
                } // end try-catch-finally
            } // end while
        }catch(IOException ioException){
            ioException.printStackTrace();
        } // end try-catch
    } // end runChatApp()

    // Delete?
    // method to set username
    private String setUsername(){
        Scanner scan = new Scanner(System.in);
        showMessage("Please enter a screen-name: ");
        String name=scan.nextLine();
        return name;
    }

    //method to wait for connection
    private void waitForConnection() throws IOException{
        showMessage("Waiting for someone else to connect... \n");
        connectionSocket = server.accept();
        showMessage("You're now connected to "+ connectionSocket.getInetAddress().getHostName());
    } // end waitForConnection method

    // get streams to send and receive messages
    private void establishIOStreams() throws IOException{
        outputStream = new ObjectOutputStream(connectionSocket.getOutputStream()); // create pathway to connect to another CPU
        outputStream.flush(); 
        inputStream = new ObjectInputStream(connectionSocket.getInputStream()); // pathway to receive messages
        showMessage("\n Streams have been established successfully. \n");
    } // end establishIOStreams method

    //method wil run for the duration of the chat
    private void chatExchange() throws IOException{
        String message=""; // may cause issues later?
        sendMessage("You are now connected! Send messages.\n");
        ableToType(true);
        do{
            try{
                message = (String) inputStream.readObject();
                showMessage("\n" + message);
            }catch(ClassNotFoundException classNotFoundException){
                showMessage("\n User attempted to send invalid input. \n");
            } // end try/catch
        }while(!message.equals("CLIENT - END") || (!message.equals("SERVER - END")));
    } // end chatExchange method

    // terminates streams and sockets
    private void closeApp() throws IOException{
        showMessage("\n ...Closing connections...\n");
        ableToType(false);
        try{
            outputStream.close();
            inputStream.close();
            connectionSocket.close();
        }catch(IOException ioException){
            ioException.printStackTrace();
        } // end try-catch
    } // end closeApp method

    // send message to the client (only seen by one user)
    private void sendMessage(String messageInBox){
        try{
            outputStream.writeObject("SERVER - "+ messageInBox);   //change to username
            outputStream.flush();                       //housekeeping
            showMessage("\nSERVER - "+ messageInBox);   //change to username
        }catch(IOException ioException){
            chatHistoryWindow.append("\n ERROR: Unable to send message!");
        } // end try-catch
    } // end sendMessage method

    //  shows message on main chat window of GUI
    private void showMessage(final String txt){
        SwingUtilities.invokeLater(
            new Runnable(){
                public void run(){
                    chatHistoryWindow.append(txt);
                } // end run method
            }
        ); 
    } // end showMessage method

    // allows user to type in box
    private void ableToType(final boolean permission){
        SwingUtilities.invokeLater(
            new Runnable(){
                public void run(){
                    userTypeHere.setEditable(permission);
                } // end run method
            } // end Runnable 
        ); // end SwingUtil
    } // end ableToType 
} // end Server class