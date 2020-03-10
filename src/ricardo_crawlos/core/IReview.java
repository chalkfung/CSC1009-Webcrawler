package ricardo_crawlos.core;

public interface IReview
{
    public java.util.Date getDataCreated();
    public int getScore();
    public String getComments();
    public int getGameID();
    public String getAuthor();
}