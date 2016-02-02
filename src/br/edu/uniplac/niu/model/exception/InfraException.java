package br.edu.uniplac.niu.model.exception;


/**
 * Hierarquia de Exception de infraestrutura
 * @author Solkam
 * @since 10 FEV 2015
 */
public class InfraException extends NIUException {

	public InfraException(String msg, Throwable t) {
		super(msg, t);
	}
	
	public InfraException(Throwable t) {
		super(t);
	}




	private static final long serialVersionUID = -3840330168056669492L;
}
