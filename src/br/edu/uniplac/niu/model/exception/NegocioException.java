package br.edu.uniplac.niu.model.exception;

import javax.ejb.ApplicationException;

/**
 * Hierarquia de exception que representam violação de regra de negocio
 * @author Vitor
 * @since 28 dez 2015
 */
@ApplicationException(rollback=false)
public class NegocioException extends NIUException {
	private static final long serialVersionUID = -737466541972315213L;


	
	public NegocioException(String msg) {
		super(msg);
	}
	
	

}
