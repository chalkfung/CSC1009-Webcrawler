package ricardo_crawlos.utilities;

import java.util.List;

/**
 * Statistic class which acts as a package which contain all the statistics values.
 * @param <T> The values type, i.e. Double
 * @param <U> The class objects which has functions to produce T statistical values
 */
public class Statistics<T,U>
{
    private T mean;
    private T max;
    private T min;
    private T variance;
    private T sd;
    private T q2;
    private T q3;
    private T q1;
    private T iqr;
    private T minAcceptableVal;
    private T maxAcceptableVal;
    private List<U> outliers;
    private List<U> nonOutliers;
    private List<U> original;

    /**
     * Constructor for the Statistics package
     */
    public Statistics(T mean, T max, T min, T variance, T sd, T q2, T q3, T q1, T iqr, T minAcceptableVal, T maxAcceptableVal, List<U> outliers, List<U> nonOutliers, List<U> original)
    {
        this.mean = mean;
        this.max = max;
        this.min = min;
        this.variance = variance;
        this.sd = sd;
        this.q2 = q2;
        this.q3 = q3;
        this.q1 = q1;
        this.iqr = iqr;
        this.minAcceptableVal = minAcceptableVal;
        this.maxAcceptableVal = maxAcceptableVal;
        this.outliers = outliers;
        this.nonOutliers = nonOutliers;
        this.original = original;
    }

    /**
     * Getter for SD
     * @return T type value of SD
     */
    public T getSd()
    {
        return sd;
    }

    /**
     * Getter for 2nd quartile
     * @return T type value of 2nd quartile
     */
    public T getQ2()
    {
        return q2;
    }

    /**
     * Getter for 3rd quartile
     * @return T type value of 3rd quartile
     */
    public T getQ3()
    {
        return q3;
    }

    /**
     * Getter for 1st quartile
     * @return T type value of 1st quartile
     */
    public T getQ1()
    {
        return q1;
    }

    /**
     * Getter for interquartile range
     * @return T type value of the interquartile range
     */
    public T getIqr()
    {
        return iqr;
    }

    /**
     * Getter for minimum acceptable value
     * @return T type value of the min acceptable value
     */
    public T getMinAcceptableVal()
    {
        return minAcceptableVal;
    }

    /**
     * Getter for maximum acceptable value
     * @return T type value of the max acceptable value
     */
    public T getMaxAcceptableVal()
    {
        return maxAcceptableVal;
    }

    /**
     * Getter of a list of acceptable outliers
     * @return List of Us which are acceptable outliers
     */
    public List<U> getOutliers()
    {
        return outliers;
    }

    /**
     * Getter of a list of non-outliers
     * @return List of Us which are non outliers
     */
    public List<U> getNonOutliers()
    {
        return nonOutliers;
    }

    /**
     * Getter of the list of unchanged data set
     * @return List of Us which are not cleansed of outliers
     */
    public List<U> getOriginal(){return original;};

    /**
     * Getter for mean
     * @return T type value of the mean
     */
    public T getMean()
    {
        return mean;
    }

    /**
     * Getter for max value
     * @return T type value of the max
     */
    public T getMax()
    {
        return max;
    }

    /**
     * Getter for min value
     * @return T type value of the min
     */
    public T getMin()
    {
        return min;
    }

    /**
     * Getter for variance value
     * @return T type value of the variance
     */
    public T getVariance()
    {
        return variance;
    }
}
