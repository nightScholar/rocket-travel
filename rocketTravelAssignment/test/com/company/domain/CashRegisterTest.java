package com.company.domain;

import com.company.service.CashRegisterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;

class CashRegisterTest
{
    private CashRegister cashRegister;
    private final CashRegisterService cashRegisterService = mock(CashRegisterService.class);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream successOutput = System.out;
    private final PrintStream errorOutput = System.out;

    @BeforeEach
    void setUp()
    {
        this.cashRegister = new CashRegister(cashRegisterService);
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void restoreStreams()
    {
        System.setOut(successOutput);
        System.setOut(errorOutput);
    }


    @Test
    void put()
    {
        final String testInput = "put 1 2 3 4 5";
        final String[] testInputArray = testInput.split(" ");
        final int[] expectedIntArray = new int[]{1, 2, 3, 4, 5};

        when(cashRegisterService.convertStringArrayToInt(testInputArray))
                .thenReturn(expectedIntArray);

        cashRegister.put(testInputArray);

        assertEquals(1, cashRegister.getTwentyDollarBills());
        assertEquals(2, cashRegister.getTenDollarBills());
        assertEquals(3, cashRegister.getFiveDollarBills());
        assertEquals(4, cashRegister.getTwoDollarBills());
        assertEquals(5, cashRegister.getOneDollarBills());
        assertEquals(68, cashRegister.getTotal());
    }

    @Test
    void take()
    {
        final String testPutInput = "put 2 3 4 4 5";
        final String[] testPutArray = testPutInput.split(" ");
        final int[] expectedPutIntArray = new int[]{2, 3, 4, 4, 5};

        final String testTakeInput = "take 3 1 4 2 6";
        final String[] testTakeArray = testTakeInput.split(" ");
        final int[] expectedTakeIntArray = new int[]{3, 1, 4, 2, 6};

        when(cashRegisterService.convertStringArrayToInt(testPutArray))
                .thenReturn(expectedPutIntArray);

        cashRegister.put(testPutArray);

        when(cashRegisterService.convertStringArrayToInt(testTakeArray))
                .thenReturn(expectedTakeIntArray);

        when(cashRegisterService.validateInput(cashRegister.getTwentyDollarBills(), expectedTakeIntArray[0])).thenReturn(false);
        when(cashRegisterService.validateInput(cashRegister.getTenDollarBills(), expectedTakeIntArray[1])).thenReturn(true);
        when(cashRegisterService.validateInput(cashRegister.getFiveDollarBills(), expectedTakeIntArray[2])).thenReturn(true);
        when(cashRegisterService.validateInput(cashRegister.getTwoDollarBills(), expectedTakeIntArray[3])).thenReturn(true);
        when(cashRegisterService.validateInput(cashRegister.getOneDollarBills(), expectedTakeIntArray[4])).thenReturn(false);

        cashRegister.take(testTakeArray);

        assertEquals(2, cashRegister.getTwentyDollarBills());
        assertEquals(2, cashRegister.getTenDollarBills());
        assertEquals(0, cashRegister.getFiveDollarBills());
        assertEquals(2, cashRegister.getTwoDollarBills());
        assertEquals(5, cashRegister.getOneDollarBills());
        assertEquals(69, cashRegister.getTotal());
    }

    @Test
    void change()
    {
        final String testInput = "put 1 0 3 4 0";
        final String[] testInputArray = testInput.split(" ");
        final int[] expectedIntArray = new int[]{1, 0, 3, 4, 0};

        when(cashRegisterService.convertStringArrayToInt(testInputArray)).thenReturn(expectedIntArray);

        cashRegister.put(testInputArray);

        final String expectedString = "$43 1 0 3 4 0" + System.lineSeparator() + "0 0 1 3 0";

        cashRegister.change(11);

        assertEquals(expectedString, outContent.toString().trim());
    }

    @Test
    void testChangeWithThirteenInRegister()
    {
        final String testInput = "put 0 1 0 0 3";
        final String[] testInputArray = testInput.split(" ");
        final int[] expectedIntArray = new int[]{0, 1, 0, 0, 3};

        when(cashRegisterService.convertStringArrayToInt(testInputArray))
                .thenReturn(expectedIntArray);

        cashRegister.put(testInputArray);

        final String expectedString = "$13 0 1 0 0 3" + System.lineSeparator() + "0 1 0 0 3";

        cashRegister.change(13);

        assertEquals(expectedString, outContent.toString().trim());
    }

    @Test
    void testNotEnoughChangeInRegister()
    {
        final String testInput = "put 1 1 2 2 3";
        final String[] testInputArray = testInput.split(" ");
        final int[] expectedIntArray = new int[]{1, 1, 3, 2, 3};

        when(cashRegisterService.convertStringArrayToInt(testInputArray))
                .thenReturn(expectedIntArray);

        cashRegister.put(testInputArray);

        final String expectedString = "$52 1 1 3 2 3" + System.lineSeparator() + "sorry";

        cashRegister.change(70);

        assertEquals(expectedString, outContent.toString().trim());
    }

    @Test
    void show()
    {
        final String testPutInput = "put 1 2 3 4 5";
        final String[] testPutArray = testPutInput.split(" ");
        final int[] expectedPutIntArray = new int[]{1, 2, 3, 4, 5};

        when(cashRegisterService.convertStringArrayToInt(testPutArray))
                .thenReturn(expectedPutIntArray);

        cashRegister.put(testPutArray);

        final String expectedString = "$" + "68" + " " + "1" + " " + "2" + " "
                + "3" + " " + "4" + " " + "5";

        assertEquals(expectedString, outContent.toString().trim());
    }

    @Test
    void getTotal()
    {
        final String testPutInput = "put 2 2 2 2 2";
        final String[] testPutArray = testPutInput.split(" ");
        final int[] expectedPutIntArray = new int[]{2, 2, 2, 2, 2};

        when(cashRegisterService.convertStringArrayToInt(testPutArray))
                .thenReturn(expectedPutIntArray);

        cashRegister.put(testPutArray);

        assertEquals(76, cashRegister.getTotal());
    }

    @Test
    void updateTotal()
    {
        final String testPutInput = "put 1 2 3 4 5";
        final String[] testPutArray = testPutInput.split(" ");
        final int[] expectedPutIntArray = new int[]{1, 2, 3, 4, 5};

        when(cashRegisterService.convertStringArrayToInt(testPutArray))
                .thenReturn(expectedPutIntArray);

        cashRegister.put(testPutArray);

        assertEquals(68, cashRegister.getTotal());
    }
}