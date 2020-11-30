import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MultiThreadedServer implements Runnable{
    protected static int amountConnections = 0;  //Количество подключений
    protected int serverPort = 3000;
    protected ServerSocket serverSocket = null;
    protected boolean isStopped = false;

    public MultiThreadedServer(int port){
        this.serverPort = port;
    }

    @Override
    public void run(){
        openServerSocket();
        while(! isStopped()){
            Socket clientSocket = null;
            try {
                amountConnections++;
                clientSocket = this.serverSocket.accept();
                System.out.println("Клиент " + amountConnections + " успешно подключен");
            } catch (IOException e) {
                if(isStopped()) {
                    System.out.println("Сервер остановлен.") ;
                    return;
                }
                throw new RuntimeException("Ошибка подключения клиентского соединения", e);
            }
            new Thread(new ServerHandler(clientSocket)).start();
        }
        System.out.println("Сервер остановлен") ;
    }


    private synchronized boolean isStopped() {
        return this.isStopped;
    }

    public synchronized void stop(){
        this.isStopped = true;
        try {
            this.serverSocket.close();
        } catch (IOException e) {
            throw new RuntimeException("Ошибка закрытия сервера", e);
        }
    }

    private void openServerSocket() {
        System.out.println("Сервер успешно запущен");
        try {
            this.serverSocket = new ServerSocket(this.serverPort);
        } catch (IOException e) {
            throw new RuntimeException("Невозможно открыть порт " + this.serverPort, e);
        }
    }
}