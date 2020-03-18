package ricardo_crawlos.models;

import ricardo_crawlos.core.IGenre;

public class Genre implements IGenre
{
    private String genreName;
    private int genreId;

    public Genre(String genreName_, int genreId_)
    {
        this.genreName = genreName_;
        this.genreId = genreId_;
    }

    @Override
    public String getGenreName()
    {
        return this.genreName;
    }

    @Override
    public int getGenreId()
    {
        return this.genreId;
    }
}
