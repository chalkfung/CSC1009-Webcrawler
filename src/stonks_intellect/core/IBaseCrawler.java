package stonks_intellect.core;

/**
 * IBaseCrawler
 */
public interface IBaseCrawler
{
    void traverse(String url);
    String[] getLinks();
}