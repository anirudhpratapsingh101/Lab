import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serial;
import java.net.Socket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

public class ChatGUI extends JFrame implements ActionListener {

    @Serial
    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(ChatGUI.class.getName());

    Socket s;
    JButton button;
    JTextArea ta1, ta2;
    String msg = "", title;
    JScrollPane scrollPane1, scrollPane2;
    InputStream is;
    OutputStream os;

    public ChatGUI(Socket x, String str) {
        s = x;
        title = str;
        button = new JButton("SEND");

        // UI IMPROVEMENT: Set proportions (History area large, input area small)
        ta1 = new JTextArea();
        ta2 = new JTextArea(3, 20);
        ta1.setEditable(false);

        // UI IMPROVEMENT: Add line wrapping and a cleaner font
        Font chatFont = new Font("SansSerif", Font.PLAIN, 14);
        ta1.setFont(chatFont);
        ta2.setFont(chatFont);
        ta1.setLineWrap(true);
        ta1.setWrapStyleWord(true);
        ta2.setLineWrap(true);
        ta2.setWrapStyleWord(true);

        scrollPane1 = new JScrollPane(ta1);
        scrollPane2 = new JScrollPane(ta2);

        // UI IMPROVEMENT: Use BorderLayout with 10px spacing between components
        setLayout(new BorderLayout(10, 10));

        // UI IMPROVEMENT: Add padding around the inner edges of the window
        getRootPane().setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Group the input area and send button together at the bottom
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.add(scrollPane2, BorderLayout.CENTER);
        bottomPanel.add(button, BorderLayout.EAST);

        // Add components to the main window
        add(scrollPane1, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        button.addActionListener(this);

        // UI IMPROVEMENT: Change window size to be taller and wider
        setSize(450, 600);
        setLocationRelativeTo(null); // Centers the window on the screen
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setTitle("Messenger - " + title);

        try {
            is = s.getInputStream();
            os = s.getOutputStream();
        } catch (IOException ioe) {
            logger.log(Level.SEVERE, "Failed to initialize input/output streams", ioe);
        }

        // Run chat() in a background thread using an expression lambda
        new Thread(this::chat).start();
    }

    public void chat() {
        while (true) {
            try {
                byte[] data = new byte[50];
                int bytesRead = is.read(data);

                if (bytesRead == -1) {
                    break;
                }

                msg = new String(data, 0, bytesRead).trim();
                final String finalMsg = msg;

                // Update UI from the Event Dispatch Thread using expression lambda
                SwingUtilities.invokeLater(() -> ta1.append(title + ": " + finalMsg + "\n"));

            } catch (SocketException se) {
                SwingUtilities.invokeLater(() -> {
                    JOptionPane.showMessageDialog(this, "Disconnected from " + title);
                    this.dispose();
                });
                break;
            } catch (Exception e) {
                logger.log(Level.SEVERE, "Error reading incoming message", e);
                break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        msg = ta2.getText().trim(); // Trim extra whitespace before sending
        if (msg.isEmpty()) return;  // Don't send empty messages

        try {
            os.write(msg.getBytes());
            ta1.append("Me: " + msg + "\n");
            ta2.setText("");
        } catch (IOException ioe) {
            logger.log(Level.SEVERE, "Failed to send message", ioe);
        }
    }
}