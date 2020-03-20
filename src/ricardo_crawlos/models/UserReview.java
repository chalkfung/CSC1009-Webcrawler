package ricardo_crawlos.models;
import java.util.Date;

/**
 * UserReview class which inherits from ReviewBase
 */
public class UserReview extends  ReviewBase
{
    private int helpfulCount;
    private int helpfulScore;

    /**
     * Constructor of User Review
     * @param gameID Game ID of the game which the review is addressing
     * @param score Score of given in the game review
     * @param comments Comments given in the game review
     * @param date  Date of when the User review was posted
     * @param author Author of the User Review
     * @param helpfulScore Helpful score, akin to thumbs up
     * @param helpfulCount Helpful count, the total of helpful or not helpful rating given
     */
    public UserReview(int gameID, double score, String comments, Date date, String author, int helpfulScore, int helpfulCount)
    {
        super(gameID, score, comments, date, author);
        this.helpfulScore = helpfulScore;
        this.helpfulCount = helpfulCount;
    }

    /**
     * Setter of the Helpful score
     * @param helpfulScore Helpful score to set
     */
    public void setHelpfulScore(int helpfulScore)
    {
        this.helpfulScore = helpfulScore;
    }

    /**
     * Getter of the Helpful score
     * @return Helpful score to get
     */
    public int getHelpfulScore()
    {
        return this.helpfulScore;
    }

    /**
     * Setter of the Helpful count
     * @param helpfulCount Helpful count to set
     */
    public void setHelpfulCount(int helpfulCount)
    {
        this.helpfulCount = helpfulCount;
    }

    /**
     * Getter of the Helpful count
     * @return Helpful count to get
     */
    public int getHelpfulCount()
    {
        return this.helpfulCount;
    }
}
