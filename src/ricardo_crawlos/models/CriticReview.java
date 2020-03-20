package ricardo_crawlos.models;
import java.util.Date;

/**
 * CriticReview class that inherits from ReviewBase class.
 */
public class CriticReview extends ReviewBase
{
    private String source;

    /**
     * This constructor is mainly for Critic Reviews's attributes.
     * @param gameID Taking in the game id and pass it to the super class, ReviewBase.
     * @param score Taking in the score and pass it to the super class, ReviewBase.
     * @param comments Taking in the comments and pass it to the super class, ReviewBase.
     * @param date Taking in the date and pass it to the super class, ReviewBase.
     * @param source Taking in the source and stores it in this class.
     * @param author Taking in the author and pass it to the super class, ReviewBase.
     */
    public CriticReview (int gameID, double score, String comments, Date date, String source, String author)
    {
        super(gameID, score, comments, date, author);
        this.source = source;
    }

    /**
     * A getter function for game id.
     * @return Gets the game id from the super class and returns the value.
     */
    public int getGameID()
    {
        return super.getGameID();
    }

    /**
     * A getter function for game rating.
     * @return Gets the score from the super class and returns the value.
     */
    public double getScore()
    {
        return super.getScore();
    }

    /**
     * A getter function for the context of the review.
     * @return Gets the comments from the super class and returns the text in String format.
     */
    public String getComments()
    {
        return super.getComments();
    }

    /**
     * A getter function for the date of when the review was created.
     * @return Gets the date from the super class and returns it.
     */
    public Date getDateCreated()
    {
        return super.getDateCreated();
    }

    /**
     * A getter function of the review author.
     * @return Gets the name of the author from the super class and returns it.
     */
    public String getAuthor()
    {
        return super.getAuthor();
    }

    /**
     * A getter function of the URL that the review is from.
     * @return Gets the source of the review from the super class and returns it.
     */
    public String getSource()
    {
        return this.source;
    }
}
