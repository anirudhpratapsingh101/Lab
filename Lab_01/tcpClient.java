import java.net.*; 
import java.io.*; 

public class tcpClient { 
    public static void main(String[] args) throws Exception { 
        System.out.println("Connecting to server..."); 
        
        // connects the client to the server running on the same computer at port 8088
        Socket cs = new Socket("localhost", 8088); 
        
        // Creates an input reader to take text from the keyboard.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in)); 
        
        System.out.println("The Local Port: " + cs.getLocalPort() + "\nThe Remote Port: " + cs.getPort()); 
        System.out.println("The Local socket is: " + cs); 
        System.out.println("Enter your name:"); 
        
        String str = br.readLine(); // Reads the name entered by the user and stores it in str.
        
        // SENDING DATA TO SERVER 
        // Gets the output stream of the client socket cs, which is used to send data from the client to the server.
        OutputStream os = cs.getOutputStream(); 
        
        // Converts the entered String into bytes and sends it to the server.
        os.write(str.getBytes()); 
        
        // READING DATA FROM SERVER 
        // Gets the input stream of the client socket, which is used to receive data sent by the server.
        InputStream is = cs.getInputStream(); 
        
        // Creates a byte array of size 50 to temporarily store the reply coming from the server.
        byte data[] = new byte[50]; 
        
        // Read the server's reply
        int bytesRead = is.read(data); 
        
        // PRINTING MESSAGE ON CLIENT CONSOLE 
        // Using bytesRead ensures we only read the exact message, ignoring empty buffer space
        String mfs = new String(data, 0, bytesRead).trim(); 
        System.out.println(mfs); 
        
        // Good practice: Close the connection when done
        cs.close();
    } 
}