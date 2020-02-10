package ricardo_crawlos.core;

/**
 * IBaseCrawler
 */
public interface ICrawler
{
    void traverse(String url);
    void run();
    String[] getLinks();
}