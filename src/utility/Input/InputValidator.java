package utility.Input;

@FunctionalInterface
public interface InputValidator {
	
	boolean checkValidity(String token);
}
