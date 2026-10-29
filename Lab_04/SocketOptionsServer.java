import java.io.*;
import java.net.*;

public class SocketOptionsServer {

    public static void main(String[] args) {
        int port = 5000;

        try {
            ServerSocket serverSocket = new ServerSocket(port);

            System.out.println("Server started...");
            System.out.println("Waiting for client on port " + port);

            Socket socket = serverSocket.accept();

            System.out.println("Client connected: "
                    + socket.getInetAddress());

            socket.setKeepAlive(true);
            System.out.println("\nSO_KEEPALIVE");
            System.out.println("Value: " + socket.getKeepAlive());

            socket.setSoLinger(true, 10);
            System.out.println("\nSO_LINGER");
            System.out.println("Enabled: " + (socket.getSoLinger() != -1));
            System.out.println("Linger time: "
                    + socket.getSoLinger() + " seconds");

            socket.setSendBufferSize(8192);
            System.out.println("\nSO_SNDBUF");
            System.out.println("Send buffer size: "
                    + socket.getSendBufferSize() + " bytes");

            socket.setReceiveBufferSize(8192);
            System.out.println("\nSO_RCVBUF");
            System.out.println("Receive buffer size: "
                    + socket.getReceiveBufferSize() + " bytes");

            socket.setTcpNoDelay(true);
            System.out.println("\nTCP_NODELAY");
            System.out.println("Value: " + socket.getTcpNoDelay());

            BufferedReader input = new BufferedReader(
                    new InputStreamReader(socket.getInputStream()));

            String message = input.readLine();
            System.out.println("\nMessage from client: " + message);

            PrintWriter output = new PrintWriter(
                    socket.getOutputStream(), true);

            output.println("Message received by server.");

            input.close();
            output.close();
            socket.close();
            serverSocket.close();

            System.out.println("\nConnection closed.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}