package account;

import utility.Input.IInput;
import utility.Input.ProcessAbortException;

public enum AccountType {
	
	PLATINUM(10e10f, 10e5f), GOLD(10e8f, 10e4f), SILVER(10e6f, 10e3f), REGULAR(10e4f, 10e2f);
	
	private final float maxLimit;
	private final float minLimit;
	
	AccountType(float maxLimit, float minLimit) {
		this.maxLimit = maxLimit;
		this.minLimit = minLimit;
	}
	
	public float getMaxLimit() {
		return maxLimit;
	}
	
	public float getMinLimit() {
		return minLimit;
	}
	
	public static AccountType GetAccountType() throws ProcessAbortException {
		
		String accountType = new IInput() {
			
			@Override
			public boolean checkValidity(String token) {
				for (AccountType type : values()) {
					if (token.toUpperCase().equals(type.name())) {
						return true;
					}
				}
				return false;
			}
			
			@Override
			public void prompt() {
				System.out.print("Account Type: ");
			}
			
			@Override
			public boolean handleInvalidCase(String token) {
				System.out.println(token + "is not a valid account type.");
				return false;
			}
			
			@Override
			public void help() {
				System.out.println("Valid Account types are: PLATINUM, GOLD, SILVER, REGULAR");
			}
			
		}.getInput();
		
		return valueOf(accountType.toUpperCase());
	}
}
