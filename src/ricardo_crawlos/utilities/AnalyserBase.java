package ricardo_crawlos.utilities;

import java.util.List;
import java.util.stream.Collectors;

import ricardo_crawlos.core.IAnalyser;
import ricardo_crawlos.core.IReview;
import ricardo_crawlos.models.UserReview;
/*
 * AnalyserBase class
 */
public class AnalyserBase<T extends IReview> implements IAnalyser<List<T>, Statistics<Double, T>>
{
    /**
     * Using Stream API to calculate the mean score of T objects using multi-threading
     * @param inputs Unreassingable List of T objects which extends IReview
     * @return Mean of the scores
     */
    public double getMean(final List<T> inputs)
    {
        return inputs.parallelStream().mapToDouble(x -> x.getScore()).average().orElse(0);
    }

    /**
     * Using Stream API to get the max score of T objects using multi-threading
     * @param inputs Unreassignable List of T objects which extends IReview
     * @return Max score
     */
    public double getMax(final List<T> inputs)
    {
        if (inputs.size() == 0)
        {
            return 0;
        }
        return inputs.parallelStream().mapToDouble(x -> x.getScore()).max().orElse(0);
    }

    /**
     * Using Stream API to get the minimum score of T objects using multi-threading
     * @param inputs Unreassignable List of T objects which extends IReview
     * @return Min score
     */
    public double getMin(final List<T> inputs)
    {
        return inputs.parallelStream().mapToDouble(x -> x.getScore()).min().orElse(0);
    }

    /**
     *  Calculate the variance of the T objects' scores
     * @param inputs Unreassignable list of T objects
     * @return Variance
     */
    public double getVariance(final List<T> inputs)
    {
        double variance = 0;
        double mean = getMean(inputs);
        double len = inputs.size();
        for (T x : inputs)
        {
            variance += (x.getScore() - mean) * (x.getScore() - mean);
        }
        variance /= len;
        return variance;
    }

    /**
     * Calculate the standard deviation of the T objects' scores
     * @param inputs Unreassignable list of T objects
     * @return Standard Deviation
     */
    public double getStandardDeviation(final List<T> inputs)
    {
        return Math.round(Math.sqrt(getVariance(inputs)) * 100.0) / 100.0;
    }

    /**
     * Get the 2nd Quartile Score/median of the T Objects's scores
     * @param inputs Unreassingable List of T object
     * @return 2nd Quartile/Median score
     */
    public double getQ2(final List<T> inputs)
    {
        if (inputs.size() <= 1)
        {
            return inputs.stream().map(x -> x.getScore()).findFirst().orElse(0.0);
        }

        double[] sorted = inputs.stream().mapToDouble(x -> x.getScore()).sorted().sequential().toArray();
        double Q2 = sorted.length % 2 == 0 ? (sorted[(sorted.length / 2) -1 ] + sorted[sorted.length / 2 ]) / 2
                : sorted[(int) (Math.floor((double) (sorted.length) * 0.5))];

        return Q2;
    }

    /**
     * Get the 3rd Quartile Score of the T Objects's scores
     * @param inputs Unreassingable List of T object
     * @return 3rd Quartile score
     */
    public double getQ3(final List<T> inputs)
    {
        if (inputs.size() <= 1)
        {
            return inputs.stream().map(x -> x.getScore()).findFirst().orElse(0.0);
        }

        double[] sorted = inputs.stream().mapToDouble(x -> x.getScore()).sorted().toArray();
        int half = (int) Math.ceil(sorted.length / 2);
        double Q3 = half % 2 == 0
                ? (sorted[(int) (sorted.length * 0.75) - 1] + sorted[(int) (sorted.length * 0.75)]) / 2
                : sorted[(int) (Math.floor((double) (sorted.length) * 0.75))];

        return Q3;
    }

