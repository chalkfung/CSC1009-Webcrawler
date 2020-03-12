package ricardo_crawlos.core;

import java.util.Date;

public interface IReview
{
    public Date getDataCreated();
    public double getScore();
    public String getComments();
    public int getGameID();
    public String getAuthor();
}