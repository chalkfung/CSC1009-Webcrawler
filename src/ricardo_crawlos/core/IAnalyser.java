package ricardo_crawlos.core;

/**
 * IAnalyser
 */
public interface IAnalyser<T, U>
{
    U Analyse(T input);
}