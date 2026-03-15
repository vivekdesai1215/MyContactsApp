package com.contacts.filter;

import com.contacts.Contact;

public interface Filter {
	   boolean matches(Contact contact);
}
