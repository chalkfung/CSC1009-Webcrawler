package ricardo_crawlos.models;

import java.util.List;

public class Game
{
    private String gameName;
    private int gameId;
    private String developer;
    private List<Integer> genres;
    private List<Integer> platforms;
    private java.util.Date releasedDate;

    public Game(String gameName_, int gameId_, String developer_, List<Integer> genres_, List<Integer> platforms_,
                java.util.Date releasedDate_)
    {
        this.gameName = gameName_;
        this.gameId = gameId_;
        this.developer = developer_;
        this.genres = genres_;
        this.platforms = platforms_;
        this.releasedDate = releasedDate_;
    }

    public String getGameName()
    {
        return this.gameName;
    }

    public int getGameId()
    {
        return this.gameId;
    }

    public String getDeveloper()
    {
        return this.developer;
    }

    public List<Integer> getGenres()
    {
        return this.genres;
    }

    public List<Integer> getPlatforms()
    {
        return this.platforms;
    }

    public java.util.Date getReleasedDate()
    {
        return this.releasedDate;
    }
}
