package gui;

import java.awt.BorderLayout;
import java.beans.PropertyVetoException;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import backup.BackupObj;
import gradient.Circle;
import gradient.Circle2;
import gradient.Oval;
import gradient.GradientVisualizer;

public class GradientWindow extends JInternalFrame implements BackupObj {
    private final GradientVisualizer m_visualizer;

    public GradientWindow()
    {
        super("Градиентное_поле", true, true, true, true);
        m_visualizer = new GradientVisualizer(new Circle2(50, 200, 350, 200), 0.1);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        getContentPane().add(panel);
        pack();
    }

    @Override
    public String toBackupString() {
        System.out.println(this.toString());
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s %d %d %d %d %b %b",
                getTitle(), getWidth(), getHeight(), getX(), getY(), isClosed(), isIcon()));
        if (isIcon()) {
            JDesktopIcon icon = getDesktopIcon();
            builder.append(String.format(" %d %d %d %d",
                    icon.getWidth(), icon.getHeight(), icon.getX(), icon.getY()));
        }
        return builder.toString();
    }

    @Override
    public void fromBackupString(String backup) {
        String[] tokens = backup.split(" ");
        setTitle(tokens[0]);
        setSize(Integer.parseInt(tokens[1]), Integer.parseInt(tokens[2]));
        setLocation(Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
        if (Boolean.parseBoolean(tokens[5])) {
            dispose();
        }
        try {
            setIcon(Boolean.parseBoolean(tokens[6]));
        } catch (PropertyVetoException e) {
            e.printStackTrace();
        }
        if (isIcon()) {
            getDesktopIcon().setSize(Integer.parseInt(tokens[7]), Integer.parseInt(tokens[8]));
            getDesktopIcon().setLocation(Integer.parseInt(tokens[8]), Integer.parseInt(tokens[9]));
            setSize(getMinimumSize());
        }
    }
}
