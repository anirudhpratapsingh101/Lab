import java.io.*;
import java.net.*;

public class SocketOptionsClient {

    public static void main(String[] args) {
        String host = "localhost";
        int port = 5000;

        try {
            // Connect to server
            Socket socket = new Socket(host, port);

            System.out.println("Connected to server.");

            // Send message
            PrintWriter output =
                    new PrintWriter(socket.getOutputStream(), true);

            output.println("Hello Server!");

            // Receive response
            BufferedReader input =
                    new BufferedReader(
                            new InputStreamReader(socket.getInputStream()));

            String response = input.readLine();

            System.out.println("Server response: " + response);

            // Close connection
            input.close();
            output.close();
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}