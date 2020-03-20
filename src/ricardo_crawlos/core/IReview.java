package ricardo_crawlos.core;

import java.util.Date;

/**
 * Interface backing for a Review model
 */
public interface IReview
{
    public Date getDateCreated();
    public double getScore();
    public String getComments();

    public int getGameID();

    public String getAuthor();
}
