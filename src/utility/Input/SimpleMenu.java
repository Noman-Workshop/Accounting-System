package utility.Input;

import java.util.ArrayList;

public class SimpleMenu implements IMenuInput {
	
	private ArrayList<SimpleMenuItem> menuItems;
	private String menuHelpMessage;
	
	{
		menuItems = new ArrayList<>();
	}
	
	public static SimpleMenu Builder() {
		return new SimpleMenu();
	}
	
	public SimpleMenu addMenuItem(SimpleMenuItem simpleMenuItem) {
		menuItems.add(simpleMenuItem);
		return this;
	}
	
	@Override
	public void prompt() {
		for (int i = 0; i < menuItems.size(); i++) {
			SimpleMenuItem menuItem = menuItems.get(i);
			System.out.print(i + 1 + ". ");
			menuItem.prompt();
		}
	}
	
	@Override
	public void execute(int itemNo) {
		menuItems.get(itemNo).execute();
	}
	
	@Override
	public boolean checkValidity(String token) {
		int itemNo = new Integer(token);
		return itemNo > 0 && itemNo <= menuItems.size();
	}
	
	@Override
	public void help() {
		System.out.println(menuHelpMessage);
		for (SimpleMenuItem menuItem : menuItems) {
			menuItem.help();
		}
		System.out.println("help: Get Help in current context");
		System.out.println("quit: Quit current Process");
	}
	
	public SimpleMenu setHelpMessage(String menuHelpMessage) {
		this.menuHelpMessage = menuHelpMessage;
		return this;
	}
}

