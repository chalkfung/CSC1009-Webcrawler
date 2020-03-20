package ricardo_crawlos.core;

/**
 * IExtractable
 * Extract type T into type U
 */
public interface IExtractable<T, U>
{
    U extractFrom(T source);
}
