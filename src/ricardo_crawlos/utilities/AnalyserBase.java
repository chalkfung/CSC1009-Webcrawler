package ricardo_crawlos.utilities;

import java.util.Arrays;



public class AnalyserBase
{
    public static double getStandardDeviation(double[] inputs)
    {
        double mean = Arrays.stream(inputs).average().getAsDouble();
        double sd = 0;
        double len = inputs.length;
        for(double x : inputs)
        {
            sd += (x - mean) * (x - mean);
        }
        sd = Math.sqrt(sd/len);
        return sd;
    }

    public static double getMax(double [] inputs)
    {
        return Arrays.stream(inputs).max().getAsDouble();
    }

    public static double getMin(double [] inputs)
    {
        return Arrays.stream(inputs).min().getAsDouble();
    }


    public static double getQ3(double[] inputs)
    {
        double[] sorted = Arrays.stream(inputs).sorted().toArray();
        int half =(int) Math.ceil(sorted.length/2);
        double Q3 = half % 2 == 0
                ? (sorted[(int)(sorted.length * 0.75)] + sorted[(int)(sorted.length * 0.75) + 1])/2
                : sorted[(int)(Math.floor((double)(sorted.length) * 0.75))]
                ;
        return Q3;
    }

    public static double getQ1(double[] inputs)
    {
        double[] sorted = Arrays.stream(inputs).sorted().toArray();
        int half =(int) Math.ceil(sorted.length/2);
        double Q1 = half % 2 == 0
                ? (sorted[sorted.length/4]+ sorted[(sorted.length/4) + 1])/2
                : sorted[(int) (Math.ceil(sorted.length/4))]
                ;
        return Q1;
    }

    public static double getIQR(double[] inputs)
    {
        return getQ3(inputs) - getQ1(inputs) ;
    }

    public static double getMinAcceptableValues(double[] inputs)
    {
        return getQ1(inputs) - 1.5 * getIQR(inputs);
    }

    public static double getMaxAcceptableValues(double[] inputs)
    {
        return getQ3(inputs) + 1.5 * getIQR(inputs);
    }

    public static double[] removeOutliers(double[] inputs)
    {
        return Arrays.stream(inputs)
                .filter(elem -> (elem < getMaxAcceptableValues(inputs) && elem > getMinAcceptableValues(inputs)))
                .toArray();
    }


    public static void main(String[] args)
    {
        double[] tmp = {8160,8160,6160,22684,0,0,60720,1380,1380,57128};
        //double sd = getStandardDeviation(tmp);
        //System.out.println(sd);
        for(double elem : removeOutliers(tmp))
        {
            System.out.println(elem);
        }
    }
}
