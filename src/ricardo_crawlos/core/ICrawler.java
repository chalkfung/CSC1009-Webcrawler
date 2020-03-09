package ricardo_crawlos.core;

/**
 * IBaseCrawler
 */
public interface ICrawler
{
    void run();
    String[] getLinks();
}