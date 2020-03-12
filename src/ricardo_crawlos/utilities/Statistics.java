package ricardo_crawlos.utilities;

import java.util.List;

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

    public Statistics(T mean, T max, T min, T variance, T sd, T q2, T q3, T q1, T iqr, T minAcceptableVal, T maxAcceptableVal, List<U> outliers, List<U> nonOutliers)
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
    }

    public T getSd()
    {
        return sd;
    }

    public T getQ2()
    {
        return q2;
    }

    public T getQ3()
    {
        return q3;
    }

    public T getQ1()
    {
        return q1;
    }

    public T getIqr()
    {
        return iqr;
    }

    public T getMinAcceptableVal()
    {
        return minAcceptableVal;
    }

    public T getMaxAcceptableVal()
    {
        return maxAcceptableVal;
    }

    public List<U> getOutliers()
    {
        return outliers;
    }

    public List<U> getNonOutliers()
    {
        return nonOutliers;
    }

    public T getMean()
    {
        return mean;
    }

    public T getMax()
    {
        return max;
    }

    public T getMin()
    {
        return min;
    }

    public T getVariance()
    {
        return variance;
    }
}
