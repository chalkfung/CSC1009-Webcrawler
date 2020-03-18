package ricardo_crawlos.core;

import java.io.IOException;
import org.jsoup.HttpStatusException;

public interface ISearchContext
{
    void probe() throws IOException;
    void fetch();
    void extract();
    void analyse();
}
