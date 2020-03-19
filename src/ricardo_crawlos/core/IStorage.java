package ricardo_crawlos.core;

/**
 * IStorage
 */
public interface IStorage<T>
{
    T[] getEntities();

    void setEntities(T[] items);
}
