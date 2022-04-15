package com.example.javaproject.controllers.calculation;

import com.example.javaproject.cache.calculation.CalculationCache;
import com.example.javaproject.entity.counter.RequestCounter;
import com.example.javaproject.entity.params.CalculationParams;
import com.example.javaproject.exceptions.calculation.WrongArgumentsOrderException;
import com.example.javaproject.services.CalculatorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.concurrent.Semaphore;

@RestController
public class CalculationController {
    private final CalculatorService calculatorService;
    private final CalculationCache calculationCache;
    private final Logger logger = LogManager.getLogger(CalculationController.class);

    @Autowired
    public CalculationController(CalculatorService calculatorService, CalculationCache calculationCache1) {
        this.calculatorService = calculatorService;
        this.calculationCache = calculationCache1;
    }

    @RequestMapping(value = "/calculation", method = RequestMethod.GET, produces = "application/json")
    public HashMap<String, Double> calculation(
            @RequestParam(name = "lv") double low,
            @RequestParam(name = "hv") double high)
            throws WrongArgumentsOrderException {
        if (low > high) {
            logger.error("Error! highValue is bigger than lowValue");
            throw new WrongArgumentsOrderException("Wrong arguments order");
        }
        CalculationParams calculationParams = new CalculationParams(low, high);
        Double result = 0.0;

        Semaphore semaphore = new Semaphore(1, true);

        if (calculationCache.isContain(calculationParams)) {
            result = calculationCache.getResultByParams(calculationParams);
            logger.info("Result taken from cache");
        } else {
            result = calculatorService.performCalculation(calculationParams);
            logger.info("Calculation performed\nResult saved to map");
            calculationCache.addResultToMap(calculationParams, result);
        }
        try {
            semaphore.acquire();
            RequestCounter.inc();
            semaphore.release();
        } catch (InterruptedException e) {
            logger.warn(Thread.currentThread().getName() + "was interrupted");
        }

        Double finalResult = result;

        return new HashMap<>() {{
            put("low-value", low);
            put("high-value", high);
            put("result", finalResult);
        }};
    }
}
