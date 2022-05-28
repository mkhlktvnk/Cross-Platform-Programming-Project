package com.example.javaproject.calculation;

import com.example.javaproject.entity.params.CalculationParams;
import com.example.javaproject.exceptions.calculation.services.CalculationService;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


public class CalculatorServiceTest {
    private final CalculationService calculationService = new CalculationService();
    @Test
    void testPerformCalculation() {
        double low = -758000.0;
        double high = 1.0E8;
        double expected = 4.8572121399913296E7;
        CalculationParams calculationParams = new CalculationParams(low, high);
        assertEquals(expected, calculationService.performCalculation(calculationParams));
    }
}
