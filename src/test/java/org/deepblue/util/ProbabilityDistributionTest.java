package org.deepblue.util;

import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.Assert.*;

public class ProbabilityDistributionTest {

    @Test
    public void testNormalization() {
        StatisticalData[] data = new StatisticalData[] {
            StatisticalData.newData("a", 0.4f, 0.2f),
            StatisticalData.newData("b", 1.0f, 0.3f),
            StatisticalData.newData("c", 0.3f, 0.5f),
            StatisticalData.newData("d", 1.0f, 0.4f)
        };
        ProbabilityDistribution pb = ProbabilityDistribution.fromStatisticalData(Arrays.asList(data));
        assertTrue(new BigDecimal(pb.partitionFunction(Float.POSITIVE_INFINITY)).compareTo(new BigDecimal(1.4f)) == 0);
        assertFalse(pb.isNormalized());

        ProbabilityDistribution npb = pb.normalized();
        assertTrue(npb.isNormalized());
        assertFalse(pb.isNormalized());
        assertTrue(new BigDecimal(pb.partitionFunction(Float.POSITIVE_INFINITY)).compareTo(new BigDecimal(1.4f)) == 0);

    }

    @Test
    public void testDuplicates() {
        StatisticalData[] data = new StatisticalData[] {
                StatisticalData.newData("a", 0.4f, 0.2f),
                StatisticalData.newData("b", 1.0f, 0.3f),
                StatisticalData.newData("a", 0.4f, 0.5f),
                StatisticalData.newData("d", 1.0f, 0.4f)
        };
        ProbabilityDistribution pb = ProbabilityDistribution.fromStatisticalData(Arrays.asList(data));
    }

}