package utility.Input;

import java.util.Scanner;

public interface IMenuInput extends InputValidator {
	
	void prompt();
	
	default void handleInvalidCase(String token) {
		System.out.println(token + " is a invalid menu item selector!");
	}
	
	void execute(int itemNo) throws ProcessAbortException;
	
	default void getInput() throws ProcessAbortException {
		Scanner sc = new Scanner(System.in);
		while (true) {
			prompt();
			System.out.print("Choice: ");
			String token = sc.next();
			if (token.toLowerCase().equals("quit")) {
				throw new ProcessAbortException();
			} else if (token.toLowerCase().equals("help")) {
				help();
			} else if (checkValidity(token)) {
				execute(new Integer(token) - 1);
			} else {
				handleInvalidCase(token);
			}
			
		}
	}
	
	default void help() {
	
	}
}
