package br.edu.uniplac.niu.model.service.email;

import java.util.Set;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.mail.Session;

import br.edu.uniplac.niu.model.exception.NegocioException;
import br.edu.uniplac.niu.model.service.email.builder.EnhancedEmailBuilder;
import br.edu.uniplac.niu.model.service.email.builder.SimpleEmailBuilder;
import br.edu.uniplac.niu.model.service.email.sender.JavaMailSender;

/**
 * EJB para envio de emails usando a API JavaMail
 * Importante: configurar o resource do JavaMail no WildFly
 * 
 * @author Vitor
 * @since 11 FEV 2015
 */
@Stateless
public class EmailService {
	
	@Resource(lookup="java:jboss/mail/Default")
	private Session mailSession;
	
	
	private JavaMailSender sender;
	
	@PostConstruct
	void init() {
		sender = new JavaMailSender(mailSession);
	}
	

	/**
	 * Envia um email simples
	 * @param simpleEmailBuilder
	 */
	public void sendEmail(SimpleEmailBuilder simpleEmailBuilder) {
		verifyRecipient(simpleEmailBuilder.getEmailRecipient());
		sender.send(simpleEmailBuilder);
	}
	
	
	/**
	 * Versao sobrecarregada que recebe um EnhancedEmailBuilder
	 * @param enhancedEmailBuilder
	 */
	public void sendEmail(EnhancedEmailBuilder enhancedEmailBuilder) {
		verifyRecipients( enhancedEmailBuilder.getNormalRecipients() );
		sender.send( enhancedEmailBuilder );
	}
	
	
	
    //RN	
	/**
	 * Verificacao de negocio: somente serao enviados emails
	 * se houverem destinatarios
	 * @param recipients
	 */
	private void verifyRecipients(Set<String> recipients) {
		if (recipients==null || recipients.isEmpty()) {
			throw new NegocioException("Não é possível enviar email sem nenhum destinatário");
		}
	}
	

	private void verifyRecipient(String simpleRecipient) {
		if (simpleRecipient==null || simpleRecipient.isEmpty()) {
			throw new NegocioException("Não é possível enviar email sem nenhum destinatário");
		}
	}
	
	

}
