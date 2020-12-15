package Decorator;


import ServerHandlers.ServerHandler;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class PortAddress extends ConnectionParametersDecorator {

    static private Socket clientSocket;

    public PortAddress(ConnectionParameters connectionParameters) {
        super(connectionParameters);
    }

    @Override
    public String decorate() {
        return super.decorate() + decorateConnectionParameters();
    }

    public String decorateConnectionParameters() {
        clientSocket = ServerHandler.getClientSocket();

        return (". Параметры подключения: " + clientSocket.getLocalPort() + ", " + clientSocket.getInetAddress());

    }

}
