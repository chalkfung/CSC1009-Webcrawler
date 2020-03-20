package ricardo_crawlos.core;

/**
 * A manager is a lookup helper of a value which can be represented by 2 related types, mapped to a int ID
 * @param <T> The primary type i.e the data model
 * @param <U> The secondary type i.e the unique name in String
 */
public interface IManager<T, U>
{
    int getID(U name);

    boolean isExist(U name);

    void append(U name);

    T getFromID(int id);
}
