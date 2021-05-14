package com.company;

import com.company.domain.CashRegister;
import com.company.service.CashRegisterService;

import java.util.Scanner;

public class Main {

    public static void main(String[] args)
    {
        CashRegister cashRegister = new CashRegister(new CashRegisterService());
        System.out.println("java Main ...\nready\n");

        Scanner scanner = new Scanner(System.in);
        String userInput = scanner.nextLine();

        while (!"quit".equalsIgnoreCase(userInput))
        {
            final String[] userInputArray = userInput.split(" ");
            String command = userInputArray[0];

            switch (command)
            {
                case "put":
                    cashRegister.put(userInputArray);
                    break;
                case "take":
                    cashRegister.take(userInputArray);
                    break;
                case "show":
                    cashRegister.show();
                    break;
                case "change":
                    cashRegister.change(Integer.parseInt(userInputArray[1]));
                    break;
                default:
                    System.out.println("Invalid Command");
            }

            userInput = scanner.nextLine();
        }

        System.out.println("Bye");
    }
}
