package com.company;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UserList {

    static String[] users = new String[10];
    static String[] passwords = new String[10];
    static Scanner userInput;

    public static void main() {
        userInput = new Scanner(System.in);
        int option = 0;

        do {
            displayMenu();
            try {
                option = getOption();
            }
            catch(InputMismatchException ime) {}

            if (option == 1 || option == 2 || option == 3 || option == 4) {

                if (option == 1) {
                    addUser();
                } else if (option == 2) {
                    displayUsers();
                } else if (option == 3) {
                    removeUser();
                } else if (option == 4) {
                    break;
                }
            }
            else {
                System.out.println("\nInvalid option");
                break;
            }
        } while (true);

        System.out.println("\nExiting");
    }

    static void displayMenu() {
        System.out.println("______________________________\n- Menu -\n");
        System.out.println("Create new user (1)");
        System.out.println("Show all users (2)");
        System.out.println("Delete user (3)");
        System.out.println("Exit the system (4)");
    }

    static int getOption() {
        System.out.print("\nOption: ");
        int option = userInput.nextInt();
        userInput.nextLine();
        return option;
    }

    static void addUser() {
        boolean continueCycle = true;
        boolean doesContainUser = false;

        do {
            for (int i = 0; i < users.length; i++) {
                if (users[i] == null) {
                    System.out.println("______________________________");
                    System.out.println("\nEnter username: ");
                    String name = userInput.nextLine();
                    doesContainUser = Arrays.stream(users).anyMatch(name::equals);
                    if (isValidEmail(name) && name.length() < 100 && !doesContainUser) {
                        System.out.println("\nEnter password: ");
                        String password = userInput.nextLine();
                        if (password.matches("(?s).*[A-Z].*") && password.matches("(?s).*[a-z].*") && password.matches("(?s).*[0-9].*") && password.length() > 7) {
                            users[i] = name;
                            passwords[i] = password;
                            System.out.println("\nUser has been added!");
                            orderUsers();
                            continueCycle = false;
                            break;
                        }
                        else {
                            System.out.println("\nPassword is incorrect. It must contain an upper case letter, lower case letter, a number and must be over 8 characters long");
                        }
                    }
                    else if (doesContainUser) {
                        System.out.println("\nCustomer already exists in the system");
                    }
                    else {
                        System.out.println("\nNot a proper username. Input it in email format");
                    }
                }
            }
        } while (continueCycle);
    }

    static void displayUsers() {
        System.out.println("______________________________\n- Users -\n");
        boolean isEmpty = true;
        for (int i = 0; i < users.length; i++){
            if (users[i] != null){
                System.out.println(i+1 + ". " + users[i]);
                isEmpty = false;
            }
        }
        if(isEmpty){
            System.out.println("\nThe list is empty");
        }
    }

    static void removeUser() {
        System.out.println("______________________________");
        boolean continueCycle = true;

        do {
            System.out.println("\nEnter username: ");
            String name = userInput.nextLine();
            if (Arrays.stream(users).anyMatch(name::equals)) {
                for (int i = 0; i < users.length; i++) {
                    if (users[i] != null && users[i].equals(name)) {
                        System.out.println("\nEnter password: ");
                        String userPassword = userInput.nextLine();
                        if (passwords[i] != null && passwords[i].equals(userPassword)) {
                            users[i] = null;
                            orderUsers();
                            System.out.println("\nCustomer has been deleted");
                            continueCycle = false;
                        } else {
                            System.out.println("\nPassword was incorrect");
                        }
                    }
                }
            } else {
                System.out.println("\nThere are no guests with this username");
            }
        } while (continueCycle);
    }

    static boolean isValidEmail(String email) {
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    static void orderUsers() {
        String[] temp = new String[users.length];
        int ti = 0;
        for (int j = 0; j < users.length; j++) {
            if (users[j] != null) {
                temp[ti] = users[j];
                ti++;
            }
        }
        users = temp;
    }
}
