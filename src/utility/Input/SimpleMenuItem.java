package utility.Input;

public class SimpleMenuItem {
	
	private String prompt;
	private MenuOperation menuOperation;
	private String helpMessage = "N/A";
	
	public static SimpleMenuItem CreateMenuItem(String prompt, MenuOperation menuOperation) {
		SimpleMenuItem simpleMenuItem = new SimpleMenuItem();
		simpleMenuItem.prompt = prompt;
		simpleMenuItem.menuOperation = menuOperation;
		return simpleMenuItem;
	}
	
	public SimpleMenuItem setHelpMessage(String helpMessage) {
		this.helpMessage = helpMessage;
		return this;
	}
	
	void prompt() {
		System.out.println(prompt);
	}
	
	void help() {
		System.out.println(prompt + ": " + helpMessage);
	}
	
	void execute() {
		menuOperation.execute();
	}
	
}
