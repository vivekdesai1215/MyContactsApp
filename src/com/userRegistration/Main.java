package com.userRegistration;

import java.util.Scanner;
import java.util.UUID;
import java.time.LocalDate;
import java.util.*;
import com.authentication.AuthContext;
import com.authentication.SessionManager;
import com.contacts.Contact;
import com.contacts.ContactSomeoneHandler;
import com.contacts.CreateContact;
import com.contacts.DeleteContactHandler;
import com.contacts.bulkoperations.BulkOperationHandler;
import com.contacts.contactsmanagement.EditContactHandler;
import com.contacts.filter.FilterHandler;
import com.display.BasicContactView;
import com.display.PrettyContactView;
import com.exception.InvalidInputException;
import com.profilemanagement.ProfileHandler;
import com.search.SearchContactHandler;
import com.tags.ApplyTagsHandler;
import com.tags.TagManagementHandler;



/**
 * UC-12: Apply Tags to Contacts
 *
 * Purpose:
 * Allows a logged-in user to assign one or multiple tags to contacts
 * for better organization and filtering.
 *
 * How it works:
 * - User selects a contact and chooses tags to apply.
 * - Tags are fetched from TagRepository or created if new.
 * - Contact maintains a Set of tags, ensuring uniqueness.
 * - Any UI or display component can be updated automatically
 *   when tags change (Observer Pattern).
 *
 * Key Concepts:
 * - OOP: Association between Contact and Tag, bidirectional relationship.
 * - Design Pattern: Observer Pattern to notify UI updates on tag changes.
 * - Java: Set operations (add/remove), equals()/hashCode() for tag comparison.
 */





// @author Vivek 
// @version 12.0



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

		// Creating Dummy Contacts
		String contactId1 = UUID.randomUUID().toString();
		List<String> emailList1 = new ArrayList<>(Arrays.asList("shreyas@gmail.com", "shreyas.work@gmail.com"));
		List<String> phoneNoList1 = new ArrayList<>(Arrays.asList("9972512411", "9988776655"));
		LocalDate date1 = LocalDate.parse("2026-01-02");
		Contact contact1 = new Contact.Builder("Shreyas")
				.contactId(contactId1)
				.date(date1)
				.email(emailList1)
				.phoneNo(phoneNoList1)
				.build();
		Main.contactList.put(contactId1, contact1);

		// Contact 02
		String contactId2 = UUID.randomUUID().toString();
		List<String> emailList2 = new ArrayList<>(Arrays.asList("vivek@gmail.com"));
		List<String> phoneNoList2 = new ArrayList<>(Arrays.asList("7204760809"));
		LocalDate date2 = LocalDate.parse("2026-02-15");
		Contact contact2 = new Contact.Builder("Vivek")
				.contactId(contactId2)
				.date(date2)
				.email(emailList2)
				.phoneNo(phoneNoList2)
				.build();
		Main.contactList.put(contactId2, contact2);
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
			System.out.println(" 1. View Profile \n 2. Update Profile Info \n 3. View Contacts \n 4. Add Contacts \n 5. Edit Contacts \n 6. Delete a contact \n 7. Bulk Delete/Export \n 8. Search Contact \n 9. Contact Someone \n 10. Filter Contacts \n 11. View/Add Tags \n 12. Apply tags to Contacts \n 13. End");
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
			case 3:{
				System.out.println("\n Your Contact List : ");

				for(Map.Entry<String, Contact> entry : contactList.entrySet()) {
					PrettyContactView view = new PrettyContactView(new BasicContactView());   
					System.out.println(view.display(entry.getValue()));
				}  
				System.out.println("\n");
				break;
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
			case 5:{
				EditContactHandler handler = new EditContactHandler();
				handler.editContact(sc, contactList);
				break;
			}
			case 6: {
				DeleteContactHandler handler = new DeleteContactHandler();
				handler.deleteContact(sc, contactList);
				break;
			}
			case 7:{
				BulkOperationHandler handler = new BulkOperationHandler();
				handler.performBulkOperation(sc, contactList);
				break;
			}
			case 8: {
				SearchContactHandler handler = new SearchContactHandler();
				try {
					handler.searchContacts(sc, contactList);

				}catch(InvalidInputException e) {
					System.out.println(e.getMessage());
				}                
				break;               
			}      
			case 9:{
				ContactSomeoneHandler handler = new ContactSomeoneHandler();
				handler.contactSomeone(sc, contactList);
				break;
			}
			case 10:{
				FilterHandler handler = new FilterHandler();
				handler.filterContacts(sc, contactList);
				break;
			}
			case 11:{
				System.out.println("\n"); 
				TagManagementHandler handler = new TagManagementHandler();
				handler.manageTags(sc);
				System.out.println("\n");
				break;
			}
			case 12:{
				ApplyTagsHandler handler = new ApplyTagsHandler();
				handler.applyTags(sc, contactList);
				System.out.println("\n");
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
