import java.awt.*;
import java.util.Arrays;
import java.util.Date;
import java.util.stream.DoubleStream;

import GUI.*;
import ricardo_crawlos.core.ICrawler;
import ricardo_crawlos.crawlers.BaseURLConstrainedClawer;
import ricardo_crawlos.crawlers.GameSpotReviewsClawer;
import ricardo_crawlos.crawlers.TraversalCrawlerBase;
import ricardo_crawlos.models.CriticReview;
import ricardo_crawlos.storage.JsonSerialiser;
import ricardo_crawlos.storage.TextWriter;

import java.awt.EventQueue;

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
