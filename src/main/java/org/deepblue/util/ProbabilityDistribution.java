package org.deepblue.util;


import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * A class that represents a probability distribution
 */
@SuppressWarnings("unused")
public class ProbabilityDistribution {

    /**
     * Istancietes a ProbabilityDistribution with the given data
     * @param stats the data
     *              Must not contain data with same label on different values
     * @return the ProbabilityDistribution
     */
    public static ProbabilityDistribution fromStatisticalData(List<? extends StatisticalData> stats) {
        ProbabilityDistribution pd = new ProbabilityDistribution();
        for (StatisticalData sd : stats) {
            pd.data.add(sd);
        }
        pd.sumDuplicates();
        return pd;
    }

    private List<StatisticalData> data;

    private boolean ordered = false;

    /**
     * Empty ProbabilityDistribution
     */
    public ProbabilityDistribution() {
        data = new ArrayList<>();
    }

    /**
     * Gives the data with given label
     * @param label label
     * @return a {@link StatisticalData} object
     */
    public StatisticalData getByLabel(String label) {
        for (StatisticalData sd: data) {
            if (Objects.equals(label, sd.label())) return sd.copy();
        }
        return null;
    }

    public StatisticalData getByValue(float value) {
        needsOrderedData();

        int index = Collections.binarySearch(data, StatisticalData.newData(null, value, 1), (o1, o2) -> Float.compare(o1.value(), o2.value()));
        return index > 0? data.get(index).copy() : null;
    }

    /**
     * Returns the expected value of the distribution
     * @return expected value
     */
    public float expectedValue() {
        float result = 0.0f;
        for (StatisticalData sd : data) {
            result += sd.value() * sd.probability();
        }
        return result;
    }

    /**
     * Adds a float to the values in distribution
     * @param f the float
     * @return a new ProbabilityDistribution with the operation done
     */
    public ProbabilityDistribution add(float f) {
        List<StatisticalData> newData = data.stream()
                .map((sd) -> sd.withValue(sd.value() + f))
                .collect(Collectors.toList());
        return ProbabilityDistribution.fromStatisticalData(newData);
    }

    /**
     * Does the square and returns the squared ProbabilityDistribution
     */
    public ProbabilityDistribution square() {
        List<StatisticalData> newData = data.stream()
                .map((sd) -> sd.withValue(sd.value() * sd.value()))
                .collect(Collectors.toList());
        return ProbabilityDistribution.fromStatisticalData(newData);
    }

    /**
     * Return true if the sum of all probabilities is one
     */
    public boolean isNormalized() {
        return new BigDecimal(partitionFunction(Float.POSITIVE_INFINITY)).compareTo(new BigDecimal(1.0f)) == 0;
    }

    /**
     * Returns the distribution normalized
     * @return a new ProbabilityDistribution that is normalized
     */
    public ProbabilityDistribution normalized() {
        float normalizationFactorInverse = partitionFunction(Float.POSITIVE_INFINITY);

        List<StatisticalData> normal = data.stream()
                .map((sd) -> sd.withProbability(sd.probability() / normalizationFactorInverse))
                .collect(Collectors.toList());
        return ProbabilityDistribution.fromStatisticalData(normal);
    }

    /**
     * The partition function of the distribution
     * @param x
     * @return
     */
    public float partitionFunction(float x) {
        needsOrderedData();

        float result = 0.0f;
        for (StatisticalData sd : data) {
            if (sd.value() > x) break;
            result += sd.probability();
        }
        return result;
    }

    private void orderByValue() {
        ordersData();
        Collections.sort(data, (o1, o2) -> {
            int result = Float.compare(o1.value(), o2.value());
            if (result == 0) {
                return o1.label().compareTo(o2.label());
            }
            return result;
        });
    }

    private void disordersData() {
        ordered = false;
    }

    private void ordersData() {
        ordered = true;
    }

    private void needsOrderedData() {
        if (!ordered) orderByValue();
    }

    private void sumDuplicates() {
        needsOrderedData();

        for (int i = 0; i < data.size() - 1; i++) {
            StatisticalData sd = data.get(i);
            float finalProb = sd.probability();
            BigDecimal _value = new BigDecimal(sd.value());
            while (new BigDecimal(data.get(i + 1).value()).compareTo(_value) == 0 &&
                 sd.label().equals(data.get(i + 1).label())) {
                finalProb += data.get(i + 1).probability();
                data.remove(i + 1);
            }

            data.set(i, sd.withProbability(finalProb));
        }
    }

}
