package com.contacts.display;
import com.contacts.Contact;

public class BasicContactView implements ContactView {
    @Override
    public String display(Contact contact) {
        return contact.toString();
    }
}
