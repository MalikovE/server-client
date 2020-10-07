import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class TestClient {
    private static byte[] buffer = new byte[256];

    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 8888);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream())
        ) {
            Scanner scanner = new Scanner(System.in);
            String request = scanner.next();

            output.write(TPPUtilities.hexStringToByteArray(request));

            int buffLen = input.read(buffer, 0, buffer.length);

            if (buffLen > 0) {
                System.out.println("Received from USPD: " + TPPUtilities.bytesToStringRaw(buffer, buffLen));
            }

            Thread.sleep(60_000);
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
