package com.company.service;

public interface CashRegisterInterface
{
    void show();
    void put(final String[] inputArray);
    void take(final String[] inputArray);
    void change(final int amountRequested);
}
