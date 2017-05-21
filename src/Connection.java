/**
 * Created by Rafał on 21.05.2017.
 */

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;


public class Connection extends Thread{
    private ServerSocket serverSocket;
    private int port;
    private boolean online;
    /**
     *
     * @param port
     */
    public Connection(int port) {
        this.port = port;
        online = true;
        start();
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            Gui.gui.addMessage("Server ready to work on port: " + port,
                    0, true);
        } catch (IOException e) {
            Gui.gui.addMessage("Port " + port + " is already in use", 3, true);
            Gui.gui.setEnabled();
            return;
        } catch (IllegalArgumentException e) {
            Gui.gui.addMessage("Invalid port number", 3, true);
            Gui.gui.setEnabled();
            return;
        }

        while (online) {
            try {
                new Handler(serverSocket.accept());
            } catch (SocketException e) {
            } catch (IOException e) {
                Gui.gui.addMessage("Server error", 3, true);
            }
        }
    }

    public void interrupt() {
        try {
            online = false;
            serverSocket.close();
        } catch (IOException e) {
        }
    }
}
