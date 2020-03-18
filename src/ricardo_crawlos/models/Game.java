package ricardo_crawlos.models;

import java.util.Date;
import java.util.List;

public class Game
{
    private String gameName;
    private int gameId;
    private String developer;
    private List<Integer> genres;
    private Date releasedDate;

    public Game(String gameName_, int gameId_, String developer_, List<Integer> genres_,
                java.util.Date releasedDate_)
    {
        this.gameName = gameName_;
        this.gameId = gameId_;
        this.developer = developer_;
        this.genres = genres_;
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

    public java.util.Date getReleasedDate()
    {
        return this.releasedDate;
    }

    @Override
    public String toString()
    {
        return "Game{" +
                "gameName='" + gameName + '\'' +
                ", gameId=" + gameId +
                ", developer='" + developer + '\'' +
                ", genres=" + genres +
                ", releasedDate=" + releasedDate +
                '}';
    }
}
