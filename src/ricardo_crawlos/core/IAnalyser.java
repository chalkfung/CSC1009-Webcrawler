package ricardo_crawlos.core;

/**
 * IAnalyser
 * Takes type T and process into type U
 */
public interface IAnalyser<T, U>
{
    U Analyse(T input);
}
