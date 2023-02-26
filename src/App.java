import user.Database;
import user.User;
import utility.Input.ProcessAbortException;
import utility.Input.SimpleMenu;
import utility.Input.SimpleMenuItem;

public class App {
	
	private static Database database = Database.getInstance();
	
	public static void main(String[] args) {
		System.out.println("Welcome to NomanInc. Online Services");
		try {
			SimpleMenu.Builder()
					.addMenuItem(SimpleMenuItem.CreateMenuItem("Register", User::P_createUser)
							.setHelpMessage("Creates a new User"))
					.addMenuItem(SimpleMenuItem.CreateMenuItem("Login", () -> {
						try {
							User user = database.P_login();
							SimpleMenu.Builder()
									.addMenuItem(SimpleMenuItem.CreateMenuItem("Personal Info", user::P_viewPersonalInfo)
											.setHelpMessage("View Personal Information"))
									.addMenuItem(SimpleMenuItem.CreateMenuItem("Add Account", user::P_addAccount)
											.setHelpMessage("Create and add a bank account"))
									.addMenuItem(SimpleMenuItem.CreateMenuItem("Operate On Accounts", user::P_operateAccounts)
											.setHelpMessage("View Account Info, Deposit and Cash Out operation"))
									.getInput();
						} catch (ProcessAbortException e) {
							System.out.println(e.getMessage());
						}
					}).setHelpMessage("Log in to operate get online services"))
					.addMenuItem(SimpleMenuItem.CreateMenuItem("About", () -> {
						System.out.println("Me Noman created this system");
					}))
					.addMenuItem(SimpleMenuItem.CreateMenuItem("Showoff", ()-> {
						System.out.println("Its time to show off");
					}))
					.getInput();
			
		} catch (ProcessAbortException abortException) {
			System.out.println("Thank you for using NomanInc. Online Services");
		}
	}
}
