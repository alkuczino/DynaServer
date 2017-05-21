import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.util.Date;

/**
 * Created by Rafał on 21.05.2017.
 */
public class Gui extends JPanel {

    public static Gui gui;
    private JPanel info;
    private JButton start;
    private JTextField port;
    private JScrollPane scroll;
    private boolean online;
    private Connection connection;

    /**
     *
     */
    public Gui() {

        gui = this;
        info = new JPanel();
        JPanel button = new JPanel();
        JPanel north = new JPanel();
        JPanel south = new JPanel();
        JPanel port = new JPanel();
        start = new JButton("Start");
        JButton exit = new JButton("Exit");
        JLabel ip;
        JLabel portText = new JLabel("Port: ");
        JLabel ipText = new JLabel ("IP: ");
        try {
            ip = new JLabel("   Server IP:   "
                    + InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            addMessage("IP not found",3, true);
            ip = new JLabel("   Server IP: unknown");
        }
        this.port = new JTextField(Integer.toString(10001));
        scroll = new JScrollPane(info);
        online = false;

        setLayout(new BorderLayout());
        south.setLayout(new BorderLayout());
        north.setLayout(new BorderLayout());
        info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
        start.setFocusable(false);
        exit.setFocusable(false);

        start.setPreferredSize(new Dimension(80,25));
        exit.setPreferredSize(new Dimension(80,25));
        this.port.setPreferredSize(new Dimension(0, 25));
        this.port.setColumns(7);
        this.port.setSize(new Dimension(0, 25));

        button.add(exit);
        button.add(start);
        ip.add(ipText);
        port.add(portText);
        port.add(this.port);

        south.add(button, BorderLayout.EAST);
        north.add(ip, BorderLayout.WEST);
        north.add(port, BorderLayout.EAST);
        add(scroll, BorderLayout.CENTER);
        add(south, BorderLayout.SOUTH);
        add(north, BorderLayout.NORTH);

        exit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                System.exit(0);
            }
        });

        start.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {

                if (online) {
                    connection.interrupt();
                    setEnabled();
                } else {
                    try {
                        int port = Integer.parseInt(Gui.this.port.getText());
                        start.setText("Stop");
                        Gui.this.port.setEnabled(false);
                        online = true;
                        try {
                            connection = new Connection(port);
                        } catch (Exception e) {
                        }
                    } catch (NumberFormatException e) {
                        addMessage("Incorrect port numebr", 3, true);
                    }
                }
            }
        });

        info.addContainerListener(new ContainerListener() {
            public void componentAdded(ContainerEvent e) {
                scroll.getVerticalScrollBar().setValue(
                        scroll.getVerticalScrollBar().getMaximum());
            }

            public void componentRemoved(ContainerEvent e) {
            }
        });
    }

    /**
     *
     * @param message
     * @param color
     * @param time
     */
    public void addMessage(String message, int color, boolean time) {

        JLabel label;
        if (time) {
            label = new JLabel(" ["
                    + DateFormat.getDateTimeInstance().format(new Date())
                    + "] " + message);
        } else {
            label = new JLabel(message);
        }
        info.add(label);
        info.revalidate();
    }

    /**
     *
     */
    public void setEnabled() {
        start.setText("Start");
        port.setEnabled(true);
        online = false;
    }
}

