package ricardo_crawlos.models;

import ricardo_crawlos.core.IReview;
import java.util.Date;

public class ReviewBase implements IReview
{
    private Date date;
    private double score;
    private String comments;
    private int gameID;
    private String author;

    public ReviewBase (int gameID, double score, String comments, Date date, String author)
    {
        this.gameID = gameID;
        this.score = score;
        this.comments = comments;
        this.date = date;
        this.author = author;
    }

    @Override
    public Date getDateCreated()
    {
        return this.date;
    }

    @Override
    public double getScore()
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

    @Override
    public String getAuthor()
    {
        return this.author;
    }


}