package utility.Input;

public class SimpleInput {
	
	private String prompt;
	private String errorMessage;
	private InputValidator inputValidator = token -> true;
	private String helpMessage;
	
	public static SimpleInput Builder() {
		return new SimpleInput();
	}
	
	public SimpleInput setPrompt(String prompt) {
		this.prompt = prompt;
		return this;
	}
	
	public SimpleInput setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
		return this;
	}
	
	public SimpleInput setInputValidator(InputValidator inputValidator) {
		this.inputValidator = inputValidator;
		return this;
	}
	
	public SimpleInput setHelpMessage(String helpMessage) {
		this.helpMessage = helpMessage;
		return this;
	}
	
	public String getInput() throws ProcessAbortException {
		return new IInput() {
			
			@Override
			public boolean checkValidity(String token) {
				return inputValidator.checkValidity(token);
			}
			
			@Override
			public void prompt() {
				System.out.print(prompt + ": ");
			}
			
			@Override
			public boolean handleInvalidCase(String token) {
				System.out.println(errorMessage);
				return false;
			}
			
			@Override
			public void help() {
				System.out.println(helpMessage);
			}
			
		}.getInput();
	}
}
