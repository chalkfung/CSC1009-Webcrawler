package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is a utility to make JOptionPane resizable
 */
public class ScrollingPlane
{
    private final int cutoff = 100;
    private List<String> source;
    private String output;
    private int x;
    private int y;
    private boolean edit;

    JScrollPane plane;

    public void setPlane(JScrollPane plane)
    {
        this.plane = plane;
    }

    public ScrollingPlane(List<String> source, int x, int y, Boolean edit)
    {
        this.output = "";
        this.source = source;
        this.x = x;
        this.y = y;
        this.edit = edit;
    }

    public static ScrollingPlane createStatic(List<String> output, int x, int y)
    {
        return new ScrollingPlane(output, x, y, false);
    }

    public void showWindow(String title, JFrame jFrame)
    {
        var buttons = new ArrayList<>();
        buttons.add("Close");

        if (source.size() > cutoff)
        {
            output = source.stream().limit(cutoff).collect(Collectors.joining("\n\n"));
            var loadMore = new JButton("Load All");
            buttons.add(loadMore);
            loadMore.addActionListener(x ->
            {
                var result = JOptionPane.showOptionDialog(
                        null,
                        "This operation is very resource intensive. Continue?",
                        "Warning",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        new Object[]{ "Load", "Cancel" },
                        null
                );

                if (result == JOptionPane.OK_OPTION)
                {
                    output = String.join("\n\n", source);

                    SimpleLoaderManager.StartLoadingInstance(jFrame, () ->
                            {
                                JOptionPane.getRootFrame().dispose();
                                setPlane(getPlane());
                                return null;
                            },
                            () ->
                            {
                                JOptionPane.showOptionDialog(
                                        null,
                                        new Object[]{ plane },
                                        title + " " + source.size() + " Entries",
                                        JOptionPane.DEFAULT_OPTION,
                                        JOptionPane.PLAIN_MESSAGE,
                                        null,
                                        new Object[]{ "Close" },
                                        null
                                );
                            }
                    );
                }
            });
        }
        else
        {
            output = String.join("\n\n", source);
        }

        JOptionPane.showOptionDialog(
                null,
                new Object[]{ getPlane() },
                title + " " + (Math.min(source.size(), cutoff)) + " of " + source.size() + " Entries",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                buttons.toArray(),
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

        scrollPane.addHierarchyListener(e ->
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
        });
        return scrollPane;
    }
}
