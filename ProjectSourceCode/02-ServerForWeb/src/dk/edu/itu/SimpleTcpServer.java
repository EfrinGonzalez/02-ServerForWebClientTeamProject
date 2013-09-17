/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.edu.itu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.stream.FileImageInputStream;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.output.XMLOutputter;

/**
 *
 * @author rao
 */
public class SimpleTcpServer {
	

    public static void main(String args[]) {
        
        
        try {
            int serverPort = 7896;

            // create a server socket listening at port 7896
            ServerSocket serverSocket = new ServerSocket(serverPort);
            System.out.println("Server started at 7896");
            // Server starts accepting requests.
            // This is blocking call, and it wont return, until there is request from a client.
            Socket socket = serverSocket.accept();
            // Get the inputstream to receive data sent by client. 
            InputStream is = socket.getInputStream();
            // based on the type of data we want to read, we will open suitable input stream.  
            DataInputStream dis = new DataInputStream(is);

            // Read the String data sent by client at once using readUTF,
            // Note that read calls also blocking and wont return until we have some data sent by client. 
            String message = dis.readUTF(); // blocking call

            // Print the message.
            System.out.println("Message from Client: " + message);


            // Now the server switches to output mode delivering some message to client.
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            outputStream.writeUTF("Hello Client, we can hear you...!");
            
            
            
            /*Let's try here to read the xml file*/
       /*     try {
                //InputStream xmlStream = getServletContext().getResourceAsStream("/WEB-INF/task-manager-xml.xml");
               // InputStream in = new FileInputStream("C:\\Users\\Efrin Gonzalez\WorkspaceEE_MobileAndDistSyst\02-ServerForWeb\src\resources\task-manager-xml.xml");
            	 InputStream in = new FileInputStream("C:\\Users\\Efrin Gonzalez\\WorkspaceEE_MobileAndDistSyst\\02-ServerForWebClientTeamProject\\src\\resources\\task-manager-xml.xml");
            	//													/02-ServerForWebClientTeamProject/src/resources/task-manager-xml.xml
            	 //This line takes the all xml file
                    String query = "//task";
               
               // Document tasksDoc = TasksJDOMParser.GetTasksByQuery(in, query);
                 String tasksDoc = TasksJDOMParser.GetTasksByQuery(in, query);
                    
                //new XMLOutputter().output(tasksDoc, out);
                System.out.println("Info came complete!");
                //We have to return the type Document here
                
                outputStream.writeUTF("Hello Client, we can hear you...!"+tasksDoc);
                
            } catch (JDOMException ex) {
                //Logger.getLogger(GetAllTasksServlet.class.getName()).log(Level.SEVERE, null, ex);
            }*/
            
            //-------finish of xml file processing
            

            outputStream.flush();

            socket.close();
            
            serverSocket.close();


        } catch (IOException ex) {
            Logger.getLogger(SimpleTcpServer.class.getName()).log(Level.SEVERE, null, ex);

            System.out.println("error message: " + ex.getMessage());
        }



    }
}