/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.mysimplecalculator;

/**
 *
 * @author John Clarenze M. Rosedo
 */
import java.util.*;
public class MySimpleCalculator {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choice = "y";

        System.out.println("---------------------------------------------------------");
        System.out.println(    "Welcome to My Calculator by: John Clarenze Rosedo");
        System.out.println("---------------------------------------------------------");

        while (choice.equalsIgnoreCase("y")) {
            System.out.print("Choose operation you want to use (+, -, *, /, sqrt, sin, cos, tan): ");
            String operation = scanner.next();

            double result ;
            if (operation.matches("[+\\-*/]")) {
                System.out.print("Enter first number: ");
                double num1 = scanner.nextDouble();
                System.out.print("Enter second number: ");
                double num2 = scanner.nextDouble();

                result = switch (operation) {
                    case "+" -> num1 + num2;
                    case "-" -> num1 - num2;
                    case "*" -> num1 * num2;
                    case "/" -> (num2 != 0) ? num1 / num2 : Double.NaN;
   
                    default -> 0;
                };

                if (operation.equals("/") && num2 == 0) {
                    System.out.println("Error: Cannot divide by zero.");
                    continue;
                }
            } else if (operation.equals("sqrt")) {
                System.out.print("Enter number: ");
                double num = scanner.nextDouble();
                result = (num >= 0) ? Math.sqrt(num) : Double.NaN;

                if (num < 0) {
                    System.out.println("Error: Cannot calculate square root of a negative number.");
                    continue;
                }
            } else if (operation.matches("sin|cos|tan")) {
                System.out.print("Enter angle in degrees: ");
                double angle = Math.toRadians(scanner.nextDouble());
                result = switch (operation) {
                    case "sin" -> Math.sin(angle);
                    case "cos" -> Math.cos(angle);
                    case "tan" -> Math.tan(angle);
                    default -> 0;
                };
            } else {
                System.out.println("Invalid operation. Try again.");
                continue;
            }

            System.out.println("Result: " + (result == (int) result ? (int) result : result));
            System.out.print("Do another calculation? (y/n): ");
            choice = scanner.next();
        }

        System.out.println("--------------------------------------------------");
        System.out.println("Thank you for using My Calculator!");
        System.out.println("--------------------------------------------------");
    }
}

   
   