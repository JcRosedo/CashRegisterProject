/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.assignment;
/**
 *
 * @author JC AR
 */
import java.util.*;
public class Assignment {
    public static void main(String[] args) {
       Scanner scanner = new Scanner(System.in);
        int correctNumber = 2;  
        int attempts = 0;       
        int maxAttempts = 3;    

        System.out.println("Welcome to the Number Guessing Game!");
        System.out.println("Choose a number between 1 and 5. You have 3 attempts to guess the correct number.");

        while (attempts < maxAttempts){
            System.out.print("Enter your chosen number: ");
            int playerGuess = scanner.nextInt();
            attempts++;

            if (playerGuess == correctNumber){
                System.out.println("Congratulations! ");
                break;  
            } else {
                if (attempts < maxAttempts){
                    System.out.println("Please Try Again!");
                } else {
                    System.out.println("Game Over!");
                }
            }
        }
    }    
}
   
