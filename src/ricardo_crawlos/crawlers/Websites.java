package ricardo_crawlos.crawlers;

import ricardo_crawlos.core.IWebsite;

/**
 * Info of websites implemented to crawl 
 */
public class Websites
{
    private static IWebsite gamespot = new IWebsite()
    {
        @Override
        public String getDomain()
        {
            return "gamespot";
        }

        @Override
        public String getBaseUrl()
        {
            return "https://www.gamespot.com/";
        }
    };
    private static IWebsite metacritic = new IWebsite()
    {
        @Override
        public String getDomain()
        {
            return "metacritic";
        }

        @Override
        public String getBaseUrl()
        {
            return "https://www.metacritic.com/";
        }
    };

    /**
     * @return the gamespot
     */
    public static IWebsite getGamespot()
    {
        return gamespot;
    }

    /**
     * @return the metacritic
     */
    public static IWebsite getMetacritic()
    {
        return metacritic;
    }
}
