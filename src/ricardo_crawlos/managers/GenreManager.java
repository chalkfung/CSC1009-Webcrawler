package ricardo_crawlos.managers;

import ricardo_crawlos.models.Genre;

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
