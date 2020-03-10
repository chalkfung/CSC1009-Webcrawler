package ricardo_crawlos.models;

import ricardo_crawlos.core.IReview;
import java.util.Date;

public class ReviewBase implements IReview
{
    private Date date;
    private int score;
    private String comments;
    private int gameID;

    public ReviewBase (int gameID, int score, String comments, Date date)
    {
        this.gameID = gameID;
        this.score = score;
        this.comments = comments;
        this.date = date;
    }

    @Override
    public Date getDataCreated()
    {
        return this.date;
    }

    @Override
    public int getScore()
    {
        return this.score;
    }

    @Override
    public String getComments()
    {
        return this.comments;
    }

    @Override
    public int getGameID()
    {
        return this.gameID;
    }
}