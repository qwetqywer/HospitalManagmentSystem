package ClientHandlers;

import Configs.ServerConnectionConfigs;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientHandler extends ServerConnectionConfigs {
    private Socket clientSocket;
    private ObjectOutputStream respond;
    private ObjectInputStream request;

    private String message;
    private static ClientHandler client;

    private ClientHandler(){
        try {
            clientSocket = new Socket(ServerConnectionConfigs.ipAddress,
                    Integer.parseInt(ServerConnectionConfigs.port));
            respond = new ObjectOutputStream(clientSocket.getOutputStream());
            request = new ObjectInputStream(clientSocket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ClientHandler getClient(){
        if(client == null)
        {
            client = new ClientHandler();
        }
        return client;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setRequest(ObjectInputStream request) {
        this.request = request;
    }

    public void setRespond(ObjectOutputStream respond) {
        this.respond = respond;
    }

    public void setClientSocket(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void sendMessage(String message){
        try {

            respond.writeObject(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendObject(Object object){
        try {
            respond.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readMessage() throws IOException {
        try {

            message = (String) request.readObject();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }

        return message;
    }

    public int read() throws IOException {
        return request.read();
    }

    public Object readObject(){
        Object object = new Object();
        try {
            object = request.readObject();
        } catch (ClassNotFoundException | IOException e) {

            e.printStackTrace();
        }
        return object;
    }

    public void close() {
        try {
            clientSocket.close();
            //outStream.flush();
            request.close();
            respond.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
