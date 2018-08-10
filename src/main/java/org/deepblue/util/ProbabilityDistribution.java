package org.deepblue.util;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ProbabilityDistribution {

    public static ProbabilityDistribution fromStatisticalData(List<? extends StatisticalData> stats) {
        ProbabilityDistribution pd = new ProbabilityDistribution();
        for (StatisticalData sd : stats) {
            pd.data.add(sd);
        }
        return pd;
    }

    private List<StatisticalData> data;

    private boolean ordered = false;

    public ProbabilityDistribution() {
        data = new ArrayList<>();
    }

    public boolean isNormalized() {
        return new BigDecimal(partitionFunction(Float.POSITIVE_INFINITY)).compareTo(new BigDecimal(1.0f)) == 0;
    }

    public ProbabilityDistribution normalized() {
        float normalizationFactorInverse = partitionFunction(Float.POSITIVE_INFINITY);

        List<StatisticalData> normal = data.stream().map((sd) -> sd.withProbability(sd.probability() / normalizationFactorInverse)).collect(Collectors.toList());
        return ProbabilityDistribution.fromStatisticalData(normal);
    }

    public float partitionFunction(float weight) {
        needsOrderedData();

        float result = 0.0f;
        for (StatisticalData sd : data) {
            if (sd.weight() > weight) break;
            result += sd.probability();
        }
        return result;
    }

    private void orderByWeight() {
        ordersData();
        Collections.sort(data, (o1, o2) -> Float.compare(o1.weight(), o2.weight()));
    }

    private void disordersData() {
        ordered = false;
    }

    private void ordersData() {
        ordered = true;
    }

    private void needsOrderedData() {
        if (!ordered) orderByWeight();
    }

}
