package com.example.javaproject.entity.params;

import java.util.Objects;

public class CalculationParams {
    private final double minValue;
    private final double maxValue;

    public CalculationParams(double minValue, double maxValue) {
        this.minValue = maxValue;
        this.maxValue = minValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public double getMaxValue() {
        return maxValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalculationParams that = (CalculationParams) o;
        return Double.compare(that.minValue, minValue) == 0 && Double.compare(that.maxValue, maxValue) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(minValue, maxValue);
    }
}
