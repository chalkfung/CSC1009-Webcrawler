import java.util.Arrays;
import java.util.Date;
import java.util.stream.DoubleStream;

import ricardo_crawlos.crawlers.BaseURLConstrainedClawer;
import ricardo_crawlos.crawlers.TraversalCrawlerBase;
import ricardo_crawlos.models.CriticReview;
import ricardo_crawlos.storage.JsonSerialiser;
import ricardo_crawlos.storage.TextWriter;

public class Main
{
    public static void main(String[] args)
    {
        CriticReview criticReview = new CriticReview(1, 1, "test", new Date(), "www", "test1");

        TextWriter.writeAllText("testfolder/reviewtest.json", JsonSerialiser.DefaultInstance().toJson(criticReview));
    }
}
