package com.api.StudyU_Flow.domain.tools;

import java.util.Collections;
import java.util.List;

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
}
