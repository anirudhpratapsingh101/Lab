import java.net.*;
import java.io.*;

class TcpClient {
    public static void main(String[] args) throws Exception {
        System.out.println("connecting to server");
        Socket cs=new Socket("localhost",8088);
        boolean on=true;
        int timeout=90;
        cs.setKeepAlive(on);
        cs.setSoLinger(on,timeout);
        cs.setSendBufferSize(67);
        cs.setReceiveBufferSize(789);
        cs.setTcpNoDelay(on);

        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

        System.out.println("The Local Port "+cs.getLocalPort()+"\nThe Remote Port"+cs.getPort());
        System.out.println("The Local socket is "+cs);
        System.out.println("Enter your name");
        String str=br.readLine();
        OutputStream os=cs.getOutputStream();
        os.write(str.getBytes());
        InputStream is=cs.getInputStream();
        byte data[]=new byte[50];
        is.read(data);
        String mfs=new String(data);
        mfs=mfs.trim();
        System.out.println(mfs);

        System.out.println("SO_KEEPALIVE is enabled: "+cs.getKeepAlive());
        System.out.println("Timeout value: " +cs.getSoLinger());
        System.out.println("Send Buffer size: "+cs.getSendBufferSize());
        System.out.println("Buffer size: "+cs.getReceiveBufferSize());
        System.out.println("SO_TIMEOUT option is enabled: "+cs.getTcpNoDelay());
    }
}