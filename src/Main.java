import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.DoubleStream;

import GUI.MainWindow;
import ricardo_crawlos.core.ICrawler;
import ricardo_crawlos.core.IReview;
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
        
        ICrawler clawler = new GameSpotReviewsClawer("dota-2");
        clawler.run();

        String[] cacheOutput = Arrays.stream(clawler.getTraversalResults()).map(x -> x.html()).toArray(String[]::new);

        TextWriter.writeAllText("database/cache/gamespot/dota-2/reviewPageCache" + ".json", JsonSerialiser.DefaultInstance().toJson(
                cacheOutput));

       // ShowWindow();
        /*
        List<ReviewBase> rbList = new ArrayList<>();
        ReviewBase rb1 = new ReviewBase(0, 9, "Pinoy doto bad doto", new Date(), "Sinkie");
        ReviewBase rb2 = new ReviewBase(0, 10, "Pinoy doto best doto", new Date(), "Trash");
        ReviewBase rb3 = new ReviewBase(0, 6, "Pinoy doto suck doto", new Date(), "Sinkie1");
        ReviewBase rb4 = new ReviewBase(0, 8, "Pinoy doto lame doto", new Date(), "Sinkie2");
        ReviewBase rb5 = new ReviewBase(0, 10, "Pinoy doto win doto", new Date(), "Dumpster");
        ReviewBase rb6 = new ReviewBase(0, 0, "Pinoy doto lose doto", new Date(), "Sinkie3");
        ReviewBase rb7 = new ReviewBase(0, 8, "Pinoy doto lame doto", new Date(), "Sinkie2");
        ReviewBase rb8 = new ReviewBase(0, 8, "Pinoy doto lame doto", new Date(), "Sinkie2");
        rbList.add(rb1);
        rbList.add(rb2);
        rbList.add(rb3);
        rbList.add(rb4);
        rbList.add(rb5);
        rbList.add(rb6);
        rbList.add(rb7);
        rbList.add(rb8);
        AnalyserBase<ReviewBase> rbAnal = new AnalyserBase<>();
        Statistics<Double,ReviewBase> stats1 = rbAnal.Analyse(rbList);
        System.out.println(stats1.getMean());
        for(IReview elem : stats1.getNonOutliers())
        {
            System.out.println(elem.getScore());
        }

         */
    }
}
