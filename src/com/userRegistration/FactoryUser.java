package com.userRegistration;

public class FactoryUser {
    public CreateUser createUser(String userType) {
        if(userType == null) return null;

        if(userType.equalsIgnoreCase("PREMIUM")) {
            return new CreateUser(User.PREMIUM);
        } else if(userType.equalsIgnoreCase("FREE")) {
            return new CreateUser(User.FREE);
        }
        return null;
    }
}
