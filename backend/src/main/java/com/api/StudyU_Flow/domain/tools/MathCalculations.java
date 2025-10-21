package com.api.StudyU_Flow.domain.tools;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

public class MathCalculations {

    public static double getPercentage(double part, double total) {
        return (part / total) * 100;
    }

    public static Integer findMaximumInCollection(List<Integer> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            throw new IllegalArgumentException("The list cannot be empty.");
        }
        return Collections.max(numbers);
    }
    public static Map<Long, Double> getTop5EntriesByValue(Map<Long, Double> originalMap) {
        if (originalMap == null || originalMap.isEmpty()) {
            return new HashMap<>();
        }

        return originalMap.entrySet().stream()

                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))

                .limit(5)

                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    public static Double roundDouble(Double value, int decimals) {
        if (value == null) {
            return null;
        }

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(decimals, RoundingMode.HALF_UP);

        return bd.doubleValue();
    }
}
