package com.github.phanikb.nvd.cli.processor.pc;

import org.junit.jupiter.api.Test;

import com.github.phanikb.nvd.common.NvdException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculateTotalResultsFunctionTest {

    @Test
    void testFunctionalInterfaceWithVariousFixedResults() throws NvdException {
        CalculateTotalResultsFunction calculateTotal1 = () -> 100;
        assertEquals(100, calculateTotal1.calculateTotalResults());

        CalculateTotalResultsFunction calculateTotal2 = () -> 1000;
        assertEquals(1000, calculateTotal2.calculateTotalResults());

        CalculateTotalResultsFunction calculateTotal3 = () -> 0;
        assertEquals(0, calculateTotal3.calculateTotalResults());

        CalculateTotalResultsFunction calculateTotal4 = () -> -50;
        assertEquals(-50, calculateTotal4.calculateTotalResults());
    }

    @Test
    void testFunctionalInterfaceWithComplexCalculation() throws NvdException {
        CalculateTotalResultsFunction calculateTotal = () -> {
            int base = 50;
            int multiplier = 3;
            return base * multiplier + 25;
        };

        int result = calculateTotal.calculateTotalResults();
        assertEquals(175, result); // 50 * 3 + 25 = 175
    }

    @Test
    void testFunctionalInterfaceWithMethodReference() throws NvdException {
        CalculateTotalResultsFunction calculateTotal = this::calculateFixedTotal;

        int result = calculateTotal.calculateTotalResults();
        assertEquals(42, result);
    }

    @Test
    void testFunctionalInterfaceThrowingException() {
        CalculateTotalResultsFunction calculateTotal = () -> {
            throw new NvdException("Calculation failed");
        };

        NvdException exception = assertThrows(NvdException.class, calculateTotal::calculateTotalResults);

        assertEquals("Calculation failed", exception.getMessage());
    }

    @Test
    void testFunctionalInterfaceWithExternalData() throws NvdException {
        int[] data = {10, 20, 30, 40, 50};
        CalculateTotalResultsFunction calculateTotal = () -> {
            int sum = 0;
            for (int value : data) {
                sum += value;
            }
            return sum;
        };

        int result = calculateTotal.calculateTotalResults();
        assertEquals(150, result); // 10 + 20 + 30 + 40 + 50 = 150
    }

    @Test
    void testFunctionalInterfaceComposition() throws NvdException {
        CalculateTotalResultsFunction baseCalculation = () -> 100;
        CalculateTotalResultsFunction bonusCalculation = () -> 25;

        CalculateTotalResultsFunction combinedCalculation =
                () -> baseCalculation.calculateTotalResults() + bonusCalculation.calculateTotalResults();

        int result = combinedCalculation.calculateTotalResults();
        assertEquals(125, result);
    }

    @Test
    void testFunctionalInterfaceWithExceptionInCalculation() {
        CalculateTotalResultsFunction calculateTotal = () -> {
            throw new NvdException("Division by zero error");
        };

        NvdException exception = assertThrows(NvdException.class, calculateTotal::calculateTotalResults);

        assertEquals("Division by zero error", exception.getMessage());
    }

    private int calculateFixedTotal() {
        return 42;
    }
}
