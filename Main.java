/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.main;

/**
 *
 * @author jcAR
 */

import java.io.*;
import java.util.*;

public class Main {


private static final Scanner jcar = new Scanner(System.in);
private static final String DIRECTORY = "C:\\Users\\clair\\OneDrive\\Desktop\\JC COPROG 1st\\Directory";
private static final ArrayList<String> fileList = new ArrayList<>();

    public static void main(String[] args) {
        File dir = new File(DIRECTORY);
        if (!dir.exists()) dir.mkdir();
        loadFileList();

        while (true) {
            System.out.println("\n--- File Manager ---");
            System.out.println("1. Create a File");
            System.out.println("2. Write to File");
            System.out.println("3. Read a File");
            System.out.println("4. Delete a File");
            System.out.println("5. Exit application. ");    
            System.out.print("Choose an option (1-5): ");

            String choice = jcar.nextLine();

            switch (choice) {
                case "1":
                    createFile();
                    break;
                case "2":
                    writeToFile();
                    break;
                case "3":
                    readFile();
                    break;
                case "4":
                    deleteFile();
                    break;
                case "5":
                    System.out.println("Exiting app");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createFile() {
        System.out.print("Enter file name: ");
        String fileName = jcar.nextLine();
        File file = new File(DIRECTORY + "/" + fileName + ".txt");

        try {
            if (file.createNewFile()) {
                fileList.add(file.getName());
                
                System.out.println("File created: " + file.getName());
            } else {
                System.out.println("File already exists.");
            }
        } catch (IOException e) {
            System.out.println("Error creating your file: " + e.getMessage());
        }
    }
private static void loadFileList() {
        fileList.clear();
        File dir = new File(DIRECTORY);
        String[] files = dir.list((d, name) -> name.endsWith(".txt"));
        if (files != null) {
            fileList.addAll(Arrays.asList(files));
        }
    }
    private static void writeToFile() {
        listFiles();
        System.out.print("Enter the name of your file: ");
        String fileName = jcar.nextLine() + ".txt";

        if (!fileList.contains(fileName)) {
            System.out.println("File not found.");
            return;
        }

        File file = new File(DIRECTORY + "/" + fileName);

        System.out.println("Enter text (type 'exit' on a new line to stop):");
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            while (true) {
                String line = jcar.nextLine();
                if (line.equalsIgnoreCase("Exit")) break;
                writer.write(line);
                writer.newLine();
            }
            
            System.out.println("Writing completed.");
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    private static void readFile() {
        listFiles();
        System.out.print("Enter the file name to read (without extension): ");
        String fileName = jcar.nextLine() + ".txt";

        if (!fileList.contains(fileName)) {
            System.out.println("File not found in list.");
            return;
        }

        File file = new File(DIRECTORY + "/" + fileName);

        System.out.println("\n-------- File Content --------");
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            
            System.out.println("--- End of File ---");
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    private static void deleteFile() {
        listFiles();
        System.out.print("Enter the file name to delete: ");
        String fileName = jcar.nextLine() + ".txt";

        if (!fileList.contains(fileName)) {
            System.out.println("File not found. ");
            return;
        }

        File file = new File(DIRECTORY + "/" + fileName);

        if (file.delete()) {
            fileList.remove(fileName);
            
            System.out.println("File deleted: " + fileName);
        } else {
            System.out.println("Failed to delete file.");
        }
    }

    private static void listFiles() {
        System.out.println("\n-------- Available Files --------");
        if (fileList.isEmpty()) {
            System.out.println("No files available.");
        } else {
            for (String name : fileList) {
                System.out.println("- " + name);
            }
        }
        System.out.println("--------------------------------------------------");
    }
}