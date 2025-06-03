/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.cashregistersign;

/**
 *
 * @author Jc Ar
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

public class CashRegisterSign {
    public static Scanner jcr = new Scanner(System.in);
    public static ArrayList<String> cashRegister = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();

    public static boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z\s]{5,15}$"); 
        //\s = space 5,15 = up to 15 charac only
        
    }

    public static boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d\\D]{8,20}$"); 
        //  escape backslashes, so \d becomes "\\d", \\d = digit \\D = non digit $ = end of line 8,20 = up to 20 characters only
    }

    public static void signUp() {
        String username, password;

        while (true) {
            System.out.print("Create your username: (Must be 5-15 characters): ");
            username = jcr.nextLine();
            if (!isValidUsername(username)) {
                System.out.println("Invalid username! Must be 5-15 characters, alphanumeric only.");
                continue;
            }
            break;
        }

        while (true) {
            System.out.print("Create your password: (Must be 8-20 characters, with at least one uppercase letter and one number): ");
            password = jcr.nextLine();
            if (!isValidPassword(password)) {
                System.out.println("Invalid password! Must be 8-20 characters, with at least one uppercase letter and one number.");
                continue;
            }
            break;
        }

        users.add(new User(username, password));
        System.out.println("Sign up success! You can now login.");
    }

    public static boolean login() {
        while (true) {
            System.out.print("Username: ");
            String inputUsername = jcr.nextLine();
            System.out.print("Password: ");
            String inputPassword = jcr.nextLine();

            for (User user : users) {
                if (user.username.equals(inputUsername) && user.password.equals(inputPassword)) {
                    System.out.println("Login success! Welcome " + inputUsername);
                    return true;
                }
            }
            System.out.println("Incorrect username or password. Please try again.");
        }
    }

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
        System.out.println("=== Welcome to Bershka Cash Register System! === ");
        boolean loggedIn = false;

        while (true) {
            System.out.println("Select your option:");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");
            int option = jcr.nextInt();
            jcr.nextLine();

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
                    System.out.println("Invalid choice! Please choose between 1-4 ONLY.");
            }

            System.out.print("Perform another transaction? (yes/no): ");
            String response = jcr.nextLine();
            if (!response.equalsIgnoreCase("yes")) {
                System.out.println("Thank you for using My Cash Register! ");
                break;
            }
        }
    }
}
