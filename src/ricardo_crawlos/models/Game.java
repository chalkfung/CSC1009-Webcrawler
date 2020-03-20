package ricardo_crawlos.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import ricardo_crawlos.core.IGame;
import ricardo_crawlos.managers.GenreManager;

/**
 * A Game class with IGame interface.
 */
public class Game implements IGame
{
    private String gameName;
    private int gameId;
    private String developer;
    private List<Integer> genres;
    private Date releasedDate;
    private String description;

    /**
     *  This is a constructor for Games.
     * @param gameName_ Takes in game name and stores in this class.
     * @param gameId_ Takes in game id and stores in this class.
     * @param developer_ Takes in developer and stores in this class.
     * @param genres_ Takes in genres and stores in this class.
     * @param releasedDate_ Takes in released date and stores in this class.
     * @param description_ Takes in description and stores in this class.
     */
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

    /**
     * A getter function that gets the game's name.
     * @return Gets the gameName from this class and returns it.
     */
    @Override
    public String getGameName()
    {
        return this.gameName;
    }

    /**
     * A getter function for the game id.
     * @return Gets the game id from this class and returns it.
     */
    @Override
    public int getGameId()
    {
        return this.gameId;
    }

    /**
     * A getter function for the game's developer.
     * @return Gets the developer from this class and returns it.
     */
    @Override
    public String getDeveloper()
    {
        return this.developer;
    }

    /**
     * A getter function for the game's genres
     * @return Gets the genres from this class and returns it.
     */
    @Override
    public List<Integer> getGenres()
    {
        return this.genres;
    }

    /**
     * A getter function of the released date of the game.
     * @return Gets the released date from this class and returns it.
     */
    @Override
    public java.util.Date getReleasedDate()
    {
        return this.releasedDate;
    }

    /**
     * A getter function of the game's description
     * @return Gets the description from this class and returns it.
     */
    @Override
    public String getDescription()
    {
        return this.description;
    }

    /**
     * Formats the genres, released date and description of the games into string
     * @return Once formatted, it returns in string type.
     */
    @Override
    public String toString()
    {
        return "Genres: " + genres.stream().map(x -> GenreManager.getInstance().getFromID(x).getGenreName()).collect(Collectors.joining(", ")) + "\n" +
                "Released: " + new SimpleDateFormat("dd-MMM-yyyy").format(releasedDate) + "\n\n" +
                description;
    }
}
