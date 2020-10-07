import java.io.IOException;
import java.net.ServerSocket;

public class ServerNaiveImpl implements Server {
    private final int port;

    public ServerNaiveImpl(int port) {
        this.port = port;
    }

    @Override
    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            while (true) {
                Session session = new Session(serverSocket.accept());
                session.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
