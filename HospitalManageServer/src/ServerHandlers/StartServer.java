package ServerHandlers;

import ServerHandlers.MultiThreadedServer;

public class StartServer {
    public static final int PORT_WORK = 8005;

    public static void main(String[] args) {
        MultiThreadedServer server = new MultiThreadedServer(PORT_WORK);
        new Thread(server).start();
        // System.out.println("Stopping Server");
        //server.stop();
    }
}