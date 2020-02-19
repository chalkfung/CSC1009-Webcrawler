package ricardo_crawlos.core;

public interface IManager<T, U>
{
    int getID(U name);
    boolean isExist(U name);
    void append(U name);
}
