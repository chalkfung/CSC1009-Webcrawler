package ricardo_crawlos.core;

import java.util.List;

public interface IGame
{
    String getGameName();

    int getGameId();

    String getDeveloper();

    List<Integer> getGenres();

    java.util.Date getReleasedDate();
}
