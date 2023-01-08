package com.assessment.promotion.main;

import com.assessment.promotion.model.Promotion;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PromotionEngine {

    private static final Map<Character, Integer> unitPriceMap = new HashMap<>();
    static {
        unitPriceMap.put('A', 50);
        unitPriceMap.put('B', 30);
        unitPriceMap.put('C', 20);
        unitPriceMap.put('D', 15);
    }

    public static void main(String[] args) {
        List<Character> cart = Arrays.asList('A','A','A','A', 'A',
                'B','B','B','B','B',
                'C');
        PromotionEngine promotionEngine = new PromotionEngine();
        List<Promotion> promotions = new ArrayList<>();
        promotions.add(new Promotion(3, new char[]{'A'}, 130));
        promotions.add(new Promotion(1, new char[]{'C', 'D'}, 30));
        promotions.add(new Promotion(2, new char[]{'B'}, 45));
        int totalAmount = promotionEngine.applyPromotions(cart, promotions);
        System.out.println("Total " + totalAmount);
    }

    public int applyPromotions(List<Character> carts,  List<Promotion> promotions) {
        if(carts == null || carts.isEmpty()) {
            return 0;
        }
        int totalCost = 0;
        Map<Character, Integer> skuCounts = carts.stream().collect(Collectors.groupingBy(Function.identity(),
                Collectors.collectingAndThen(Collectors.counting(), Long::intValue)));
        for (Promotion promotion : promotions) {
            boolean applicable = true;
            for (int i = 0; i < promotion.getSkus().length; i++) {
                char sku = promotion.getSkus()[i];
                int quantity = promotion.getQuantity();
                if (skuCounts.getOrDefault(sku, 0) < quantity) {
                    applicable = false;
                    break;
                }
            }

            if (applicable) {
                int promotionCount = 0;
                int quantity = skuCounts.get(promotion.getSkus()[0]);
                if (quantity >= 2 * promotion.getQuantity()) {
                    while (quantity >= promotion.getQuantity()) {
                        promotionCount++;
                        quantity = quantity - promotion.getQuantity();
                    }
                }else {
                    promotionCount++;
                }

                for (int i = 0; i < promotion.getSkus().length; i++) {
                    char sku = promotion.getSkus()[i];
                    skuCounts.put(sku, skuCounts.get(sku) - promotionCount * promotion.getQuantity());
                }
                totalCost += promotionCount * promotion.getPrice();
            }
        }

        for (Map.Entry<Character, Integer> entry : skuCounts.entrySet()) {
            char sku = entry.getKey();
            int quantity = entry.getValue();
            totalCost += unitPriceMap.get(sku) * quantity;
        }

        return totalCost;
    }
}