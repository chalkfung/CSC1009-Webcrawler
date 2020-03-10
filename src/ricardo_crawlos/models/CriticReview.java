package ricardo_crawlos.models;
import java.util.Date;

public class CriticReview extends ReviewBase
{
    private String source;

    public CriticReview (int gameID, int score, String comments, Date date, String source)
    {
        super(gameID, score, comments, date);
        this.source = source;
    }

    public int getGameID()
    {
        return super.getGameID();
    }

    public int getScore()
    {
        return super.getScore();
    }

    public String getComments()
    {
        return super.getComments();
    }

    public Date getDateCreated()
    {
        return super.getDataCreated();
    }

    public String getSource()
    {
        return this.source;
    }
}