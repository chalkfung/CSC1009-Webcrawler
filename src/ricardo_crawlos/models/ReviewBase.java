package ricardo_crawlos.models;

import ricardo_crawlos.core.IReview;
import java.util.Date;

/**
 * ReviewBase class which implements IReview interface
 * Holds all the information required for Reviews
 */
public class ReviewBase implements IReview
{
    private Date date;
    private double score;
    private String comments;
    private int gameID;
    private String author;

    /**
     * Constructor for ReviewBase
     * @param gameID    ID of the game which the review is addressing
     * @param score     Score of the game given by the review
     * @param comments  Comments in the game review
     * @param date      Date of the game review
     * @param author    Author of the game reviewer
     */
    public ReviewBase (int gameID, double score, String comments, Date date, String author)
    {
        this.gameID = gameID;
        this.score = score;
        this.comments = comments;
        this.date = date;
        this.author = author;
    }

    /**
     * Getter of the date when the review is posted
     * @return Date of the review
     */
    @Override
    public Date getDateCreated()
    {
        return this.date;
    }

    /**
     * Getter of the score given in the review
     * @return Score
     */
    @Override
    public double getScore()
    {
        return this.score;
    }

    /**
     * Getter of the comments given in the review
     * @return Comments in String
     */
    @Override
    public String getComments()
    {
        return this.comments;
    }


    /**
     * Getter of the game ID which the review is addressing
     * @return Game Integer ID
     */
    @Override
    public int getGameID()
    {
        return this.gameID;
    }

    /**
     * Getter of the author of the review
     * @return Author name in Strings
     */
    @Override
    public String getAuthor()
    {
        return this.author;
    }
}
