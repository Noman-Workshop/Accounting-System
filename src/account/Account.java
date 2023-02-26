package account;

import user.Database;
import utility.Input.ProcessAbortException;
import utility.Input.SimpleInput;
import utility.Input.SimpleMenu;
import utility.Input.SimpleMenuItem;

import java.util.regex.Pattern;

public class Account {
	
	private String accountNumber;
	private AccountType accountType;
	private float balance;
	
	private static Database database = Database.getInstance();
	
	public Account(String accountNumber, AccountType accountType, float balance) {
		this.accountNumber = accountNumber;
		this.accountType = accountType;
		this.balance = balance;
	}
	
	
	public String getAccountNumber() {
		return accountNumber;
	}
	
	@Override
	public String toString() {
		return accountNumber + "{" +
				", \n\taccountType=" + accountType +
				", \n\tbalance=" + balance +
				"\n}";
	}
	
	public static Account P_createAccount() throws ProcessAbortException {
		System.out.println("Process New Account Creation Initiated...");
		String accountNumber = SimpleInput.Builder()
				.setPrompt("Account Number")
				.setInputValidator(
						token -> Pattern.compile("\\d{3}[-.]?\\d{2}[-.]?\\d{6}").matcher(token).matches()
								&& database.isUniqueAccountNumber(token))
				.setErrorMessage("Account number is not valid!")
				.setHelpMessage("Account number should be 13 digits long of which " +
						"first 3 digits represents district code, " +
						"next 2 digits represents bank code " +
						"and the last 6 digits is the account number provided from respective bank.")
				.getInput();
		
		AccountType accountType = AccountType.GetAccountType();
		
		float balance = new Float(SimpleInput.Builder()
				.setPrompt("Balance")
				.setInputValidator(token -> {
					try {
						float tokenFloat = new Float(token);
						return tokenFloat >= accountType.getMinLimit() && tokenFloat <= accountType.getMaxLimit();
					} catch (NumberFormatException numberFormatException) {
						System.out.println(token + " is not a valid number!");
						return false;
					}
				})
				.setErrorMessage("Account Balance must be between " +
						accountType.getMinLimit() + " to " + accountType.getMaxLimit() + " units to register for " +
						accountType.name() + " rated online services!")
				.getInput());
		
		return new Account(accountNumber, accountType, balance);
	}
	
	public void P_operateAccount() {
		try {
			SimpleMenu.Builder()
					.addMenuItem(SimpleMenuItem.CreateMenuItem("Account Info", this::P_viewAccountInfo))
					.addMenuItem(SimpleMenuItem.CreateMenuItem("Deposit", this::P_deposit))
					.addMenuItem(SimpleMenuItem.CreateMenuItem("Cash Out", this::P_cashOut))
					.getInput();
		} catch (ProcessAbortException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void P_deposit() {
		try {
			float amount = new Float(SimpleInput.Builder()
					.setPrompt("Amount")
					.setInputValidator(token -> {
						try {
							int tokenInt = new Integer(token);
							return tokenInt + balance <= accountType.getMaxLimit();
						} catch (NumberFormatException e) {
							System.out.println(token + " is not a valid number!");
						}
						return false;
					})
					.setErrorMessage("Transaction Limit Exceeded")
					.getInput());
			balance += amount;
			System.out.println(amount + " units deposited successfully");
		} catch (ProcessAbortException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void P_cashOut() {
		try {
			float amount = new Float(SimpleInput.Builder()
					.setPrompt("Amount")
					.setInputValidator(token -> {
						try {
							int tokenInt = new Integer(token);
							return balance - tokenInt >= accountType.getMinLimit();
						} catch (NumberFormatException e) {
							System.out.println(token + " is not a valid number!");
						}
						return false;
					})
					.setErrorMessage("Transaction Limit Exceeded")
					.getInput());
			balance -= amount;
			System.out.println(amount + " units cashed out successfully");
		} catch (ProcessAbortException e) {
			System.out.println(e.getMessage());
		}
	}
	
	public void P_viewAccountInfo() {
		System.out.println(this);
	}
	
	
}
