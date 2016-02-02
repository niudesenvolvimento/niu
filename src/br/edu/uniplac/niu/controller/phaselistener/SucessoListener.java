package br.edu.uniplac.niu.controller.phaselistener;

import javax.faces.application.FacesMessage;


import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

import org.primefaces.context.RequestContext;

/**
 * Listener responsavel por colocar um atributo boolean 
 * no context do request ('sucessOnly') que indica que 
 * somente msgs de sucesso existem.
 * Este atributo pode ser lido via javascritpt atraves da 
 * variavel args.
 * 
 * @author Vitor
 * @since 28 dez 2015
 */
public class SucessoListener implements PhaseListener {

	
	private static final String SUCESSO_SOMENTE = "sucessoSomente";

	@Override
	public void beforePhase(PhaseEvent event) {
		FacesContext facesContext = event.getFacesContext();
		
		RequestContext requestContext = RequestContext.getCurrentInstance();

		FacesMessage.Severity severity = facesContext.getMaximumSeverity();
		
		/*
		 * 0: info (unico que nao � erro)
		 * 1: warn 
		 * 2: error
		 * 3: fatal
		 */
		if (severity!=null && severity.getOrdinal() > 1) {//error para cima
			requestContext.addCallbackParam(SUCESSO_SOMENTE, Boolean.FALSE );
		} else {
			requestContext.addCallbackParam(SUCESSO_SOMENTE, Boolean.TRUE );
		}
	}

	@Override
	public PhaseId getPhaseId() {
		return PhaseId.RENDER_RESPONSE;
	}

	
	
	@Override
	public void afterPhase(PhaseEvent event) {
	}

	
	private static final long serialVersionUID = -6258492859105424577L;
}
