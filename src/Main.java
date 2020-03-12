import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.DoubleStream;

import GUI.MainWindow;
import ricardo_crawlos.core.ICrawler;
import ricardo_crawlos.crawlers.BaseURLConstrainedClawer;
import ricardo_crawlos.crawlers.GameSpotReviewsClawer;
import ricardo_crawlos.crawlers.TraversalCrawlerBase;
import ricardo_crawlos.models.CriticReview;
import ricardo_crawlos.models.ReviewBase;
import ricardo_crawlos.storage.JsonSerialiser;
import ricardo_crawlos.storage.TextWriter;
import ricardo_crawlos.utilities.*;

public class Main
{
    public static void main(String[] args)
    {
        /*
        ICrawler clawler = new GameSpotReviewsClawer("dota-2");
        clawler.run();

        String[] cacheOutput = Arrays.stream(clawler.getTraversalResults()).map(x -> x.html()).toArray(String[]::new);

        TextWriter.writeAllText("database/cache/gamespot/dota-2/reviewPageCache" + ".json", JsonSerialiser.DefaultInstance().toJson(
                cacheOutput));
         */
       // ShowWindow();
        List<ReviewBase> rbList = new ArrayList<>();
        ReviewBase rb1 = new ReviewBase(0, 9, "Pinoy doto bad doto", new Date(), "Sinkie");
        ReviewBase rb2 = new ReviewBase(0, 10, "Pinoy doto best doto", new Date(), "Trash");
        ReviewBase rb3 = new ReviewBase(0, 6, "Pinoy doto suck doto", new Date(), "Sinkie1");
        ReviewBase rb4 = new ReviewBase(0, 8, "Pinoy doto lame doto", new Date(), "Sinkie2");
        ReviewBase rb5 = new ReviewBase(0, 10, "Pinoy doto win doto", new Date(), "Dumpster");
        ReviewBase rb6 = new ReviewBase(0, 0, "Pinoy doto lose doto", new Date(), "Sinkie3");
        rbList.add(rb1);
        rbList.add(rb2);
        rbList.add(rb3);
        rbList.add(rb4);
        rbList.add(rb5);
        rbList.add(rb6);
        AnalyserBase<ReviewBase> rbAnal = new AnalyserBase<>();
        System.out.println(rbAnal.getMax(rbList));
        System.out.println(rbAnal.getQ3(rbList));
        System.out.println(rbAnal.getQ1(rbList));
        System.out.println(rbAnal.getIQR(rbList));
        System.out.println(rbAnal.getMean(rbList));
        System.out.println(rbAnal.getVariance(rbList));
        System.out.println(rbAnal.getStandardDeviation(rbList));
        for (ReviewBase elem : rbAnal.removeOutliers(rbList)
        )
        {
            System.out.println(elem.getComments());
        }
        System.out.println("Printing outliers...");
        for (ReviewBase elem : rbAnal.showOutliers(rbList)
             )
        {
          System.out.println(elem.getComments());
        }

    }
}
