package ricardo_crawlos.core;

import java.io.IOException;
import java.util.Dictionary;
import ricardo_crawlos.utilities.Statistics;

public interface ISearchContext
{
    void probe() throws IOException;
    void fetch();
    void extract();
    IGame getGameInfo();
    IReview[] getAllUserReviews();
    IReview[] getAllCriticReviews();
    Dictionary<Integer, Statistics<Double, IReview>> analyse();
}
