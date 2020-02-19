package ricardo_crawlos.managers;

import ricardo_crawlos.core.IManager;
import ricardo_crawlos.models.Genre;
import java.util.HashMap;

public class GenreManager implements IManager<Genre, String>
{

    private static GenreManager gmInstance = null;
    private HashMap<String, Integer> genreMap;

    private GenreManager()
    {
        this.genreMap = new HashMap<String, Integer>();
    }

    public static IManager getInstance()
    {
        if(gmInstance == null)
            gmInstance = new GenreManager();
        return gmInstance;
    }

    public int getID(String name)
    {
        return genreMap.get(name);
    }

    public boolean isExist(String name)
    {
        return genreMap.containsKey(name);
    }

    public void append(String name)
    {
        genreMap.putIfAbsent(name, genreMap.size());
    }
}
