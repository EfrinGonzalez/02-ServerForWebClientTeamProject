/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package itu.dk.smds.e2013.common;

/**
 *
 * @author rao
 */
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TcpClient {
	
//This method opens the connection to the server and sends the requested data
    public String openConnection(String msg){
    	String response="";
    	try {
            // IP address of the server,
            InetAddress serverAddress = InetAddress.getByName("localhost");
            
            // It is the same port where server will be listening.
            int serverPort = 7896;
            //int serverPort = 4444;
            
            String message = msg;
            // Open a socket for communication.
            Socket socket = new Socket(serverAddress, serverPort);
            
            // Get data output stream to send a String message to server.
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            dos.writeUTF(message);
            
            dos.flush();
            
            // Now switch to listening mode for receiving message from server.
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            
           // Note that this is a blocking call,  
            //String 
            response = dis.readUTF();
            
            
            // Print the message.
           // System.out.println("Message from Server: " + response);
            
            
            // Finnaly close the socket. 
            socket.close();
           // return (response);
        } catch (IOException ex) {
            Logger.getLogger(TcpClient.class.getName()).log(Level.SEVERE, null, ex);
            
            System.out.println("error message: " + ex.getMessage());
        }
		return response; 
        
    }
}