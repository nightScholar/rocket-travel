package com.company.domain;

import com.company.service.CashRegisterInterface;
import com.company.service.CashRegisterService;

public class CashRegister implements CashRegisterInterface
{
    private int twentyDollarBills;
    private int tenDollarBills;
    private int fiveDollarBills;
    private int twoDollarBills;
    private int oneDollarBills;
    private int total;

    private final CashRegisterService cashRegisterService;

    public CashRegister(final CashRegisterService cashRegisterService)
    {
        this.twentyDollarBills = 0;
        this.tenDollarBills = 0;
        this.fiveDollarBills = 0;
        this.twoDollarBills = 0;
        this.oneDollarBills = 0;
        this.cashRegisterService = cashRegisterService;
    }

    public int getTwentyDollarBills()
    {
        return twentyDollarBills;
    }

    public int getTenDollarBills()
    {
        return tenDollarBills;
    }

    public int getFiveDollarBills()
    {
        return fiveDollarBills;
    }

    public int getTwoDollarBills()
    {
        return twoDollarBills;
    }

    public int getOneDollarBills()
    {
        return oneDollarBills;
    }

    @Override
    public void put(final String[] inputArray)
    {
        int[] intArray = cashRegisterService.convertStringArrayToInt(inputArray);

        this.twentyDollarBills += intArray[0];
        this.tenDollarBills += intArray[1];
        this.fiveDollarBills += intArray[2];
        this.twoDollarBills += intArray[3];
        this.oneDollarBills += intArray[4];
        updateTotal();
        this.show();
    }

    @Override
    public void take(final String[] inputArray)
    {
        int[] intArray = cashRegisterService.convertStringArrayToInt(inputArray);

        if (cashRegisterService.validateInput(this.twentyDollarBills, intArray[0])) {
            this.twentyDollarBills -= intArray[0];
        }
        if (cashRegisterService.validateInput(this.tenDollarBills, intArray[1])) {
            this.tenDollarBills -= intArray[1];
        }
        if (cashRegisterService.validateInput(this.fiveDollarBills, intArray[2])) {
            this.fiveDollarBills -= intArray[2];
        }
        if (cashRegisterService.validateInput(this.twoDollarBills, intArray[3])) {
            this.twoDollarBills -= intArray[3];
        }
        if (cashRegisterService.validateInput(this.oneDollarBills, intArray[4])) {
            this.oneDollarBills -= intArray[4];
        }
        updateTotal();
        this.show();
    }

    @Override
    public void show()
    {
        System.out.println(("$" + this.total + " " + this.twentyDollarBills + " " + this.tenDollarBills + " " +
                this.fiveDollarBills + " " + this.twoDollarBills + " " + this.oneDollarBills));
    }

    @Override
    public void change(final int amountRequested)
    {
        if (amountRequested > this.getTotal())
        {
            System.out.println("sorry");
        }
        else
        {
            for (int numberOfTwenties = 0; numberOfTwenties <= this.twentyDollarBills; numberOfTwenties++)
            {
                for (int numberOfTens = 0; numberOfTens <= this.tenDollarBills; numberOfTens++)
                {
                    for (int numberOfFives = 0; numberOfFives <= this.fiveDollarBills; numberOfFives++)
                    {
                        for (int numberOfTwos = 0; numberOfTwos <= this.twoDollarBills; numberOfTwos++)
                        {
                            for (int numberOfSingles = 0; numberOfSingles <= this.oneDollarBills; numberOfSingles++)
                            {
                                int subTotal = (numberOfTwenties * 20) + (numberOfTens * 10) + (numberOfFives * 5) +
                                        (numberOfTwos * 2) + numberOfSingles;

                                if (subTotal == amountRequested)
                                {
                                    System.out.println(numberOfTwenties + " " + numberOfTens + " " + numberOfFives
                                            + " " + numberOfTwos + " " + numberOfSingles);

                                    this.twentyDollarBills -= numberOfTwenties;
                                    this.tenDollarBills -= numberOfTens;
                                    this.fiveDollarBills -= numberOfFives;
                                    this.twoDollarBills -= numberOfTwos;
                                    this.oneDollarBills -= numberOfSingles;
                                    updateTotal();
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public int getTotal()
    {
        return this.total = (this.twentyDollarBills * 20) + (this.tenDollarBills * 10) +
                (this.fiveDollarBills * 5) + (this.twoDollarBills * 2) + (this.oneDollarBills);
    }

    public void updateTotal()
    {
        this.total = (this.twentyDollarBills * 20) + (this.tenDollarBills * 10) +
                (this.fiveDollarBills * 5) + (this.twoDollarBills * 2) + (this.oneDollarBills);
    }

}
