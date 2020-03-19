package ricardo_crawlos.core;

/**
 * IExtractable
 */
public interface IExtractable<T, U>
{
    U extractFrom(T source);
}
