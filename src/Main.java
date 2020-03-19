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


