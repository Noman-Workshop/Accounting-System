package utility.Input;

public class ProcessAbortException extends Exception {
	
	public ProcessAbortException() {
		this("Process Aborted by User");
	}
	
	public ProcessAbortException(String message) {
		super(message);
	}
}
