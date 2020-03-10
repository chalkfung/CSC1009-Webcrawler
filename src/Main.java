import java.util.Arrays;
import java.util.Date;
import java.util.stream.DoubleStream;

import ricardo_crawlos.crawlers.BaseURLConstrainedClawer;
import ricardo_crawlos.crawlers.TraversalCrawlerBase;

public class Main
{
    public static void main(String[] args)
    {
        BaseURLConstrainedClawer clawer = new BaseURLConstrainedClawer("https://www.gamespot.com/dota-2/reviews/");

        clawer.traverse("https://www.gamespot.com/dota-2/reviews/?page=1");

        System.out.println(clawer);
        
        Arrays.asList(0.0).stream().mapToDouble(x -> x).reduce(0, (x,y) -> x);
    
    }
}
