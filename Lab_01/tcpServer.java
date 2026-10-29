import java.io.*; 
import java.net.*; 

public class tcpServer { 
    public static void main(String[] args) throws Exception { 
        // Use try-with-resources to automatically close the ServerSocket when done
        try (ServerSocket ss = new ServerSocket(8088)) {
            System.out.println("Server is sitting at port 8088 and waiting for a client..."); 
            
            // accept() waits for a client.
            Socket ls = ss.accept(); 
            System.out.println("Client connected! Port is " + ls.getPort()); 
            
            // Get streams ONCE outside the loop for efficiency
            InputStream is = ls.getInputStream();  
            OutputStream os = ls.getOutputStream();
            
            byte[] data = new byte[50]; 
            int bytesRead;

            // read() returns the number of bytes read, or -1 if the client disconnected
            while ((bytesRead = is.read(data)) != -1) { 
                // Create a string only from the bytes actually read to avoid garbage data
                String mfc = new String(data, 0, bytesRead).trim(); 
                
                System.out.println("Received from client: " + mfc);
                
                // Fixed the variable name typo here (mfs instead of mts)
                String mfs = "The message was: " + mfc; 
                
                // Send the response back to the client
                os.write(mfs.getBytes());  
            }
            
            System.out.println("Client disconnected.");
            ls.close(); // Close the client connection once they leave
        } 
    } 
}