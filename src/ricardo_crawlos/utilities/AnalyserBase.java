package ricardo_crawlos.utilities;

import java.util.*;
import java.util.stream.Collectors;

import ricardo_crawlos.core.IReview;

public class AnalyserBase<T extends IReview>
{
    public double getMean(final List<T> inputs)
    {
        return inputs.stream().mapToDouble(x->x.getScore()).average().getAsDouble();
    }

    public double getMax(final List<T> inputs)
    {
        return inputs.stream().mapToDouble(x -> x.getScore()).max().getAsDouble();
    }

    public double getMin(final List<T> inputs)
    {
        return inputs.stream().mapToDouble(x -> x.getScore()).min().getAsDouble();
    }

    public double getVariance(final List<T> inputs)
    {
        double variance = 0;
        double mean = getMean(inputs);
        double len = inputs.size();
        for(T x : inputs)
        {
            variance += (x.getScore() - mean) * (x.getScore() - mean);
        }
        variance /= len;
        return variance;
    }

    public double getStandardDeviation(final List<T> inputs)
    {
        return Math.sqrt(getVariance(inputs));
    }

    public double getQ2(final List<T> inputs)
    {
        double[] sorted = inputs.stream().mapToDouble(x->x.getScore()).sorted().toArray();
        double Q2 = sorted.length % 2 == 0
                ? (sorted[sorted.length/2 - 1] + sorted[sorted.length/2])/2
                : sorted[(int)(Math.ceil((double)(sorted.length) * 0.5))]
                ;
        return Q2;
    }

    public double getQ3(final List<T> inputs)
    {
        double[] sorted = inputs.stream().mapToDouble(x->x.getScore()).sorted().toArray();
        int half =(int) Math.ceil(sorted.length/2);
        double Q3 = half % 2 == 0
                ? (sorted[(int)(sorted.length * 0.75) - 1] + sorted[(int)(sorted.length * 0.75)])/2
                : sorted[(int)(Math.floor((double)(sorted.length) * 0.75))]
                ;
        return Q3;
    }

    public double getQ1(final List<T> inputs)
    {
        double[] sorted = inputs.stream().mapToDouble(x->x.getScore()).sorted().toArray();
        int half =(int) Math.ceil(sorted.length/2);
        double Q1 = half % 2 == 0
                ? (sorted[sorted.length/4 - 1]+ sorted[sorted.length/4])/2
                : sorted[(int) (Math.ceil(sorted.length/4))]
                ;
        return Q1;
    }

    public double getIQR(final List<T> inputs)
    {
        return getQ3(inputs) - getQ1(inputs) ;
    }

    public double getMinAcceptableValues(final List<T> inputs)
    {
        return getQ1(inputs) - 1.5 * getIQR(inputs);
    }

    public double getMaxAcceptableValues(final List<T> inputs)
    {
        return getQ3(inputs) + 1.5 * getIQR(inputs);
    }

    public List<T> removeOutliers(final List<T> inputs)
    {
        return inputs.stream().filter(elem-> (elem.getScore() < getMaxAcceptableValues(inputs)
                        && elem.getScore() > getMinAcceptableValues(inputs))).collect(Collectors.toList());
    }

    public List<T> showOutliers(final List<T> inputs)
    {
        return inputs.stream().filter(elem-> !(elem.getScore() < getMaxAcceptableValues(inputs)
                        && elem.getScore() > getMinAcceptableValues(inputs))).collect(Collectors.toList());
    }


}
