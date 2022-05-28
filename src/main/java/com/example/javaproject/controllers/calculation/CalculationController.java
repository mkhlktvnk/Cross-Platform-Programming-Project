package com.example.javaproject.controllers.calculation;

import com.example.javaproject.cache.calculation.CalculationCache;
import com.example.javaproject.entity.params.CalculationParams;
import com.example.javaproject.entity.result.CalculationResult;
import com.example.javaproject.exceptions.calculation.MinValueGreaterThanMaxValueException;
import com.example.javaproject.repository.CalculationResultsRepository;
import com.example.javaproject.exceptions.calculation.services.CalculationService;
import com.example.javaproject.thread.RequestCounterThread;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

@RestController
public class CalculationController {
    private final CalculationService calculationService;
    private final CalculationCache calculationCache;
    private final Logger logger = LogManager.getLogger(CalculationController.class);
    private final CalculationResultsRepository calculationResultsRepository;

    @Autowired
    public CalculationController(
            CalculationService calculationService,
            CalculationCache calculationCache,
            CalculationResultsRepository calculationResultsRepository
    ) {
        this.calculationService = calculationService;
        this.calculationCache = calculationCache;
        this.calculationResultsRepository = calculationResultsRepository;
    }

    @RequestMapping(value = "/calculation", method = RequestMethod.GET, produces = "application/json")
    public HashMap<String, Double> calculation(
            @RequestParam(name = "min") Double min,
            @RequestParam(name = "max") Double max
    ) throws MinValueGreaterThanMaxValueException {
        if (min > max) {
            throw new MinValueGreaterThanMaxValueException("Min greater than max");
        }
        CalculationParams calculationParams = new CalculationParams(min, max);
        Double result = 0.0;
        if (calculationCache.isContain(calculationParams)) {
            result = calculationCache.getResultByParams(calculationParams);
            logger.info("Result taken from cache");
        } else {
            result = calculationService.performCalculation(calculationParams);
            logger.info("Calculation performed\nResult saved to map");
            calculationCache.addResultToMap(calculationParams, result);
        }
        RequestCounterThread requestCounterThread = new RequestCounterThread("Request-Counter-Thread");
        requestCounterThread.start();
        Double finalResult = result;
        CalculationResult calculationResult = new CalculationResult(finalResult);
        calculationResultsRepository.save(calculationResult);
        return new HashMap<>() {{
            put("min-value", min);
            put("max-value", max);
            put("result", finalResult);
        }};
    }

    @RequestMapping(value = "/calculation", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<?> calculateBulkParams(@RequestBody List<CalculationParams> calculationParamsList) {
        List<Double> resultList = new LinkedList<>();
        calculationParamsList.forEach((calculationParam) -> {
            try {
                resultList.add(calculationService.performCalculation(calculationParam));
            } catch (MinValueGreaterThanMaxValueException exception) {
                logger.error("MinValueGreaterThanMaxValueException");
            } catch (NullPointerException exception) {
                logger.error("NullPointerException");
            }
        });
        resultList.forEach((result) -> {
            calculationResultsRepository.save(new CalculationResult(result));
        });
        Double min = calculationService.getMinResult(resultList);
        Double max = calculationService.getMaxResult(resultList);
        Double sum = calculationService.getSum(resultList);
        return new ResponseEntity<>(resultList + "\nsum: " + sum + "\nmin: " + min + "\nmax: " + max,
                HttpStatus.OK);
    }
}
