package com.company.service;

public class CashRegisterService
{
    public CashRegisterService() {}

    public int[] convertStringArrayToInt(final String[] arrayToConvert)
    {
        final int[] intArray = new int[5];

        for (int i = 0; i < arrayToConvert.length - 1; i++)
        {
            intArray[i] = Integer.parseInt(arrayToConvert[i + 1]);
        }

        return intArray;
    }

    public boolean validateInput(final int number1, final int number2)
    {
        return number1 >= number2;
    }
}
