import java.util.Arrays;
import java.util.Date;
import java.util.stream.DoubleStream;

import org.jsoup.nodes.Document;

import GUI.MainWindow;
import ricardo_crawlos.core.ICrawler;
import ricardo_crawlos.crawlers.BaseURLConstrainedClawer;
import ricardo_crawlos.crawlers.GameSpotReviewsClawer;
import ricardo_crawlos.crawlers.TraversalCrawlerBase;

public class Main
{
    public static void main(String[] args)
    {
        // ICrawler clawler = new GameSpotReviewsClawer("dota-2");
        // clawler.run();

        // String[] cacheOutput = Arrays.stream(clawler.getTraversalResults()).map(x -> x.html()).toArray(String[]::new);

        // TextWriter.writeAllText("database/cache/gamespot/dota-2/reviewPageCache" + ".json", JsonSerialiser.DefaultInstance().toJson(
        //         cacheOutput));
       // ShowWindow();
    }
}
