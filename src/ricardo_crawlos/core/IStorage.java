package ricardo_crawlos.core;

/**
 * IStorage
 * Planned simple storage provider to store collections in the file system
 */
public interface IStorage<T>
{
    T[] getEntities();

    void setEntities(T[] items);
}
