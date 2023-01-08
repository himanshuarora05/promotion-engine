package com.assessment.promotion;

import com.assessment.promotion.main.PromotionEngine;
import com.assessment.promotion.model.Promotion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PromotionTest {
    
    private static final List<Promotion> promotions = new ArrayList<>();
    private PromotionEngine promotionEngine = new PromotionEngine();

    @BeforeAll
    public static void before() {
        promotions.add(new Promotion(3, new char[]{'A'}, 130));
        promotions.add(new Promotion(2, new char[]{'B'}, 45));
        promotions.add(new Promotion(1, new char[]{'C', 'D'}, 30));
    }

    @Test()
    public void givenItemsInCartWhenCheckoutThenReturnTotal() {
        List<Character> cart = Arrays.asList('A','B','C');
        int result = promotionEngine.applyPromotions(cart, promotions);
        Assertions.assertEquals(100, result);
    }

    @Test
    public void givenOneSkuMultipleTimesInCartEligibleForPromotionWhenCheckoutThenPromotionIsApplied() {
        List<Character> cart = Arrays.asList('A','A','A');
        int result = promotionEngine.applyPromotions(cart, promotions);
        Assertions.assertEquals(130, result);
    }

    @Test
    public void givenTwoSkuItemsInCartEligibleForPromotionWhenCheckoutThenPromotionIsApplied() {
        List<Character> cart = Arrays.asList('C', 'D');
        int result = promotionEngine.applyPromotions(cart, promotions);
        Assertions.assertEquals(30, result);
    }

    @Test
    public void givenMultipleSkuItemsInCartEligibleForPromotionWhenCheckoutThenPromotionIsApplied() {
        List<Character> cart = Arrays.asList('A','A','A','C','D');
        int result = promotionEngine.applyPromotions(cart, promotions);
        Assertions.assertEquals(160, result);
    }

    @Test
    public void givenMultipleSkuItemsInCartEligibleForMultiplePromotionsWhenCheckoutThenMultiplePromotionsAreApplied() {
        List<Character> cart = Arrays.asList('A','A','A','A','A','B','B','B','B','B','C');
        int result = promotionEngine.applyPromotions(cart, promotions);
        Assertions.assertEquals(370, result);
    }

    @Test
    public void givenMultipleSkuItemsInCartEligibleForAllPromotionsWhenCheckoutThenAllPromotionsAreApplied() {
        List<Character> cart = Arrays.asList('A','A','A','B','B','B','B','B','C', 'D');
        int result = promotionEngine.applyPromotions(cart, promotions);
        Assertions.assertEquals(280, result);
    }

    @Test
    public void givenNoItemsInCartWhenCheckoutThenZeroIsReturned() {
        int result = promotionEngine.applyPromotions(Collections.emptyList(), promotions);
        Assertions.assertEquals(0, result);
    }




}
