/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cashregister;

/**
 *
 * @author Jc 
 */
import java.util.*;

public class Cashregister {
    static Scanner jcr = new Scanner(System.in);
    static ArrayList<String> cashRegister = new ArrayList<>();
   

    public static double getValidPeso(String message) {
        double amount;
        while (true) {
            System.out.print(message);
            if (jcr.hasNextDouble()) {
                amount = jcr.nextDouble();
                jcr.nextLine();
                if (amount > 0) return amount;
                else System.out.println("Wrong amount! Enter other value. ");
            } else {
                System.out.println("Invalid! Enter a valid number.");
                jcr.nextLine();
            }
        }
    }

    public static void addProduct() {
        System.out.print("Enter product name: ");
        String name = jcr.nextLine();
        double price = getValidPeso("Enter product price: ");
        System.out.print("Enter product quantity: ");
        int quantity = jcr.nextInt();
        jcr.nextLine();
        
        double totalPrice = price * quantity;
        String productDetails = name + " | Price: " + price + " | Qty: " + quantity + " | Total: " + totalPrice;
        cashRegister.add(productDetails);
        
        System.out.println("Product added!");
    }

    public static void displayCart() {
    System.out.println("--- Cart ---");
    if (cashRegister.isEmpty()) {
        System.out.println("You have no item on your cart.");
        return;
    }

    double total = 0;
    for (String item : cashRegister) {
        System.out.println(item);
        total += Double.parseDouble(item.split("\\|")[3].replace("Total: ", ""));
    }

    System.out.println("Total Price: " + total);
}


    public static void checkout() {
        if (cashRegister.isEmpty()) {
            System.out.println("Your cart is empty. Add products.");
            return;
        }

        double total = 0;
        for (String item : cashRegister) {
            String[] parts = item.split("\\|");
            String totalStr = parts[3].trim().replace("Total: ", "");
            total += Double.parseDouble(totalStr);
        }

        System.out.println("Total amount due: " + total);
        double payment;
        do {
            payment = getValidPeso("Enter payment: ");
            if (payment < total) System.out.println("Insufficient payment! Enter at least " + total);
        } while (payment < total);

        System.out.println("Payment accepted! Change: " + (payment - total));
        cashRegister.clear();
    }

    public static void main(String[] args) {
        while (true) {
            System.out.println("--- Bershka ---");
            System.out.println("1. Add Product");
            System.out.println("2. View Cart");
            System.out.println("3. Checkout");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = jcr.nextInt();
            jcr.nextLine(); 

            switch (choice) {
                case 1: 
                    addProduct(); 
                    break;
                case 2: 
                    displayCart(); 
                    break;
                case 3: 
                    checkout(); 
                    break;
                case 4: 
                    System.out.println("Thank you for using my Cash Register!"); 
                    return;
                    
                default: 
                    System.out.println("Invalid choice! Please choose between 1-4.");
                    }

            System.out.print("\nPerform another transaction? (yes/no): ");
            String response = jcr.nextLine();
            if (!response.equalsIgnoreCase("yes")) {
                System.out.println("Thank you! ");
                break;
            }
        }
    }
}