package com.example.javaproject.entity;

public class CalculationParams {
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
