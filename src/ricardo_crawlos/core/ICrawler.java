package ricardo_crawlos.core;

import org.jsoup.nodes.Document;

/**
 * IBaseCrawler
 */
public interface ICrawler
{
    void run();
    String[] getTraversableLinks();

    String[] getTraversedLinks();

    Document[] getTraversalResults();
}