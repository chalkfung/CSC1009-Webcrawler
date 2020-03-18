package ricardo_crawlos.crawlers;

import javax.xml.stream.FactoryConfigurationError;
import org.jsoup.Jsoup;

import ricardo_crawlos.core.IPaginatedCrawler;
import ricardo_crawlos.core.IWebsite;

public class MetacriticUserReviewsCrawler extends BaseURLConstrainedCrawler implements IPaginatedCrawler
{
    protected String gamePath;

    private static String getUrl(String gamePath, String subPath)
    {
        return "https://www.metacritic.com/" + gamePath + "/user-reviews" + subPath;
    }

    public MetacriticUserReviewsCrawler(String theGamePath)
    {
        super(getUrl(theGamePath, ""));
        this.gamePath = theGamePath;
        this.links.add(this.baseUrl);
    }

    @Override
    public void run()
    {
        traverse(this.baseUrl + "?sort-by=date&num_items=30&page=0");
    }

    @Override
    protected boolean canTraverse(String url)
    {
        if (url.contains("user-reviews?sort-by=date&num_items=30"))
        {
            return super.canTraverse(url);
        }
        else
        {
            return false;
        }
    }

    @Override
    public IWebsite getWebsiteInfo()
    {
        return Websites.getMetacritic();
    }

    @Override
    public String getDomain()
    {
        return "metacritic";
    }

    @Override
    public String getExtractionName()
    {
        return "user-reviews";
    }

    @Override
    public String fromSubpage(String subPath)
    {
        return getUrl(gamePath, subPath);
    }

    @Override
    public String extractFrom(String html)
    {
        return Jsoup.parse(html).select("ol.reviews.user_reviews").outerHtml();
    }

    @Override
    public int getPageCount()
    {
        return this.links.size() - 1;
    }
}
