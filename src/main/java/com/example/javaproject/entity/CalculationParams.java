package com.example.javaproject.entity;

import java.util.Objects;

public class CalculationParams {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CalculationParams that = (CalculationParams) o;
        return Double.compare(that.lowValue, lowValue) == 0 && Double.compare(that.highValue, highValue) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(lowValue, highValue);
    }

    private final double lowValue;
    private final double highValue;

    public CalculationParams(double lowValue, double highValue) {
        this.lowValue = lowValue;
        this.highValue = highValue;
    }

    public double getHighValue() {
        return highValue;
    }

    public double getLowValue() {
        return lowValue;
    }
}
