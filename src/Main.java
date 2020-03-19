import javax.swing.*;
import java.awt.*;

import GUI.MainReviewFrame;

public class Main
{
    public static void main(String[] args)
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


