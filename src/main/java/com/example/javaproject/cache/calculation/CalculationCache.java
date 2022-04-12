package com.example.javaproject.cache.calculation;

import com.example.javaproject.entity.CalculationParams;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component("calculationCache")
public class CalculationCache {
    private final HashMap<CalculationParams, Double> calculationHashMap = new HashMap<>();

    public boolean isContain(CalculationParams calculationParams) {
        return calculationHashMap.containsKey(calculationParams);
    }

    public Double getResultByParams(CalculationParams calculationParams) {
        return calculationHashMap.get(calculationParams);
    }

    public void addResultToMap(CalculationParams calculationParams, Double result) {
        calculationHashMap.put(calculationParams, result);
    }
}
