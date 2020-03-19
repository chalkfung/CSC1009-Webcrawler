package GUI;

import java.awt.Dialog;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.HierarchyEvent;
import java.awt.event.HierarchyListener;
import javax.swing.*;


public class ScrollingPlane
{
    //This classs is to make JOptionPane resizable

    private String output;
    private int x;
    private int y;
    private boolean edit;

    public ScrollingPlane(String output, int x, int y, Boolean edit)
    {

        this.output = output;
        this.x = x;
        this.y = y;
        this.edit = edit;
    }

    public void setoutput(String newOutput)
    {
        output = newOutput;
    }

    public static ScrollingPlane createStatic(String output, int x, int y)
    {
        return new ScrollingPlane(output, x, y, false);
    }

    public void showWindow(String title)
    {
        String[] button = new String[]{ "OK" };
        JOptionPane.showOptionDialog(null, new Object[]{
                getPlane()
        }, title, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, button, null);
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

}
