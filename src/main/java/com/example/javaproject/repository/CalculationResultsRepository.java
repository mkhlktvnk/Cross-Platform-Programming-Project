package com.example.javaproject.repository;

import com.example.javaproject.entity.result.CalculationResult;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CalculationResultsRepository extends JpaRepository<CalculationResult, Integer> {

}
