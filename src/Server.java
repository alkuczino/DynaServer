import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class Server extends JFrame{

    public Server() {
    super("DynaBlaster Server");

    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    Gui gui = new Gui();
    setPreferredSize(new Dimension(500,300));
    add(gui);
}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Server server = new Server();
                server.setVisible(true);
                server.pack();
                Dimension dm = server.getToolkit().getScreenSize();
                server.setLocation(
                        (int) (dm.getWidth() / 8 - server.getWidth() / 8),
                        (int) (dm.getHeight() / 4 - server.getHeight() / 4));
            }
        });
    }
}
