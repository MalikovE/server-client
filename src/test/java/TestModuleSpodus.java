public class TestModuleSpodus {
    public static void main(String[] args) {
        Server server = new ServerNaiveImpl(8888);
        server.start();
    }
}
