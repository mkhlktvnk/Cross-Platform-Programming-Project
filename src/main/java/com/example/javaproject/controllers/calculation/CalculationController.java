package com.example.javaproject.controllers.calculation;

import com.example.javaproject.cache.calculation.CalculationCache;
import com.example.javaproject.entity.CalculationParams;
import com.example.javaproject.exceptions.calculation.WrongArgumentsOrderException;
import com.example.javaproject.services.CalculatorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
public class CalculationController {
    private final CalculatorService calculatorService;

    @Autowired
    public CalculationController(CalculatorService calculatorService) {
        this.calculatorService = calculatorService;
    }

    @RequestMapping(value = "/calculation", method = RequestMethod.GET, produces = "application/json")
    public HashMap<String, Double> calculation(
            @RequestParam(name = "lv") double low,
            @RequestParam(name = "hv") double high)
            throws WrongArgumentsOrderException {
        if (low > high) {
            throw new WrongArgumentsOrderException("Wrong arguments order");
        }
        CalculationParams calculationParams = new CalculationParams(low, high);
        Double result = calculatorService.performCalculation(calculationParams);

        return new HashMap<>() {{
            put("low-value", low);
            put("high-value", high);
            put("result", result);
        }};
    }
}
