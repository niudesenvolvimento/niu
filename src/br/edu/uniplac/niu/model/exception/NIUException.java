package br.edu.uniplac.niu.model.exception;


/**
 * Exception pai na hierarquia do sistema
 * @author Vitor
 * @since 28 dez 2015
 */
public class NIUException extends RuntimeException {
	
	public NIUException(String msg) {
		super(msg);
	}
	
	public NIUException(String msg, Throwable t) {
		super(msg, t);
	}
	
	public NIUException(Throwable t) {
		super(t);
	}

	
	
	private static final long serialVersionUID = -8213636800004619805L;
}
