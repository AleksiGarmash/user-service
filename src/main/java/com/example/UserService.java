package com.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserService {

    private static final Logger log = LogManager.getLogger(UserService.class);
    private final UserDAO userDAO;
    private final Scanner scanner;

    public UserService() {
        this.userDAO = new UserDAO();
        this.scanner = new Scanner(System.in);
    }

    public void createUser() {
        System.out.println("\n=== Create new User! ===");
        System.out.println("Enter Name: ");
        String name = scanner.nextLine();

        System.out.println("Enter Email: ");
        String email = scanner.nextLine();

        Optional<User> existUser = userDAO.findByEmail(email);
        if (existUser.isPresent()) {
            System.out.println("User with this Email already exist!");
            return;
        }

        System.out.println("Enter Age: ");
        int age = Integer.parseInt(scanner.nextLine());

        User user = new User(name, email, age);
        Long id = userDAO.save(user);

        System.out.println("User successfully created with ID: " + id);
    }

    public void getUserById() {
        System.out.println("\n=== Find User by ID ===");
        System.out.println("Enter ID: ");
        Long id = Long.parseLong(scanner.nextLine());

        Optional<User> user = userDAO.findById(id);
        if (user.isPresent()) {
            System.out.println("User found: " + user.get());
        } else {
            System.out.println("User not found!");
        }
    }

    public void getAllUsers() {
        System.out.println("\n=== All Users ===");
        List<User> users = userDAO.findAll();

        if (users.isEmpty()) {
            System.out.println("There is no Users!");
        } else {
            System.out.println("Total Users: " + users.size());
        }
    }

    public void updateUser() {
        System.out.println("\n=== Update User ===");
        System.out.println("Enter ID to update: ");
        Long id = Long.parseLong(scanner.nextLine());

        Optional<User> userOpt = userDAO.findById(id);
        if (userOpt.isEmpty()) {
            System.out.println("User not found!");
            return;
        }

        User user = userOpt.get();
        System.out.println("Current User: " + user);

        System.out.println("Enter new Name (current: " + user.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.isEmpty()) {
            user.setName(name);
        }

        System.out.println("Enter new Email (current: " + user.getEmail() + "): ");
        String email = scanner.nextLine();

        if (!email.trim().isEmpty()) {
            Optional<User> existUser = userDAO.findByEmail(email);
            if (existUser.isPresent()) {
                System.out.println("User with this Email already exist!");
                return;
            }

            user.setEmail(email);
        }

        System.out.println("Enter new Age (current: " + user.getAge() + "): ");
        int age = Integer.parseInt(scanner.nextLine());
        if (age > 0 && age <= 100) {
            user.setAge(age);
        }

        userDAO.update(user);
        System.out.println("User successfully updated!");
    }

    public void deleteUser() {
        System.out.println("\n=== Delete User ===");
        System.out.println("Enter ID to delete: ");
        Long id  = Long.parseLong(scanner.nextLine());

        Optional<User> user = userDAO.findById(id);
        if (user.isPresent()) {
            System.out.println("User to Delete: " + user);
            userDAO.delete(id);
            System.out.println("User successfully deleted!");
        } else {
            System.out.println("User not found!");
        }
    }

    public void close() {
        scanner.close();
        HibernateUtil.shutdown();
    }
}
