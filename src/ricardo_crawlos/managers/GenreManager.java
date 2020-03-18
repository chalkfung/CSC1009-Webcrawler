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

public class GenreManager implements IManager<Genre, String>
{

    private static GenreManager gmInstance = null;
    private Map<String, Integer> genreMap;

    private String savePath = "database/managers/genre_manager.json";

    private GenreManager()
    {
        this.genreMap = new HashMap<>();

        var path = Path.of(savePath);
        if (Files.exists(path))
        {
            try
            {
                Type type = new TypeToken<Map<String, Integer>>(){}.getType();
                genreMap = JsonSerialiser.DefaultInstance().fromJson(Files.readString(path), type);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    public static GenreManager getInstance()
    {
        if(gmInstance == null)
            gmInstance = new GenreManager();
        return gmInstance;
    }

    public int getID(String name)
    {
        return this.genreMap.get(name);
    }

    public boolean isExist(String name)
    {
        return this.genreMap.containsKey(name);
    }

    public void append(String name)
    {
        if (!isExist(name))
        {
            this.genreMap.put(name, this.genreMap.size());
            var serialised = JsonSerialiser.DefaultInstance().toJson(this.genreMap);
            TextWriter.writeAllText(savePath, serialised);
        }
    }

    @Override
    public Genre getFromID(int id)
    {
        if (genreMap.containsValue(id))
        {
            for (String key : genreMap.keySet())
            {
                if (genreMap.get(key) == id)
                {
                    return new Genre(key, id);
                }
            }
        }
        return new Genre("Unknown Genre", -1);
    }
}
