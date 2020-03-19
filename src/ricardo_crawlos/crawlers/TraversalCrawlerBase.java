package ricardo_crawlos.crawlers;

import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import ricardo_crawlos.core.ICrawler;

/**
 * BaseCrawler
 */
public abstract class TraversalCrawlerBase implements ICrawler
{
    protected final Set<String> links;
    protected final Queue<Document> traversalResults;

    private AtomicInteger maxPageSeen = new AtomicInteger(0);

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

    public double estimateTraversalProgress(String currentUrl)
    {
        try
        {
            var currentPage = Integer.parseInt(currentUrl.split("page=")[1]);
            if (maxPageSeen.get() < currentPage)
            {
                maxPageSeen.set(currentPage);
            }
            if (maxPageSeen.get() < 4)
            {
                return 0;
            }
            return (traversalResults.size() / ((double) maxPageSeen.get())) * 100;
        }
        catch (Exception e)
        {
            return 0;
        }
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
            var progress = estimateTraversalProgress(url);

            System.out.println("Traversing: " + (progress == 0 ? "" : String.format("est %.2f%% ", progress)) + url);
            try
            {
                Document document = Jsoup.connect(url).get();

                traversalResults.add(document);

                document.select("a[href]")
                        .parallelStream()
                        .map(x -> x.attr("abs:href"))
                        .filter(this::canTraverse)
                        .sorted(Comparator.comparing((String x) -> x).reversed())
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