import stonks_intellect.*;

public class Main
{
    public static void main(String[] args)
    {
        BaseCrawler crawler = new BaseCrawler();
        crawler.getPageLinks("https://sites.google.com/view/loopohkok/home");
    }
}
