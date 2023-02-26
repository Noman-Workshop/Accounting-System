package user;

import account.Account;
import utility.Input.ProcessAbortException;
import utility.Input.SimpleInput;
import utility.Input.SimpleMenu;
import utility.Input.SimpleMenuItem;

import java.util.ArrayList;

public class User {
	
	private String firstName;
	private String lastName;
	private int age;
	
	private String userName;
	private String encryptedPassword;
	
	private ArrayList<Account> accounts;
	
	private static Database database = Database.getInstance();
	
	public User(String firstName, String lastName, int age, String userName, String password) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
		accounts = new ArrayList<>();
		this.userName = userName;
		this.encryptedPassword = encrypt(password);
	}
	
	public static void P_createUser() {
		try {
			System.out.println("Process Create User initiated...");
			String firstName = SimpleInput.Builder()
					.setPrompt("First Name")
					.setInputValidator(token -> token.length() > 1)
					.setErrorMessage("First name must be at least single character long!")
					.setHelpMessage("First Name of the User")
					.getInput();
			
			String lastName = SimpleInput.Builder()
					.setPrompt("Last Name")
					.setInputValidator(token -> token.length() > 1)
					.setErrorMessage("Last name must be at least single character long!")
					.setHelpMessage("Demo last message help")
					.getInput();
			
			int age = new Integer(SimpleInput.Builder()
					.setPrompt("Age")
					.setInputValidator(token -> {
						try {
							int tokenInt = new Integer(token);
							return tokenInt > 18;
						} catch (NumberFormatException numberFormatException) {
							System.out.println(token + " is not a valid number!");
							return false;
						}
					})
					.setErrorMessage("Age must be at least 18!")
					.getInput());
			
			
			String userName = SimpleInput.Builder()
					.setPrompt("User Name")
					.setInputValidator(token -> token.length() > 5 && database.isUniqueUserName(token))
					.setErrorMessage("User name is already taken or too short!")
					.setHelpMessage("User name must be at least 5 characters long")
					.getInput();
			
			String password = SimpleInput.Builder()
					.setPrompt("Password")
					.setInputValidator(token -> token.length() > 7)
					.setErrorMessage("Password too short!")
					.setHelpMessage("Password must be at least 7 characters long")
					.getInput();
			
			User user = new User(firstName, lastName, age, userName, password);
			database.registerUserName(userName, user);
			System.out.println("New User Successfully created.");
		} catch (ProcessAbortException | InvalidArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void P_addAccount() {
		try {
			Account account = Account.P_createAccount();
			database.registerAccountNumber(account.getAccountNumber());
			this.accounts.add(account);
			System.out.println("Account created and added successfully.");
		} catch (ProcessAbortException | InvalidArgumentException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void P_viewPersonalInfo() {
		System.out.println(this);
	}
	
	public void P_operateAccounts() {
		SimpleMenu simpleMenu = SimpleMenu.Builder();
		for (Account account : accounts) {
			simpleMenu.addMenuItem(SimpleMenuItem.CreateMenuItem(account.getAccountNumber(), account::P_operateAccount));
		}
		try {
			simpleMenu
					.setHelpMessage("Select account to operate on")
					.getInput();
		} catch (ProcessAbortException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public boolean matchPassword(String password) {
		return encryptedPassword.equals(encrypt(password));
	}
	
	@Override
	public String toString() {
		return lastName + "{" +
				"\n\tfirstName='" + firstName + '\'' +
				", \n\tlastName='" + lastName + '\'' +
				", \n\tage=" + age +
				", \n\tuserName='" + userName + '\'' +
				"\n}";
	}
	
	/**
	 * Dummy encryption algorithm
	 *
	 * @param str string to encrypt
	 *
	 * @return encrypted string
	 */
	private String encrypt(String str) {
		StringBuilder encrypted = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			encrypted.append(str.charAt(i) + userName.length() % str.length());
		}
		
		return encrypted.toString();
	}
	
}
