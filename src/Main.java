import java.awt.*;

import GUI.MainReviewFrame;
import ricardo_crawlos.core.IExtractableCrawler;
import ricardo_crawlos.core.IReviewsExtractor;
import ricardo_crawlos.crawlers.GamespotReviewsCrawler;
import ricardo_crawlos.crawlers.MetacriticCriticReviewsCrawler;
import ricardo_crawlos.crawlers.MetacriticUserReviewsCrawler;
import ricardo_crawlos.extractors.GamespotReviewsExtractor;
import ricardo_crawlos.extractors.MetacriticCriticReviewsExtractor;
import ricardo_crawlos.extractors.MetacriticUserReviewsExtractor;
import ricardo_crawlos.storage.CachedGamesiteCrawler;
import ricardo_crawlos.storage.JsonSerialiser;
import ricardo_crawlos.storage.TextWriter;

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
