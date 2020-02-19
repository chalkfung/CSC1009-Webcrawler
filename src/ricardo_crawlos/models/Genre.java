package ricardo_crawlos.models;

public class Genre {
    private String genreName;
    private int genreId;

    public Genre(String genreName_, int genreId_)
    {
        this.genreName = genreName_;
        this.genreId = genreId_;
    }

    public String getGenreName()
    {
        return this.genreName;
    }

    public int getGenreId()
    {
        return this.genreId;
    }
}
