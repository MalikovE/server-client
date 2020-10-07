import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class GUIClient extends JFrame {
    private static byte[] buffer = new byte[256];

    public GUIClient() {
        super("Spodus client");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 300);
        setLocationRelativeTo(null);

        initComponents();

        setLayout(null);
        setVisible(true);
    }

    private void initComponents() {
        JLabel requestLabel = new JLabel("Enter request: ");
        requestLabel.setBounds(40, 20, 100, 30);
        add(requestLabel);

        JTextField requestField = new JTextField();
        requestField.setBounds(160, 20, 600, 30);
        add(requestField);

        JLabel responseLabel = new JLabel("Response: ");
        responseLabel.setBounds(40, 70, 100, 30);
        add(responseLabel);

        JLabel response = new JLabel("");
        response.setBounds(160, 70, 600, 30);
        add(response);

        JButton sendButton = new JButton("Send");
        sendButton.setBounds(800, 20, 100, 30);
        sendButton.addActionListener(e -> {
            try (Socket socket = new Socket("localhost", 8888);
                 DataInputStream input = new DataInputStream(socket.getInputStream());
                 DataOutputStream output = new DataOutputStream(socket.getOutputStream())
            ) {
                String request = requestField.getText();
                if (request != null && request.trim().length() > 0) {
                    output.write(TPPUtilities.hexStringToByteArray(request));
                }

                int buffLen = input.read(buffer, 0, buffer.length);
                if (buffLen > 0) {
                    response.setText(TPPUtilities.bytesToStringRaw(buffer, buffLen));
                }

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        add(sendButton);
    }

    public static void main(String[] args) {
        new GUIClient();
    }
}
