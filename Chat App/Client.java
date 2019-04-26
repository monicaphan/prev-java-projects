import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

import javax.swing.*;
import javax.swing.SwingUtilities;

/* sits on personal device. no one else can access this unless it
   is through the server to ensure safety of device. */

public class Client extends JFrame{
    
    private JTextField userTypeHere;
    private JTextArea chatHistoryWindow;
    private ObjectOutputStream output;
    private ObjectInputStream input;
    private String message = "";
    private String serverIP;
    private Socket connectionSocket;

    //constructor
    public Client(String host){
        super("Coffee ChatApp - CLIENT");
        serverIP = host;                    // insert server IP
        userTypeHere = new JTextField();
        userTypeHere.setEditable(false);    // false until connection has been established
        userTypeHere.addActionListener(
            new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    sendMessage(e.getActionCommand()); 
                    userTypeHere.setText("");
                } // end actionPerformed
            } 
        );  
        userTypeHere.setFont(userTypeHere.getFont().deriveFont(25f));
        add(userTypeHere, BorderLayout.SOUTH);
        chatHistoryWindow = new JTextArea();
        add(new JScrollPane(chatHistoryWindow),BorderLayout.CENTER);
        chatHistoryWindow.setEditable(false); // prevent user from typing in box
        chatHistoryWindow.setFont(chatHistoryWindow.getFont().deriveFont(25f));

        setIconImage(new ImageIcon("C:\\Users\\Monica\\Desktop\\Chat App_Server\\CoffeeIcon.png").getImage());  // find a way to not need the whole path??
        ImageIcon image = new ImageIcon( "C:\\Users\\Monica\\Desktop\\Chat App_Server\\CoffeeIcon.png" ); 
        
        setSize(850,500); 
        setVisible(true);
    } // end constructor 

    // connect to server
    public void runApp(){
        try{
            connectToServer();                                      // only connect to one server
            establishIOStreams(); //setupStreams
            chatExchange(); // whilechatting
        }catch(EOFException eofException){
            showMessage("\n Client terminated the connection.");    // we generally want this answer
        }catch(IOException ioException){   
            ioException.printStackTrace();                          // u dunn goofed, brother. 
        } finally{
            closeApp();                                             // housekeeping
        }// end try-catch-catch-finally
    }
    
    // est. socket between client and server
    private void connectToServer() throws IOException{
        showMessage("Attempting to connect...Please wait...\n");
        connectionSocket = new Socket(InetAddress.getByName(serverIP), 6666);
        showMessage("CLIENT is now connected to " + connectionSocket.getInetAddress().getHostName());
    } // end method connectToServer
    
    // set up streams to send/recieve messages
    private void establishIOStreams() throws IOException{
        output = new ObjectOutputStream(connectionSocket.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connectionSocket.getInputStream());
        showMessage("\nStreams have been set up- you may begin chatting \n");
    } // end method establishIOStreams
    
    // while chatting to server
    private void chatExchange() throws IOException{
        permissionToType(true);
        do{
            try{
                message = (String) input.readObject();
                showMessage("\n"+message);
            }catch(ClassNotFoundException cnfexception){
                showMessage("\n Invalid input. \n");
            }
        } while(!message.equals("SERVER - END") || !message.equals("CLIENT - END"));
    } // end method
    
    // close streams and sockets
    private void closeApp(){
        showMessage("\n Closing streams and sockets...\n"); // inform user of function
        permissionToType(false);                            
        try{
            output.close();
            input.close();
            connectionSocket.close();
        } catch(IOException ioe){
            ioe.printStackTrace();
        } // end try/catch
        showMessage("\n Disconnected from server.");
    } // end method closeApp()
    
    // send msg to user on server  
    private void sendMessage(String message){
        try{
            output.writeObject("CLIENT - " + message);
            output.flush();
            showMessage("\nCLIENT - "+message);
        }catch(IOException ioe){
            chatHistoryWindow.append("\nBing-bong something went wrong! Unable to send your message. \n");
        } // end try/catch
    } //end method 

    // showMessage to chatHistoryWindow (update GUI)
    private void showMessage(final String msg){
        SwingUtilities.invokeLater(
            new Runnable(){
                public void run(){
                    chatHistoryWindow.append(msg);
                }
            }
        );
    } // end method showMessage

    //permission to type in textbox
    private void permissionToType(final boolean torf){
        SwingUtilities.invokeLater(
            new Runnable(){
                public void run(){
                    userTypeHere.setEditable(torf);
                } // end run()
            } 
        ); // end invoke later
    } // end permissionToType ()

} //end class