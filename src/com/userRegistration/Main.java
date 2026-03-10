package com.userRegistration;

import java.util.Scanner;
import java.util.UUID;
import java.util.*;
import com.authentication.AuthContext;
import com.authentication.SessionManager;
import com.contacts.Contact;
import com.contacts.CreateContact;
import com.exception.InvalidInputException;
import com.profilemanagement.ProfileHandler;



/**
 * UC-04: Create Contact
 *
 * Purpose:
 * Enables a logged-in user to add a new contact with name, emails, phone numbers,
 * and optional fields like contactId and date.
 *
 * How it works:
 * - User input is collected via Scanner and validated using NameValidator,
 *   EmailValidator, and PhoneNoValidator.
 * - A unique contactId is generated with UUID and date is set with LocalDate.now().
 * - The Contact object is constructed using the Builder Pattern:
 *   -> Mandatory field: name
 *   -> Optional fields: email list, phone number list, date, contactId
 * - The built Contact is stored in Main.contactList for later retrieval.
 *
 * Key Concepts:
 * - OOP: Encapsulation of contact details, validation logic separated into utility classes.
 * - Design Pattern: Builder Pattern ensures flexible and readable Contact creation.
 * - Java Features: Collections for multiple emails/phones, LocalDate for timestamps, UUID for unique IDs.
 */

// @author Vivek 
// @version 4.0


public class Main {
    public static List<User> userList = new ArrayList<>();
    public static Map<String,Contact> contactList = new HashMap<>();

    // Static block with demo users
    static {
        User user01 = new User.Builder("Vivek", UserType.FREE)
                .email("vivek@gmail.com")
                .password("vivek1010")
                .phoneNo("7204760809")
                .build();
        User user02 = new User.Builder("Shreyas", UserType.PREMIUM)
                .email("shreyas@gmail.com")
                .password("shreyas1010")
                .phoneNo("9972512411")
                .build();
        userList.add(user01);
        userList.add(user02);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean isLoggedIn = false;
        User loggedInUser=null;
        try {
            System.out.println("---------------------------------------------");
            System.out.println("----- Welcome to MyContacts Application -----");
            System.out.println("---------------------------------------------");
            System.out.print("\n 1. Register \n 2. Login \n : ");
            String choiceStr = sc.nextLine();

            int choice;
            try {
                choice = Integer.parseInt(choiceStr);
            } catch (NumberFormatException e) {
                throw new InvalidInputException("Invalid choice. Please enter 1 for Register or 2 for Login.");
            }

            if (choice == 1) {
                User user = RegistrationHandler.handleRegistration(sc);
                userList.add(user); 
                System.out.println("Registration successful for: " + user.getEmail());
            } else if (choice == 2) {
                System.out.println("Choose login type: basic / oauth");
                String loginType = sc.nextLine();

                if (!loginType.equalsIgnoreCase("basic") && !loginType.equalsIgnoreCase("oauth")) {
                    throw new InvalidInputException("Invalid login type entered. Please choose 'basic' or 'oauth'.");
                }

                AuthContext context = new AuthContext();
                context.setStrategy(loginType);

                System.out.print("Enter email: ");
                String email = sc.nextLine();
                if (email == null || email.isEmpty()) {
                    throw new InvalidInputException("Email cannot be empty.");
                }

                System.out.print("Enter password: ");
                String password = sc.nextLine();
                if (password == null || password.isEmpty()) {
                    throw new InvalidInputException("Password cannot be empty.");
                }

                
                loggedInUser = context.login(email, password);
                if (loggedInUser != null) {
                	isLoggedIn = true;
                    if (loginType.equalsIgnoreCase("oauth")) {
                        String token = UUID.randomUUID().toString();
                        SessionManager sm = SessionManager.startSession();
                        sm.createSession(token, loggedInUser);
                        System.out.println("Login successful! Session ID: " + token);
                    } else {
                        System.out.println("Login successful with Basic Auth!");
                    }
                } else {
                    System.out.println("Login failed. Invalid credentials.");
                }
            } else {
                throw new InvalidInputException("Invalid choice. Please enter 1 for Register or 2 for Login.");
            }
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
        
        
        if(isLoggedIn==false)return;
        
        System.out.println("\n\n----Welcome "+loggedInUser.getName()+"----");
        System.out.println("-----------------------------");
        boolean end = false;
        do {
        	System.out.println(" 1. View Profile \n 2. Update Profile Info \n 3. View Contacts \n 4. Add Contacts \n 5. End ");
            System.out.print(" : ");
            int ch = sc.nextInt();
            sc.nextLine();
            
            switch(ch) {
            case 1:{
            	System.out.println("Your Profile Info : ");
            	System.out.println(loggedInUser);
            	break;
            }
            case 2:{
            	try {
            		ProfileHandler.updateProfile(loggedInUser, sc);
            	}catch(InvalidInputException e) {
            		System.out.println(e.getMessage());
            	}
            }
            case 4:{
            	System.out.println("Adding a New Contact \n");
            	try {
            		Contact contact = CreateContact.createContact(sc);
            		System.out.println("\nSuccessfull added contact : "+contact);
            	}catch(InvalidInputException e) {
            		System.out.println(e.getMessage());
            	}
            	break;
            	
            }
            default :{
            	end = true;
            	break;
            }
            }
        }while(end==false);
        
    }
}
