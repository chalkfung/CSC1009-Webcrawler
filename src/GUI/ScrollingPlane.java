package GUI;

import java.awt.*;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import javax.swing.*;

/**
 * This class is a utility to make JOptionPane resizable
 */
public class ScrollingPlane
{
    private final int cutoff = 10000;
    private String source;
    private String output;
    private int x;
    private int y;
    private boolean edit;

    public ScrollingPlane(String output, int x, int y, Boolean edit)
    {

        this.output = this.source = output;
        this.x = x;
        this.y = y;
        this.edit = edit;
    }

    public static ScrollingPlane createStatic(String output, int x, int y)
    {
        return new ScrollingPlane(output, x, y, false);
    }

    public void showWindow(String title, JFrame jFrame)
    {
        JButton loadMore = new JButton("Load All");
        Object[] buttons = new Object[]{ "OK", loadMore };

        loadMore.addActionListener(x ->
        {
            output = source;

            var result = JOptionPane.showOptionDialog(
                    null,
                    "This operation is very resource intensive",
                    "Warning",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    new Object[] {"Load", "Cancel"},
                    null
            );

            if (result == JOptionPane.OK_OPTION)
            {
                GameReviewHomePanel.loaderAction(jFrame, (dialog, lable) -> startLoading(dialog, lable, title));
            }
        });

        if (output.length() < cutoff)
        {
            loadMore.setEnabled(false);
        }
        else
        {
            output = output.substring(0, cutoff);
        }

        JOptionPane.showOptionDialog(
                null,
                new Object[]{ getPlane() },
                title,
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                buttons,
                null
        );
    }

    private JScrollPane getPlane()
    {
        JScrollPane scrollPane = new JScrollPane(new JLabel(output));
        scrollPane.setPreferredSize(new Dimension(x, y));

        JTextArea textArea = new JTextArea(output);
        textArea.setWrapStyleWord(true);
        textArea.setLineWrap(true);
        textArea.setEditable(edit);
        textArea.setBackground(UIManager.getColor("Label.background"));
        textArea.setFont(UIManager.getFont("Label.font"));
        textArea.setBorder(UIManager.getBorder("Label.border"));

        scrollPane.getViewport().setView(textArea);

        scrollPane.addHierarchyListener(new HierarchyListener()
        {
            public void hierarchyChanged(HierarchyEvent e)
            {
                Window window = SwingUtilities.getWindowAncestor(scrollPane);
                if (window instanceof Dialog)
                {
                    Dialog dialog = (Dialog) window;
                    if (!dialog.isResizable())
                    {
                        dialog.setResizable(true);
                    }
                }
            }
        });
        return scrollPane;
    }

    public SwingWorker<String, Void> startLoading(JDialog load, JLabel gifLabel, String title)
    {
        gifLabel.setText("Loading");
        return new SwingWorker<String, Void>()
        {
            JScrollPane plane;
            @Override
            protected String doInBackground() throws InterruptedException
            {
                System.out.println("Getting");
                JOptionPane.getRootFrame().dispose();
                plane = getPlane();
                System.out.println("Gotten");
                return null;
            }

            @Override
            protected void done()
            {
                load.dispose();

                JOptionPane.showOptionDialog(
                        null,
                        new Object[]{ plane },
                        title,
                        JOptionPane.DEFAULT_OPTION,
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        new Object[] {"Ok"},
                        null
                );
            }
        };
    }
}
