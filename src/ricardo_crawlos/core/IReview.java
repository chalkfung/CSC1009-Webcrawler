package ricardo_crawlos.core;

import java.util.Date;

public interface IReview
{
    public Date getDateCreated();
    public double getScore();
    public String getComments();

    public int getGameID();

    public String getAuthor();
}
