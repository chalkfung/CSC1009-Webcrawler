package ricardo_crawlos.core;

import java.util.Date;
import java.util.List;

/**
 * The interface backing for Game model
 */
public interface IGame
{
    String getGameName();

    int getGameId();

    String getDeveloper();

    List<Integer> getGenres();

    Date getReleasedDate();

    String getDescription();
}
