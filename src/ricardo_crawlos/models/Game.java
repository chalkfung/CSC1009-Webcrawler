package ricardo_crawlos.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ricardo_crawlos.core.IGame;
import ricardo_crawlos.managers.GenreManager;

public class Game implements IGame
{
    private String gameName;
    private int gameId;
    private String developer;
    private List<Integer> genres;
    private Date releasedDate;
    private String description;

    public Game(String gameName_, int gameId_, String developer_, List<Integer> genres_,
                Date releasedDate_, String description_)
    {
        this.gameName = gameName_;
        this.gameId = gameId_;
        this.developer = developer_;
        this.genres = genres_;
        this.releasedDate = releasedDate_;
        this.description = description_;
    }

    @Override
    public String getGameName()
    {
        return this.gameName;
    }

    @Override
    public int getGameId()
    {
        return this.gameId;
    }

    @Override
    public String getDeveloper()
    {
        return this.developer;
    }

    @Override
    public List<Integer> getGenres()
    {
        return this.genres;
    }

    @Override
    public java.util.Date getReleasedDate()
    {
        return this.releasedDate;
    }

    @Override
    public String getDescription()
    {
        return this.description;
    }

    @Override
    public String toString()
    {
        return "Genres: " + genres.stream().map(x -> GenreManager.getInstance().getFromID(x).getGenreName()).collect(Collectors.joining(", ")) + "\n" +
                "Released: " + new SimpleDateFormat("dd-MMM-yyyy").format(releasedDate) + "\n\n" +
                description;
    }
}
