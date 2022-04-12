package com.example.javaproject.calculation;

import com.example.javaproject.entity.CalculationParams;
import com.example.javaproject.services.CalculatorService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CalculatorServiceTest {
    private final CalculatorService calculatorService = new CalculatorService();

    @Test
    void testPerformCalculation() {
        double low = -758000.0;
        double high = 1.0E8;
        double expected = 4.8572108E7;
        CalculationParams calculationParams = new CalculationParams(low, high);
        assertEquals(expected, calculatorService.performCalculation(calculationParams));
    }
}
