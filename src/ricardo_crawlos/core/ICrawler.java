package ricardo_crawlos.core;

import org.jsoup.nodes.Document;

/**
 * IBaseCrawler
 */
public interface ICrawler extends Runnable
{
    String[] getTraversableLinks();
    String[] getTraversedLinks();
    Document[] getTraversalResults();
}