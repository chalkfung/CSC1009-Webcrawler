package ricardo_crawlos.models;

import ricardo_crawlos.core.IGenre;

/**
 * Genre class with IGenre interface.
 */
public class Genre implements IGenre
{
    private String genreName;
    private int genreId;

    /**
     * This is a constructor for Genre.
     * @param genreName_ Takes in genre and stores it in this class.
     * @param genreId_ Takes in genre id and stores it in this class.
     */
    public Genre(String genreName_, int genreId_)
    {
        this.genreName = genreName_;
        this.genreId = genreId_;
    }

    /**
     * A getter function for name of the genre.
     * @return Gets the genre's name from this class and returns it.
     */
    @Override
    public String getGenreName()
    {
        return this.genreName;
    }

    /**
     * A getter function for the genre's id
     * @return Gets the genre id from this class and returns it.
     */
    @Override
    public int getGenreId()
    {
        return this.genreId;
    }
}
