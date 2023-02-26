package user;

import utility.Input.ProcessAbortException;
import utility.Input.SimpleInput;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {
	
	private static HashMap<String, User> registeredUsers;
	private static ArrayList<String> registeredAccountNumbers;
	
	private static Database database;
	
	{
		registeredUsers = new HashMap<>();
		registeredAccountNumbers = new ArrayList<>();
	}
	
	public static Database getInstance() {
		if (database == null) {
			database = new Database();
		}
		return database;
	}
	
	public boolean isUniqueUserName(String userName) {
		return !registeredUsers.containsKey(userName);
	}
	
	public boolean isUniqueAccountNumber(String accountNumber) {
		return registeredAccountNumbers.stream().noneMatch(accountNumber::equals);
	}
	
	public void registerUserName(String userName, User user) throws InvalidArgumentException {
		if (isUniqueUserName(userName)) registeredUsers.put(userName, user);
		else throw new InvalidArgumentException("Provided user name " + userName + "already exists");
	}
	
	public void registerAccountNumber(String accountNumber) throws InvalidArgumentException {
		if (isUniqueUserName(accountNumber)) registeredAccountNumbers.add(accountNumber);
		else
			throw new InvalidArgumentException("Provided account number " + accountNumber + "already exists");
	}
	
	public User P_login() throws ProcessAbortException {
		System.out.println("Process User Login initiated...");
		String userName = SimpleInput.Builder()
				.setPrompt("User Name")
				.getInput();
		
		String password = SimpleInput.Builder()
				.setPrompt("Password")
				.getInput();
		
		if (!isUniqueUserName(userName) && registeredUsers.get(userName).matchPassword(password)) {
			System.out.println("Log in successful");
			return registeredUsers.get(userName);
		} else {
			throw new ProcessAbortException("User Name and Password Combination is not correct!");
		}
		
	}
}
