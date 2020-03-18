package ricardo_crawlos.managers;

import ricardo_crawlos.core.IManager;
import ricardo_crawlos.models.Genre;
import ricardo_crawlos.storage.JsonSerialiser;
import ricardo_crawlos.storage.TextWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.reflect.TypeToken;

public class GenreManager extends ItemStoreManagerBase<Genre, String>
{
    private static GenreManager gmInstance = null;

    @Override
    protected String getStoragePath()
    {
        return "database/managers/genre_manager.json";
    }

    private GenreManager()
    {
        super();
    }

    @Override
    public Genre getFromID(int id)
    {
        return new Genre(reverseLookupMap.get(id), id);
    }

    public static GenreManager getInstance()
    {
        if(gmInstance == null)
            gmInstance = new GenreManager();
        return gmInstance;
    }
}
