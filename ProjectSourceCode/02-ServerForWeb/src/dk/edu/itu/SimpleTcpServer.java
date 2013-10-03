/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dk.edu.itu;

//import Server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ListIterator;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.stream.FileImageInputStream;
import javax.lang.model.element.Element;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.jdom2.Document;
import org.jdom2.JDOMException;
import org.jdom2.output.XMLOutputter;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

//import SimpleTc.ServerThread;

import dk.edu.itu.serialization.Cal;
import dk.edu.itu.serialization.Task;


//In order to have a better understanding of what is going on with the threads, check this video out. 
//http://www.youtube.com/watch?v=2cQJJwoSNLk

public class SimpleTcpServer {
	
	public static final int serverPort = 7896; 
    public static void main(String args[]) throws IOException{
        
    	new SimpleTcpServer().runServer();
    }
    
	public void runServer() throws IOException{
		ServerSocket serverSocket = new ServerSocket(serverPort);
		System.out.println("Server up & ready for connections at 7896...");
		
		while(true){//we accept different connections from different clients
			Socket socket = serverSocket.accept();
			new ServerThread(socket).start();
		}
		
	}
    
    //This function is pretty much to show on screen what we have ask for. 
    private static void PrintTaskObject(Task task) {
        try {
            StringWriter writer = new StringWriter();
            // create a context object for Student Class
            JAXBContext jaxbContext = JAXBContext.newInstance(Cal.class);
            // Call marshal method to serialize student object into Xml
            jaxbContext.createMarshaller().marshal(task, writer);
            System.out.println(writer.toString());
        } catch (JAXBException ex) {
            //Logger.getLogger(UniversitySerializer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //this function writes into the file, as its name suggests.
    private static void SaveFile(String xml, String path) throws IOException {
        File file = new File(path);
        // create a bufferedwriter to write Xml
        BufferedWriter output = new BufferedWriter(new FileWriter(file));
        output.write(xml);
        output.close();
    }
    
    
   
  //-------An inner class for the thread--------
  	public class ServerThread extends Thread{
  		Socket socket;
  		
  		ServerThread(Socket socket){
  			this.socket = socket;
  		}

  		public void run(){
  			 try {  
  	            // Get the inputstream to receive data sent by client. 
  	            InputStream is = socket.getInputStream();
  	            // based on the type of data we want to read, we will open suitable input stream.  
  	            DataInputStream dis = new DataInputStream(is);

  	            // Read the String data sent by client at once using readUTF,
  	            // Note that read calls also blocking and wont return until we have some data sent by client. 
  	            String message = dis.readUTF(); // blocking call

  	            // Print the message if you want to see what the client says.
  	         //   System.out.println("Message from Client: " + message);


  	            //-------------------------------------------
  	            //In the next section we try to work with the serialization part
  	            StringWriter writer = new StringWriter();
  	            try {
  	            	Object obj = new Object(); 
  	            	String path = "C:/Users/Efrin Gonzalez/Documents/task-manager-xml.xml";
  	            	FileInputStream stream = new FileInputStream("C:/Users/Efrin Gonzalez/Documents/task-manager-xml.xml");
  	            	
  	                // create an instance context class, to serialize/deserialize.
  	                JAXBContext jaxbContext = JAXBContext.newInstance(Cal.class);

  	                // Deserialize university xml into java objects.
  	                Cal cal = (Cal) jaxbContext.createUnmarshaller().unmarshal(stream);
  	                
  	                // Iterate through the collection of student object and print each student object in the form of Xml to console.
  	                ListIterator<Task> listIterator = cal.tasks.listIterator();            
  	                System.out.println("Printing student objects serialized into Xml");            
  	                
  	                while (listIterator.hasNext()) {
  	                    PrintTaskObject(listIterator.next());
  	                    
  	                }

  	            
  	                
  	                // Serialize university object into xml.            
  	                //StringWriter writer = new StringWriter();
  	                

  	                // We can use the same context object, as it knows how to 
  	                //serialize or deserialize University class.
  	                jaxbContext.createMarshaller().marshal(cal, writer);
  	                
  	                System.out.println("Printing serialized university Xml before saving into file!");
  	                
  	                // Print the serialized Xml to Console.
  	                System.out.println(writer.toString());  	                
  	        
  	                // Finally save the Xml back to the file.
  	               SaveFile(writer.toString(), path);
  	           
  	            } catch (JAXBException ex) {
  	                //Logger.getLogger(UniversitySerializer.class.getName()).log(Level.SEVERE, null, ex);
  	            }
  	            
  	            //The next lines originally go out of this try catch (around line 121)
  	            // Now the server switches to output mode delivering some message to client.
  	            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
  	            outputStream.writeUTF(writer.toString());  	         
  	            outputStream.flush();
  	            socket.close();
  	        } catch (IOException ex) {
  	            Logger.getLogger(SimpleTcpServer.class.getName()).log(Level.SEVERE, null, ex);

  	            System.out.println("error message: " + ex.getMessage());
  	        }
  		}
  		
  	}//end of the inner class    
    
}//End of the outer class