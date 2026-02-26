package com.userRegistration;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        FactoryUser factory = new FactoryUser();

        System.out.print("What type of subscription are you opting for : (Premium | Free) : ");
        String userType = sc.next();

        CreateUser baseUser = factory.createUser(userType);

        
        System.out.print("Enter your name: ");
        String name = sc.next();

        System.out.print("Enter your email: ");
        String email = sc.next();

        System.out.print("Enter your password: ");
        String password = sc.next();

        CreateUser fullUser = new UserBuilder(name, baseUser.userType)
                                .email(email)
                                .password(password)
                                .build();

        System.out.println("User created: " + fullUser);
    }
}
