package br.edu.uniplac.niu.controller.exceptionhandler;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

/**
 * Fabrica do Manipulador de Exceptions do JSF. (vai configurado em faces-config.xml)
 * 
 * @author vitor
 * @24 JUN 2013
 */
public class NiuExceptionHandlerFactory extends ExceptionHandlerFactory {

	private ExceptionHandlerFactory parent;

	public NiuExceptionHandlerFactory(ExceptionHandlerFactory parent) {
		this.parent = parent;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		return new NiuExceptionHandler(parent.getExceptionHandler());
	}

}
