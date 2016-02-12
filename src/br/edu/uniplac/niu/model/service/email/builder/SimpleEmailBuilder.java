package br.edu.uniplac.niu.model.service.email.builder;

/**
 * Construtor de emails simples
 * 
 * @author Vitor
 * @since 11 FEV 2016
 */
public interface SimpleEmailBuilder {
	
	String getEmailRecipient();

	String getReplyTo();

	String getSubject();

	String getContent();

}
