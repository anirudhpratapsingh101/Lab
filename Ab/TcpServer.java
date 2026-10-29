import java.io.*;
import java.net.*;
import java.util.Date;

public class TcpServer {
    public static void main(String[] args) throws Exception {
        ServerSocket ss = new ServerSocket(8088);
        System.out.println("server is ready");

        while (true) {
            Socket ls = ss.accept();
            InputStream is = ls.getInputStream();
            byte data[] = new byte[50];
            int bytesRead = is.read(data);

            if (bytesRead != -1) {
                String mfc = new String(data, 0, bytesRead);
                mfc = mfc.trim();

                Date d = new Date();
                String mfs = d.toString();

                OutputStream os = ls.getOutputStream();
                os.write(mfs.getBytes());
            }
            ls.close();
        }
    }
}