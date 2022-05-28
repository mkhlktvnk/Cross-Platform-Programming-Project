package com.example.javaproject.entity.result;

import javax.persistence.*;

@Entity
@Table(name = "results_table")
public class CalculationResult {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Column(name = "result")
    private Double result;

    public CalculationResult(Double result) {
        this.result = result;
    }

    public CalculationResult() {

    }

    public Integer getId() {
        return id;
    }

    public Double getResult() {
        return result;
    }
}
