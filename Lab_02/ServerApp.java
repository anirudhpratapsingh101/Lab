import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerApp implements Runnable {
    private Socket s; // Corrected: Made this an instance variable to avoid thread race conditions
    public static int i = 1;

    // Constructor to assign the accepted socket to this thread
    public ServerApp(Socket s) {
         this.s = s;
    }

    public static void main(String[] args) {
        try (ServerSocket ss = new ServerSocket(8089)) {
            while (true) {
                System.out.println("Waiting for client " + i);
                Socket socket = ss.accept();
                i++;

                // Pass the specific connection socket to the new Runnable
                ServerApp sa = new ServerApp(socket);
                Thread t = new Thread(sa);
                t.start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            InputStream is = s.getInputStream();
            byte[] b = new byte[1024];
            is.read(b);
            String clientName = new String(b).trim();
            new ChatGUI(s, clientName);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}