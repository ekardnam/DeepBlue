package org.deepblue.util;

import org.deepblue.DeepBlue;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * A class that represents some statistical data
 */
public abstract class StatisticalData {

    /**
     * The data label
     * IMPORTANT data with same label MUST have same value
     * Adding this check at runtime is computationally as much expensive as most operation on
     * this kind of that, so this check is not included by default
     * @return
     */
    public abstract String label();

    public abstract float value();

    public abstract float probability();

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StatisticalData)) return false;
        StatisticalData sd = (StatisticalData) other;
        return Objects.equals(label(), sd.label()) &&
               new BigDecimal(value()).compareTo(new BigDecimal(sd.value())) == 0 &&
               new BigDecimal(probability()).compareTo(new BigDecimal(sd.probability())) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(label(), value(), probability());
    }

    public StatisticalData withProbability(float probability) {
        return new DataContainer(label(), value(), probability);
    }

    public StatisticalData withValue(float value) {
        return new DataContainer(label(), value, probability());
    }

    public static StatisticalData newData(String label, float value, float probability) {
        return new DataContainer(label, value, probability);
    }

    static class DataContainer extends StatisticalData {

        private String label;

        private float value;

        private float probability;

        public DataContainer(String label, float value, float probability) {
            this.label = label;
            this.value = value;
            this.probability = probability;
        }

        @Override
        public String label() {
            return label;
        }

        @Override
        public float value() {
            return value;
        }

        @Override
        public float probability() {
            return probability;
        }
    }

}
