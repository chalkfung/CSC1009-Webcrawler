package ricardo_crawlos.core;

/**
 * IGameSearcher
 * Gets the path of the game for a website
 */
public interface IGameSearcher
{
    IWebsite getWebsiteInfo();

    String getPath();
}
