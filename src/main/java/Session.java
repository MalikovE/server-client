import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Session extends Thread {
    private final Socket clientSocket;
    private DataInputStream input;
    private DataOutputStream output;
    private byte[] socketBuff = new byte[256];

    public Session(Socket clientSocket) {
        this.clientSocket = clientSocket;
        try {
            input = new DataInputStream(clientSocket.getInputStream());
            output = new DataOutputStream(clientSocket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                int buffLen = input.read(socketBuff, 0, socketBuff.length);
                if (buffLen > 0) {
                    System.out.println("Received from socket: " + TPPUtilities.bytesToStringRaw(socketBuff, buffLen));
                    output.write(socketBuff);
                } else {
                    Thread.sleep(50);
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
