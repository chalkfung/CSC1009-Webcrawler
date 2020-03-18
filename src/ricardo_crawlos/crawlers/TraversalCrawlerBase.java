package ricardo_crawlos.crawlers;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import ricardo_crawlos.core.ICrawler;

/**
 * BaseCrawler
 */
public abstract class TraversalCrawlerBase implements ICrawler
{
    protected final Set<String> links;
    protected final Queue<Document> traversalResults;

    public TraversalCrawlerBase()
    {
        links = Collections.newSetFromMap(new ConcurrentHashMap<>());
        traversalResults = new ConcurrentLinkedQueue<>();
    }

    protected abstract boolean canTraverse(String url);

    public String[] getTraversableLinks(Document document)
    {
        Elements linksOnPage = document.select("a[href]");
        return linksOnPage
                .stream()
                .map(x -> x.attr("abs:href"))
                .filter(this::canTraverse)
                .distinct()
                .toArray(String[]::new);
    }

    /**
     * Note: recursive
     *
     * @param url The url to the page to look for more urls
     */
    protected void traverse(String url)
    {
        if (links.add(url))
        {
            System.out.println("Traversing: " + url);
            try
            {
                Document document = Jsoup.connect(url).get();

                traversalResults.add(document);

                document.select("a[href]")
                        .stream()
                        .parallel()
                        .map(x -> x.attr("abs:href"))
                        .filter(this::canTraverse)
                        .forEach(this::traverse);
            }
            catch (IOException e)
            {
                System.err.println("For '" + url + "': " + e.getMessage());
            }
        }
    }

    @Override
    public String[] getTraversedLinks()
    {
        return links.toArray(String[]::new);
    }

    @Override
    public Document[] getTraversalResults()
    {
        return traversalResults.toArray(Document[]::new);
    }

    @Override
    public String toString()
    {
        return "Links traversed: " + links.size() + "\nAll links:\n\t" + String.join("\n\t", getTraversedLinks());
    }
}