    /**
     * Get the 1st Quartile score of the T objects' scores
     * @param inputs Unreassignable List of T objects
     * @return 1st Quartile score
     */
    public double getQ1(final List<T> inputs)
    {
        if (inputs.size() <= 1)
        {
            return inputs.stream().map(x -> x.getScore()).findFirst().orElse(0.0);
        }

        double[] sorted = inputs.stream().mapToDouble(x -> x.getScore()).sorted().toArray();
        int half = (int) Math.ceil(sorted.length / 2);
        double Q1 = half % 2 == 0 ? (sorted[sorted.length / 4 - 1] + sorted[sorted.length / 4]) / 2
                : sorted[(int) (Math.ceil(sorted.length / 4))];
        return Q1;
    }

    /**
     * Get the Interquartile Range of the T objects' scores
     * @param inputs Unreassingable list of T objects
     * @return Interquartile range
     */
    public double getIQR(final List<T> inputs)
    {
        return getQ3(inputs) - getQ1(inputs);
    }

    /**
     * Get the minimum acceptable T objects' score, any score below is considered as outlier.
     * @param inputs Unreassinable list of T objects
     * @return Minimum acceptable scores
     */
    public double getMinAcceptableValues(final List<T> inputs)
    {
        return getQ1(inputs) - 1.5 * getIQR(inputs);
    }

    /**
     * Get the maximum acceptable T objects' score, any score above is considered as outlier
     * @param inputs Unreassingable list of T objects
     * @return Maximum acceptable scores
     */
    public double getMaxAcceptableValues(final List<T> inputs)
    {
        return getQ3(inputs) + 1.5 * getIQR(inputs);
    }

    /**
     * Remove all T objects with scores falling above and below the acceptable scores.
     * @param inputs Unreassinable list of T objects
     * @return List of T objects' with scores which are not outlying
     */
    public List<T> removeOutliers(final List<T> inputs)
    {
        return inputs.parallelStream().filter(elem -> (elem.getScore() < getMaxAcceptableValues(inputs)
                && elem.getScore() > getMinAcceptableValues(inputs))).collect(Collectors.toList());
    }

    /**
     * Collecting all T objects with score falling above and below the acceptable score. If T is an instance of
     * UserReview, check if the helpful rating is above 0.5.
     * @param inputs Unreassinable list of T objects
     * @return List of T objects' with scores which are outlying
     */
    @SuppressWarnings("unchecked")
    public List<T> showOutliers(final List<T> inputs)
    {
        if (inputs.size() > 0 && inputs.get(0) instanceof UserReview)
        {
            var maxAccept = getMaxAcceptableValues(inputs);
            var minAccept = getMinAcceptableValues(inputs);

            System.out.println("Max Accept: " + maxAccept + " Min Accept: " + minAccept);
            var output =inputs.parallelStream().map(x -> (UserReview) x)
                    .filter(elem -> (elem.getScore() > maxAccept || elem.getScore() < minAccept)
                            && (((double) elem.getHelpfulScore() / (double) elem.getHelpfulCount()) >= 0.5))
                    .map(x -> (T) x).collect(Collectors.toList());

            return output;
        }
        else
        {
            return inputs.parallelStream().filter(elem -> !(elem.getScore() < getMaxAcceptableValues(inputs)
                    && elem.getScore() > getMinAcceptableValues(inputs))).collect(Collectors.toList());
        }
    }

    /**
     * Getting the probability given a score
     * @param inputs Unreassignable list of T
     * @param score Score to calculate the probability
     * @return Probability of given score
     */
    public double probabilityOfScore(final List<T> inputs, double score)
    {
        double sd = getStandardDeviation(inputs);
        return (1.0 / (sd * Math.sqrt(2.0 * Math.PI)))
                * Math.exp(-0.5 * ((score - getMean(inputs)) / sd) * ((score - getMean(inputs)) / sd));
    }

    /**
     * Wrap up all the required statistics values into a single class object.
     * @param input Unreassinable list of T
     * @return Statistics object package which contains all the necessary statistics values
     */
    @Override
    public Statistics<Double, T> Analyse(List<T> input)
    {
        return new Statistics<Double, T>(getMean(input), getMax(input), getMin(input), getVariance(input),
                getStandardDeviation(input), getQ2(input), getQ3(input), getQ1(input), getIQR(input),
                getMinAcceptableValues(input), getMaxAcceptableValues(input), showOutliers(input),
                removeOutliers(input), input);
    }
}
