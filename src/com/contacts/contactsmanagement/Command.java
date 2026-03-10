package com.contacts.contactsmanagement;

public interface Command {
    void execute();
    void undo();
}