package ricardo_crawlos.core;

import java.io.IOException;
import java.util.Dictionary;
import org.jsoup.HttpStatusException;

import ricardo_crawlos.models.Game;
import ricardo_crawlos.utilities.Statistics;

public interface ISearchContext
{
    void probe() throws IOException;
    void fetch();
    void extract();
    IGame getGameInfo();
    Dictionary<Integer, Statistics<Double, IReview>> analyse();
}
