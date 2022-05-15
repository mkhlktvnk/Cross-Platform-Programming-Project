package com.example.javaproject.controllers.calculation;

import com.example.javaproject.cache.calculation.CalculationCache;
import com.example.javaproject.entity.counter.RequestCounter;
import com.example.javaproject.entity.params.CalculationParams;
import com.example.javaproject.exceptions.calculation.WrongArgsOrderException;
import com.example.javaproject.services.CalculatorService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;

@RestController
public class CalculationController {
    private final CalculatorService calculatorService;
    private final CalculationCache calculationCache;
    private final Logger logger = LogManager.getLogger(CalculationController.class);

    @Autowired
    public CalculationController(CalculatorService calculatorService, CalculationCache calculationCache) {
        this.calculatorService = calculatorService;
        this.calculationCache = calculationCache;
    }

    @RequestMapping(value = "/calculation", method = RequestMethod.GET, produces = "application/json")
    public HashMap<String, Double> calculation(
            @RequestParam(name = "lv") double low,
            @RequestParam(name = "hv") double high)
            throws WrongArgsOrderException {
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

    @RequestMapping(value = "/calculation", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> calculateBulkParams(@Valid @RequestBody List<CalculationParams> calculationParamsList) {
        List<Double> resultList = new LinkedList<>();
        calculationParamsList.forEach((calculationParam) -> {
            try {
                resultList.add(calculatorService.performCalculation(calculationParam));
            } catch (WrongArgsOrderException e) {
                logger.error("Wrong args order");
            }
        });
        Double min = calculatorService.getMinResult(resultList);
        Double max = calculatorService.getMaxResult(resultList);
        Double sum = calculatorService.calculateSum(resultList);
        return new ResponseEntity<>(resultList + "\nsum: " + sum + "\nmin: " + min + "\nmax: " + max,
                HttpStatus.OK);
    }
}
