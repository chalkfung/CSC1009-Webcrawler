package ricardo_crawlos.models;
import java.util.Date;

public class CriticReview extends ReviewBase
{
    private String source;

    public CriticReview (int gameID, double score, String comments, Date date, String source, String author)
    {
        super(gameID, score, comments, date, author);
        this.source = source;
    }

    public int getGameID()
    {
        return super.getGameID();
    }

    public double getScore()
    {
        return super.getScore();
    }

    public String getComments()
    {
        return super.getComments();
    }

    public Date getDateCreated()
    {
        return super.getDateCreated();
    }

    public String getAuthor()
    {
        return super.getAuthor();
    }

    public String getSource()
    {
        return this.source;
    }
}
