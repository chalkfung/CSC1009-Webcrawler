import stonks_intellect.*;
import stonks_intellect.core.*;

public class Main
{
    public static void main(String[] args)
    {
        IBaseCrawler crawler = new BaseCrawler();
        crawler.getPageLinks("https://sites.google.com/view/loopohkok/home");
    }
}
