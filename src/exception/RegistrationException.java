package exception;

public class RegistrationException extends Exception {

    public RegistrationException() {}
	
	public RegistrationException(String msg) {
		super(msg);
	}
	public RegistrationException(String msg,Throwable cause) {
		super(msg,cause);
	}
	public RegistrationException(RegistrationException r){
		super(r.toString()); 
	}
    
    
}
