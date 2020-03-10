package ricardo_crawlos.models;
import java.util.Date;

public class UserReview extends  ReviewBase
{
    private int helpfulCount;
    private int helpfulScore;

    public UserReview(int gameID, int score, String comments, Date date, int helpfulScore, int helpfulCount)
    {
        super(gameID, score, comments, date);
        this.helpfulScore = helpfulScore;
        this.helpfulCount = helpfulCount;
    }

    public void setHelpfulScore(int helpfulScore)
    {
        this.helpfulScore = helpfulScore;
    }

    public int getHelpfulScore()
    {
        return this.helpfulScore;
    }

    public void setHelpfulCount(int helpfulCount)
    {
        this.helpfulCount = helpfulCount;
    }

    public int getHelpfulCount()
    {
        return this.helpfulCount;
    }
}