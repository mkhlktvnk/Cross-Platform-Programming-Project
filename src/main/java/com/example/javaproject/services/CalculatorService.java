package com.example.javaproject.services;
import com.example.javaproject.entity.params.CalculationParams;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
public class CalculatorService {
    private static final Logger logger = LogManager.getLogger(CalculatorService.class);

    private Double mathFunction(Double x) {
        return Math.sin(x);
    }

    public Double performCalculation(CalculationParams calculationParams) {
        int n = 100;
        double h = (calculationParams.getHighValue() - calculationParams.getLowValue()) / 2;
        double sum = 0.0;
        for (int i = 1; i <= n; i++) {
            sum += 2 * mathFunction(calculationParams.getLowValue() + i * h);
        }
        sum *= h / 2;
        return sum;
    }

    public Double calculateSum(List<Double> resultList) {
        Double sum = 0.0;
        for (Double result : resultList) {
            sum += result;
        }
        return sum;
    }

    public Double getMinResult(List<Double> resultList) {
        double min = 0.0;
        if (!resultList.isEmpty()) {
            min = resultList.stream().mapToDouble(Double::doubleValue).min().getAsDouble();
        }
        return min;
    }

    public Double getMaxResult(List<Double> resultList) {
        double max = 0.0;
        if (!resultList.isEmpty()) {
            max = resultList.stream().mapToDouble(Double::doubleValue).max().getAsDouble();
        }
        return max;
    }

    public Double getSum(List<Double> resultList) {
        double sum = 0.0;
        if (!resultList.isEmpty()) {
            sum = resultList.stream().mapToDouble(Double::doubleValue).sum();
        }
        return sum;
    }
}
