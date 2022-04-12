package com.example.javaproject.services;
import com.example.javaproject.entity.CalculationParams;
import org.springframework.stereotype.Component;

@Component
public class CalculatorService {
    private Double mathFunction(Double x) {
        return Math.sin(x);
    }

    public double performCalculation(CalculationParams calculationParams) {
        float sum = 0.0F;
        int n = 100;
        double h = (calculationParams.getHighValue() - calculationParams.getLowValue()) / 2;
        for (int i = 1; i <= n; i++) {
            sum += 2 * mathFunction(calculationParams.getLowValue() + i * h);
        }
        sum *= h / 2;
        return sum;
    }
}
