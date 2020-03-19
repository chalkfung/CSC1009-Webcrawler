package ricardo_crawlos.extractors;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.stream.Collectors;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import ricardo_crawlos.core.IExtractable;
import ricardo_crawlos.managers.GameManager;
import ricardo_crawlos.managers.GenreManager;
import ricardo_crawlos.models.Game;

public class GamespotGameinfoExtractor implements IExtractable<String, Game>
{
    private SimpleDateFormat dateParser = new SimpleDateFormat("MMM dd, yyyy");
    @Override
    public Game extractFrom(String source)
    {
        var document = Jsoup.parse(source);
        var title = document.selectFirst(".pod-objectStats-info__title > h3 > a").ownText();
        GameManager.getInstance().append(title);
        var infoNodes = document.select("dl.pod-objectStats-additional > dd > a");
        var developer = infoNodes.stream()
                .filter(x -> x.attr("href").contains("companies"))
                .map(Element::ownText)
                .findFirst()
                .orElse("Unknown Developer");
        var genres = infoNodes.stream()
                .filter(x -> x.attr("href").contains("genre"))
                .map(Element::ownText)
                .collect(Collectors.toList());

        var date = parseDate(document.select("dd.pod-objectStats-info__release li > span").text());

        var description = document.select("dd.pod-objectStats-info__deck").text();

        genres.forEach(GenreManager.getInstance()::append);
        var genreIds = genres.stream().map(x -> GenreManager.getInstance().getID(x)).collect(Collectors.toList());

        return new Game(title, GameManager.getInstance().getID(title), developer, genreIds, date, description);
    }

    Date parseDate(String text)
    {
        try
        {
            return dateParser.parse(text);
        }
        catch (Exception e)
        {
            return new Date(0);
        }
    }
}
