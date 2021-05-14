package com.company.integrationtest;

import com.company.domain.CashRegister;
import com.company.service.CashRegisterService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CashRegisterIntegrationTest
{
    private final CashRegisterService cashRegisterService = new CashRegisterService();
    private final CashRegister cashRegister = new CashRegister(cashRegisterService);

    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    private final PrintStream successOutput = System.out;
    private final PrintStream errorOutput = System.out;

    @BeforeEach
    void setUp()
    {
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
    void givenValidInputShouldShowCorrectOutput()
    {
        final String testInput = "put 5 4 4 3 10";
        final String[] testInputArray = testInput.split(" ");

        final String testTakeInput = "take 1 2 1 3 4";
        final String[] testTakeArray = testTakeInput.split(" ");

        final String expectedString = "$176 5 4 4 3 10" + System.lineSeparator() +
                "$121 4 2 3 0 6" + System.lineSeparator() + "0 0 2 0 2" + System.lineSeparator() + "$109 4 2 1 0 4";

        cashRegister.put(testInputArray);
        cashRegister.take(testTakeArray);
        cashRegister.change(12);
        cashRegister.show();

        assertEquals(expectedString, outContent.toString().trim());
    }

    @Test
    void givenInvalidChangeRequestShouldPrintSorry()
    {
        final String testInput = "put 5 4 4 3 10";
        final String[] testInputArray = testInput.split(" ");

        final String testTakeInput = "take 1 2 1 3 4";
        final String[] testTakeArray = testTakeInput.split(" ");

        final String expectedString = "$176 5 4 4 3 10" + System.lineSeparator() +
                "$121 4 2 3 0 6" + System.lineSeparator() + "sorry";

        cashRegister.put(testInputArray);
        cashRegister.take(testTakeArray);
        cashRegister.change(130);

        assertEquals(expectedString, outContent.toString().trim());
    }
}
