package ricardo_crawlos.core;

/**
 * IBaseCrawler
 */
public interface ICrawler
{
    void traverse(String url);
    String[] getLinks();
}