import ricardo_crawlos.*;
import ricardo_crawlos.core.*;

public class Main
{
    public static void main(String[] args)
    {
        ICrawler crawler = new BaseCrawler();
        crawler.traverse("https://sites.google.com/view/loopohkok/home");
        System.out.println(crawler);
    }
}
