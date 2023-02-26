package utility.Input;

import java.util.Scanner;

public interface IInput extends InputValidator {
	
	void prompt();
	
	boolean handleInvalidCase(String token);
	
	default String getInput() throws ProcessAbortException {
		
		Scanner sc = new Scanner(System.in);
		while (true) {
			prompt();
			String token = sc.next();
			if (token.toLowerCase().equals("quit")) {
				throw new ProcessAbortException();
			} else if (token.toLowerCase().equals("help")) {
				help();
			} else if (checkValidity(token)) {
				return token;
			} else if (handleInvalidCase(token)) {
				return token;
			}
		}
	}
	
	default void help() {
	
	}
	
}
