import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;

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
    private Sting serverIP;
    private Socket connectionSocket;

    //constructor
    public Client(String host){
        super("ChatApp Client");
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
        add(UserException, BorderLayout.SOUTH);
        chatHistoryWindow = new JTextArea();
        add(new JScrollPane(chatHistoryWindow),BorderLayout.CENTER);
        setSize(850,700); 
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
    
    private void connectToServer() throws IOException{
        showMessage("Attempting to connect...Please wait...\n");
        //create socket bw client and another computer
        //get server IP address and port number 
        connection = new Socket(InetAddress.getByName(serverIP), 6666);
        showMessage("You're now connected to " + connection.getInetAddress().getHostName());
    } // end method connectToServer
    
    // set up streams to send/recieve messages
    private void setupStreams() throws IOException{
        output = new ObjectOutputStream(connection.getOutputStream());
        output.flush();
        input = new ObjectInputStream(connection.getInputStream());
        showMessage("\nStreams have been set up- you may begin chatting \n");
    } // end method setupStreams
    
    // while chatting to server
    private void chatExchange() throws IOException{
        permissionToType(true);
        do{
            try{
                message = (String) input.readObject();
                // appear for user in new chat window
                showMessage("\n"+message);
            }catch(ClassNotFoundException cnfexception){
                showMessage("\n Invalid input. \n");
            }
        } while(!message.equals("SERVER - END"));
    } // end method
    
    // close streams and sockets
    private void closeApp(){
        showMessage("\n Closing streams and sockets...\n");
        permissionToType(false);
        try{
            output.close();
            input.close();
            connection.close();
        } catch(IOException ioe){
            ioe.printStackTrace();
        } // end try/catch
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
                    userTypeHere.setEdiable(torf);
                } // end run()
            } 
        ); // end invoke later
    } // end permissionToType ()

} //end class