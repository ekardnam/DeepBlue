package org.deepblue.util;

import java.math.BigDecimal;
import java.util.Objects;

public abstract class StatisticalData {

    public abstract String label();

    public abstract float weight();

    public abstract float probability();

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StatisticalData)) return false;
        StatisticalData sd = (StatisticalData) other;
        return Objects.equals(label(), sd.label()) &&
               new BigDecimal(weight()).compareTo(new BigDecimal(sd.weight())) == 0 &&
               new BigDecimal(probability()).compareTo(new BigDecimal(sd.probability())) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(label(), weight(), probability());
    }

    public StatisticalData withProbability(float probability) {
        return new DataContainer(label(), weight(), probability);
    }

    public StatisticalData withWeight(float weight) {
        return new DataContainer(label(), weight, probability());
    }

    public static StatisticalData newData(String label, float weight, float probability) {
        return new DataContainer(label, weight, probability);
    }

    static class DataContainer extends StatisticalData {

        private String label;

        private float weight;

        private float probability;

        public DataContainer(String label, float weight, float probability) {
            this.label = label;
            this.weight = weight;
            this.probability = probability;
        }

        @Override
        public String label() {
            return label;
        }

        @Override
        public float weight() {
            return weight;
        }

        @Override
        public float probability() {
            return probability;
        }
    }

}
