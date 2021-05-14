package com.company.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CashRegisterServiceTest
{
    private CashRegisterService cashRegisterService;

    @BeforeEach
    void setUp()
    {
        this.cashRegisterService = new CashRegisterService();
    }

    @Test
    void convertStringArrayToInt()
    {
        final String[] testStringArray = new String[]{"put", "3", "7", "5", "2", "9"};
        int[] actualArray = cashRegisterService.convertStringArrayToInt(testStringArray);

        assertEquals(5, cashRegisterService.convertStringArrayToInt(testStringArray).length);
        assertArrayEquals(actualArray, cashRegisterService.convertStringArrayToInt(testStringArray));
    }

    @Test
    void shoultReturnTrueWhenNumbersAreEqual()
    {
        final int num1 = 14;
        final int num2 = 14;

        assertTrue(cashRegisterService.validateInput(num1, num2));
    }
    @Test
    void shouldReturnTrueWhenNum1IsHigherThanNum2()
    {
        final int num1 = 14;
        final int num2 = 9;

        assertTrue(cashRegisterService.validateInput(num1, num2));
    }
    @Test
    void shouldReturnFalseWhenNum1IsLowerThanNum2()
    {
        final int num1 = 10;
        final int num2 = 14;

        assertFalse(cashRegisterService.validateInput(num1, num2));
    }
}