package ricardo_crawlos.core;

import java.util.Date;

public interface IReview
{
    public Date getDataCreated();
    public int getScore();
    public String getComments();
    public int getGameID();
    public String getAuthor();
}