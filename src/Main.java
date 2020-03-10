import java.util.Arrays;
import java.util.Date;
import java.util.stream.DoubleStream;

import GUI.MainWindow;
import ricardo_crawlos.crawlers.BaseURLConstrainedClawer;
import ricardo_crawlos.crawlers.TraversalCrawlerBase;
import ricardo_crawlos.models.CriticReview;
import ricardo_crawlos.storage.JsonSerialiser;
import ricardo_crawlos.storage.TextWriter;

public class Main
{
    public static void main(String[] args)
    {

       ShowWindow();
    }

    public static void ShowWindow()
    {
        MainWindow dialog = new MainWindow();
        dialog.setSize(1280, 720);
        dialog.setVisible(true);
        System.exit(0);
    }
}
