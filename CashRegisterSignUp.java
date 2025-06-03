/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cashregistersignup;

/**
 *
 * @author JC Rosedo
 */
import java.util.*;
import java.util.regex.*;

class User {
    String username;
    String password;

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

public class CashRegisterSignUp {
    static Scanner jcp = new Scanner(System.in);
    static ArrayList<String> cashRegister = new ArrayList<>();
    static ArrayList<User> users = new ArrayList<>();

    public static void signUp() {
        while (true) {
            System.out.print("Create username (5–15 alphanumeric): ");
            String username = jcp.nextLine();
            if (!username.matches("^[a-zA-Z0-9]{5,15}$")) {
                System.out.println("Invalid username!");
                continue;
            }

            System.out.print("Create password (8–20, 1 uppercase & 1 number): ");
            String password = jcp.nextLine();
            if (!password.matches("^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]{8,20}$")) {
                System.out.println("Invalid password!");
                continue;
            }

            users.add(new User(username, password));
            System.out.println("Signup successful!");
            break;
        }
    }

    public static boolean login() {
        while (true) {
            System.out.print("Username: ");
            String user = jcp.nextLine();
            System.out.print("Password: ");
            String pass = jcp.nextLine();

            for (User u : users) {
                if (u.username.equals(user) && u.password.equals(pass)) {
                    System.out.println("Login successful! Welcome " + user);
                    return true;
                }
            }

            System.out.println("Incorrect credentials. Try again.");
        }
    }

    public static double getValidPeso(String message) {
        double amount;
        while (true) {
            System.out.print(message);
            if (jcp.hasNextDouble()) {
                amount = jcp.nextDouble();
                jcp.nextLine();
                if (amount > 0) return amount;
                else System.out.println("Wrong amount! Enter other value. ");
            } else {
                System.out.println("Invalid! Enter a valid number.");
                jcp.nextLine();
            }
        }
    }

    public static void addProduct() {
        System.out.print("Enter product name: ");
        String name = jcp.nextLine();
        double price = getValidPeso("Enter product price: ");
        System.out.print("Enter product quantity: ");
        int quantity = jcp.nextInt();
        jcp.nextLine();

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
        System.out.println("=== Welcome to Bershka Cash Register System! === ");
        boolean loggedIn = false;

        while (true) {
            System.out.println("Select your option:");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");
            int option = jcp.nextInt();
            jcp.nextLine();

            if (option == 1) signUp();
            else if (option == 2) {
                loggedIn = login();
                if (loggedIn) break;
            }
            else if (option == 3) {
                System.out.println("Thank you for using my Cash Register!");
                return;
            }
            else System.out.println("Invalid option. Try again.");
        }

        while (true) {
            System.out.println("--- Bershka ---");
            System.out.println("1. Add Product");
            System.out.println("2. View Cart");
            System.out.println("3. Checkout");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = jcp.nextInt();
            jcp.nextLine();

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
                    System.out.println("Invalid choice! Please choose between 1-4 ONLY.");
            }

            System.out.print("\nPerform another transaction? (yes/no): ");
            String response = jcp.nextLine();
            if (!response.equalsIgnoreCase("yes")) {
                System.out.println("Thank you for using My Cash Register! ");
                break;
            }
        }
    }
}
