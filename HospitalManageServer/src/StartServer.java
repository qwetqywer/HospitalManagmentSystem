public class StartServer {
    public static final int PORT_WORK = 3000;

    public static void main(String[] args) {
        MultiThreadedServer server = new MultiThreadedServer(PORT_WORK);
        new Thread(server).start();
        // System.out.println("Stopping Server");
        //server.stop();
    }
}