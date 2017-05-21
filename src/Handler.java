import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.io.BufferedReader;

/**
 * Created by Rafał on 21.05.2017.
 */
public class Handler extends Thread{

    static String number="1";

    private Socket socket;
    private BufferedReader br;
    private PrintWriter pw;

    Handler(Socket socket) throws IOException {

        this.socket = socket;
        InputStream is = socket.getInputStream();
        br = new BufferedReader(new InputStreamReader(is));
        OutputStream os = socket.getOutputStream();
        pw = new PrintWriter(os, true);
        start();
    }

    public void run() {

        try {
            String command = br.readLine();
            Gui.gui.addMessage("From "
                    + socket.getInetAddress().getHostAddress() + ": "
                    + command, 2, true);

            switch (command) {

                case "GETLEVELNUMBER": {


                    try{
                        socket.setSoTimeout(5000);
                        String level = br.readLine();
                        Gui.gui.addMessage( "From "
                                + socket.getInetAddress().getHostAddress() + ": "
                                + level, 2, true);
                        socket.setSoTimeout(0);
                        number =level;
                    } catch (SocketTimeoutException e) {
                        socket.setSoTimeout(0);
                        Gui.gui.addMessage("Socket timeout", 3,
                                true);
                    }
                    socket.close();
                    break;
                }


                case "GETHIGHSCORES": {
/**
 * Tu wczytać hajskory
 */
                    String[] answer=Configuration.getHighScores();

                    if (answer == null) {
                        pw.println("ERROR");
                        Gui.gui.addMessage("Cannot read highscores from file", 3,
                                true);
                        Gui.gui.addMessage( "To "
                                + socket.getInetAddress().getHostAddress() + ": "
                                + "ERROR", 1, true);
                        socket.close();
                        break;
                    }
                    Gui.gui.addMessage("Sending high scores", 1, true);
                    for (int i = 0; i < 10; i++) {
                        pw.println(answer[i]);
                        Gui.gui.addMessage("To "
                                + socket.getInetAddress().getHostAddress() + ": "
                                + answer[i], 1, true);
                    }
                    socket.close();
                    break;
                }
                case "GETDIFFICULTY":{
                    String diff = null;
                    Gui.gui.addMessage("Waiting for difficulty",1,true);
                    socket.setSoTimeout(0);
                    diff=br.readLine();
                    if (diff == null) {
                        pw.println("Error");
                        Gui.gui.addMessage("To "
                                + socket.getInetAddress().getHostAddress() + ": "
                                + "Error", 1, true);
                        socket.close();
                        break;
                    }
                    else if (diff.equals("easy"))
                    {
                        pw.println(120);
                        pw.println(3);
                    }
                    else if (diff.equals("medium"))
                    {
                        pw.println(90);
                        pw.println(2);
                    }
                    else if (diff.equals("hard"))
                    {
                        pw.println(60);
                        pw.println(1);
                    }

                    Gui.gui.addMessage("Difficulty sent", 2, true);
                    socket.close();
                    break;
                }
                case "GETMAPSIZE":{
                    Gui.gui.addMessage("Sending map size",1,true);
                    socket.setSoTimeout(0);

                    int mapsize=Configuration.getMapSize(number);
                    if (mapsize == 0) {
                        pw.println("Error");
                        Gui.gui.addMessage("To "
                                + socket.getInetAddress().getHostAddress() + ": "
                                + 3, 1, true);
                        socket.close();
                        break;
                    }
                    pw.println(mapsize);



                }
                case "GETLEVEL": {
                    int[][] answer = null;
                    Gui.gui.addMessage("Waiting for level parameters ", 1, true);
                    socket.setSoTimeout(0);
                    /**
                     * Tu wczytać numer levelu
                     */
                    answer = Configuration.getLevel(number);
                    if (answer == null) {
                        pw.println("Error");
                        Gui.gui.addMessage("To "
                                + socket.getInetAddress().getHostAddress() + ": "
                                + "Error", 1, true);
                        socket.close();
                        break;
                    }

                    for (int i = 0; i < answer.length; i++)
                    {   for(int j=0;j<answer.length;j++)
                        pw.println(answer[i][j]);
                        Gui.gui.addMessage("To "
                                + socket.getInetAddress().getHostAddress() + ": "
                                + answer[i], 1, true);
                    }
                    Gui.gui.addMessage("Level parameters sent", 2, true);
                    socket.close();
                    break;
                }
                default:
                    pw.println("ERROR");
                    Gui.gui.addMessage("Unknown command", 3, true);
                    Gui.gui.addMessage( "To "
                            + socket.getInetAddress().getHostAddress() + ": "
                            + 3, 1, true);
                    socket.close();
                    break;
            }
            socket.close();

        } catch (IOException e) {
            Gui.gui.addMessage("Input/output exception ", 3, true);
        }
    }

}
