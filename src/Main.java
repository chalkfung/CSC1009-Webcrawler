import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import GUI.MainReviewFrame;

public class Main
{
    public static void main(String[] args) throws IOException
    {
        showWindow();
    }

    public static void showWindow()
    {
        EventQueue.invokeLater(() ->
        {
            try
            {
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
                MainReviewFrame frame = new MainReviewFrame();
                frame.setVisible(true);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        });
    }
}


