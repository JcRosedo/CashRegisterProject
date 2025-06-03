/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.characterclass;

/**
 *
 * @author John Clarenze Rosedo
 */
import java.util.*;

public class CharacterClass {
public static void main(String[] args){
     Scanner jc = new Scanner(System.in);
     
     System.out.print("Enter a single character: ");
     char inputChar = jc.next().charAt(0);
     
     System.out.println("Is letter? " + Character.isLetter(inputChar));
     System.out.println("Is digit? " + Character.isDigit(inputChar));
     System.out.println("Is letter or digit? " + Character.isLetterOrDigit(inputChar));
     System.out.println("Is whitespace? " + Character.isWhitespace(inputChar));
     System.out.println("Is uppercase? " + Character.isUpperCase(inputChar));
     System.out.println("Is lowercase? " + Character.isLowerCase(inputChar));
     System.out.println("To uppercase: " + Character.toUpperCase(inputChar));
     System.out.println("To lowercase: " + Character.toLowerCase(inputChar));
}
}
    

