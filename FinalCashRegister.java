/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.finalcashregister;

/**
 *
 * @author JC AR
 */
import java.util.*;
import java.io.*;
import java.util.regex.*;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

class User {
    String username;
    String password;

    User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

public class FinalCashRegister {
    public static Scanner jcr = new Scanner(System.in);
    public static ArrayList<String> cashRegister = new ArrayList<>();
    public static ArrayList<User> users = new ArrayList<>();
    public static String loggedInUser = "";

    public static boolean isValidUsername(String username) {
        return username.matches("^[a-zA-Z\\s]{5,15}$");
    }

    public static boolean isValidPassword(String password) {
        return password.matches("^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d\\D]{8,20}$");
    }

    public static void signUp() {
        String username, password;
        while (true) {
            System.out.print("Create your username: (Must be 5-15 characters): ");
            username = jcr.nextLine();
            if (!isValidUsername(username)) {
                System.out.println("Invalid username! Must be 5-15 characters, letters and spaces only.");
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
                    loggedInUser = inputUsername;
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
            try {
                amount = Double.parseDouble(jcr.nextLine());
                if (amount > 0) return amount;
                else System.out.println("Wrong amount! Enter other value.");
            } catch (NumberFormatException e) {
                System.out.println("Invalid! Enter a valid number.");
            }
        }
    }

    public static void addProduct() {
        System.out.print("Enter product name: ");
        String name = jcr.nextLine();
        double price = getValidPeso("Enter product price: ");
        System.out.print("Enter product quantity: ");
        int quantity = Integer.parseInt(jcr.nextLine());

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
            total += Double.parseDouble(item.split("\\|")[3].replace("Total: ", "").trim());
        }
        System.out.println("Total Price: " + total);
    }

    public static void updateProductQuantity() {
        displayCart();
        if (cashRegister.isEmpty()) 
        return;
        System.out.print("Enter product name to update quantity: ");
        String name = jcr.nextLine();
        for (int i = 0; i < cashRegister.size(); i++) {
            String[] parts = cashRegister.get(i).split("\\|");
            if (parts[0].trim().equalsIgnoreCase(name)) {
                System.out.print("Enter new quantity: ");
                int newQty = Integer.parseInt(jcr.nextLine());
                double price = Double.parseDouble(parts[1].replace("Price: ", "").trim());
                double newTotal = price * newQty;
                cashRegister.set(i, name + " | Price: " + price + " | Qty: " + newQty + " | Total: " + newTotal);
                System.out.println("Quantity updated!");
                return;
            }
        }
        System.out.println("Product not found in cart.");
    }

    public static void removeProductFromCart() {
        displayCart();
        if (cashRegister.isEmpty()) 
        return;
        System.out.print("Enter product name to remove: ");
        String name = jcr.nextLine();
        for (int i = 0; i < cashRegister.size(); i++) {
            String[] parts = cashRegister.get(i).split("\\|");
            if (parts[0].trim().equalsIgnoreCase(name)) {
                cashRegister.remove(i);
                System.out.println("Product removed!");
                return;
            }
        }
        System.out.println("Product not found in cart.");
    }

    public static void checkout(String username) {
        if (cashRegister.isEmpty()) {
            System.out.println("Your cart is empty. Add products.");
            return;
        }

        double total = 0;
        for (String item : cashRegister) {
            String[] parts = item.split("\\|");
            total += Double.parseDouble(parts[3].trim().replace("Total: ", ""));
        }

        System.out.println("Total amount due: " + total);
        double payment;
        do {
            payment = getValidPeso("Enter payment: ");
            if (payment < total) System.out.println("Insufficient payment! Enter at least " + total);
        } while (payment < total);

        System.out.println("Payment accepted! Change: " + (payment - total));

        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt", true))) {
            String dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            writer.write("Transaction Date/Time: " + dateTime);
            writer.newLine();
            writer.write("Cashier: " + username);
            writer.newLine();
            writer.write("Items:");
            writer.newLine();
            for (String item : cashRegister) {
                writer.write(item);
                writer.newLine();
            }
            writer.write("Total: " + total);
            writer.newLine();
            writer.write("==============================");
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error saving transaction: " + e.getMessage());
        }

        cashRegister.clear();
    }

    public static void viewTransactionHistory() {
        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.out.println("No transaction history found.");
        }
    }

    public static void main(String[] args) {
        System.out.println("\n================================================== ");
        System.out.println("   === Welcome to QuickCart's Register! === ");
        System.out.println("================================================== ");
        boolean loggedIn = false;

        while (true) {
            System.out.println("Select your option:");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose option: ");

            int option = Integer.parseInt(jcr.nextLine());
            if (option == 1) signUp();
            else if (option == 2) {
                loggedIn = login();
                if (loggedIn) 
                break;
            } else if (option == 3) {
                System.out.println("Thank you for using my Cash Register!");
                return;
            } else System.out.println("Invalid option. Try again.");
        }

        while (true) {
            System.out.println("-----------------------");
            System.out.println("   --- QuickCart ---");
            System.out.println("-----------------------");
            System.out.println("1. Add Product");
            System.out.println("2. View Cart");
            System.out.println("3. Update Product Quantity");
            System.out.println("4. Remove Product from Cart");
            System.out.println("5. Checkout");
            System.out.println("6. View Transaction History");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            int choice = Integer.parseInt(jcr.nextLine());

            switch (choice) {
                case 1:
                    addProduct(); 
                break;
                case 2: 
                    displayCart(); 
                break;
                case 3: 
                    updateProductQuantity(); 
                    break;
                case 4: 
                    removeProductFromCart(); 
                    break;
                case 5: 
                    checkout(loggedInUser); 
                    break;
                case 6: 
                    viewTransactionHistory(); 
                    break;
                case 7:
                    System.out.println("Thank you for using my Cash Register!");
                    return;
                default:
                    System.out.println("Invalid choice! Please choose between 1-7 ONLY.");
            }
        }
    }
}
