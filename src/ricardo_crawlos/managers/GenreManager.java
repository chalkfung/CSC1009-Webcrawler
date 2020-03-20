package ricardo_crawlos.managers;

import ricardo_crawlos.models.Genre;

/**
 * GenreManager class to handle the genre information and provide an genre ID for the scraped data.
 * Singleton class such that there should not be conflicting instances.
 */
public class GenreManager extends ItemStoreManagerBase<Genre, String>
{
    private static GenreManager gmInstance = null;
    /**
     * The storage path of the IDs bound to the genre information
     * @return String path on the disk
     */
    @Override
    protected String getStoragePath()
    {
        return "database/managers/genre_manager.json";
    }

    /**
     * Constructor for GenreManager
     */
    private GenreManager()
    {
        super();
    }

    /**
     * Get the genre class object given an id input
     * @param id Integer value which is mapped to a genre
     * @return Genre class object
     */
    @Override
    public Genre getFromID(int id)
    {
        return new Genre(reverseLookupMap.get(id), id);
    }

    /**
     * getInstance such that the constructor will only be called once through this function, which makes this class
     * singleton
     * @return GenreManager class object
     */
    public static GenreManager getInstance()
    {
        if(gmInstance == null)
            gmInstance = new GenreManager();
        return gmInstance;
    }
}
