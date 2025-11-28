package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Scanner;

public class Main {

    private static final Logger log = LogManager.getLogger(Main.class);

    public static void main(String[] args) {
        log.info("App started");

        UserService userService = new UserService();
        Scanner scanner = new Scanner(System.in);

        System.out.println(
                "\n==== User Service ===="
                + "\n1. Create User"
                + "\n2. Get All Users"
                + "\n3. Find User by ID"
                + "\n4. Update User"
                + "\n5. Delete User"
                + "\n6. Exit"
                );

        String enter = scanner.nextLine();

        while (true) {
            switch (enter) {
                case "1" -> userService.createUser();
                case "2" -> userService.getAllUsers();
                case "3" -> userService.getUserById();
                case "4" -> userService.updateUser();
                case "5" -> userService.deleteUser();
                case "6" -> {
                    userService.close();
                    log.info("App terminated");
                    return;
                }

                default -> {
                    System.out.println("Invalid option! Chose 1-6");
                }
            }
        }
    }
}
