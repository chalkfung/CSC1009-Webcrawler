import java.awt.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

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
