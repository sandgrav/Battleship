package Controller;

import java.io.IOException;

public class GameController {
    ConnectionType connectionType;
    String ipAdress;
    int port;

    public String getIpAdress() {
        return ipAdress;
    }

    public void setIpAdress(String ipAdress) {
        this.ipAdress = ipAdress;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    Client client;
    Server server;

    String[] strings = new String[]{"i shot 6c", "h shot 7b", "m shot 6g", "s shot 5f", "game over"};

    public ConnectionType getConnectionType() {
        return connectionType;
    }

    public void setConnectionType(ConnectionType connectionType) {
        this.connectionType = connectionType;
    }

    public Runnable getStartClient() {
        return startClient;
    }

    public Runnable getStartServer() {
        return startServer;
    }

    Runnable startClient = () -> {
        String string;
        int j = 0;
        client = new Client(ipAdress, port) ;
        try {
            while (true) {
                client.getWriter().println(strings[j]);
                if (strings[j].equalsIgnoreCase("game over")) {
                    System.out.println("game over");
                    break;
                }
                string = client.getReader().readLine();
                if (string.equalsIgnoreCase("game over")) {
                    System.out.println("game over");
                    break;
                }
                else
                    System.out.println(string);
                j+=2;
            }
        } catch (IOException e) {
            e.getMessage();
        }
    };

    Runnable startServer = () -> {
        String string;
        int j = 1;
        server = new Server(port);
        try {
            while (true) {
                string = server.getReader().readLine();
                System.out.println(string);
                if (string.equalsIgnoreCase("game over")) {
                    break;
                }
                server.getWriter().println(strings[j]);
                if (strings[j].equalsIgnoreCase("game over")) {
                    System.out.println("game over");
                    break;
                }
                j+=2;
            }
        } catch (IOException e) {
            e.getMessage();
        }
    };

}

