package com.assessment.promotion.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Promotion {
    private int quantity;
    private char[] skus;
    private int price;
}
