package exception;

public class OperationException extends Exception{
    public OperationException() {}
	
	public OperationException(String msg) {
		super(msg);
	}
	public OperationException(String msg,Throwable cause) {
		super(msg,cause);
	}
	public OperationException(OperationException o){
		super(o.toString());
	}
    
}