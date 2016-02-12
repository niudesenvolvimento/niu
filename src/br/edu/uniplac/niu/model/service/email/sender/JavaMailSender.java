package br.edu.uniplac.niu.model.service.email.sender;

import java.io.UnsupportedEncodingException;
import java.util.Set;
import java.util.TreeSet;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.edu.uniplac.niu.model.exception.InfraException;
import br.edu.uniplac.niu.model.service.email.builder.EnhancedEmailBuilder;
import br.edu.uniplac.niu.model.service.email.builder.SimpleEmailBuilder;

/**
 * Estrategy que usa o JavaMail como infra-estrutura
 * 
 * @author vitor
 * @since 28 ABR 2014
 */
public class JavaMailSender {

	private static final String TEXT_HTML = "text/html; charset=utf-8";
	
	private final Session mailSession;

	public JavaMailSender(Session s) {
		mailSession = s;
	}


	public String send(SimpleEmailBuilder builder) {
		MailAdapter adapter = new MailAdapter(builder.getEmailRecipient()
				                             ,builder.getReplyTo()
				                             ,builder.getSubject()
				                             ,builder.getContent() );
		return inSend( adapter );
	}
	
	
	
	public String send(EnhancedEmailBuilder builder) {
		MailAdapter adapter = new MailAdapter(builder.getNormalRecipients()
				                             ,builder.getBlindRecipients()
                                             ,builder.getReplyTO()
                                             ,builder.getEmailSubject()
                                             ,builder.getEmailBody() );
		return inSend( adapter );
	}
	
	
	
	
	
	/**
	 * Usando a infra do JavaMail, envia um email
	 * @param adapter
	 * @return sucesso
	 */
	private String inSend(MailAdapter adapter) {
		try {
			MimeMessage mm = new MimeMessage(mailSession);

			mm.setRecipients(Message.RecipientType.TO,  adapter.getTOInternetAddressArray() );
			
			if (adapter.isFillBlindRecipients()) {
				mm.setRecipients(Message.RecipientType.BCC, adapter.getBCCInternetAddressArray() );
			}
			
			if (adapter.isFillReplyTo()) {
				mm.setReplyTo( adapter.getReplayToInternetAddressArray() );
			}
			
			mm.setSubject( adapter.getSubject() );
			
			mm.setContent( adapter.getContent(), TEXT_HTML );
			
			Transport.send( mm );
			
			return "sucess";
			
		}catch (Exception e) {
			throw new InfraException( e );
		}
	}
	
	
	
	
	/**
	 * Classe interna adaptadora para JavaMail
	 * @author vitor.figueiredo
	 * @since 11 FEV 2016
	 */
	private class MailAdapter {
		private Set<String> normalRecipients = new TreeSet<>();
		private Set<String> blindRecipients = new TreeSet<>();
		private String replyTo;
		private String subject;
		private String content;
		
		/**
		 * Construtor 1: para email simples
		 * @param normalRecipient
		 * @param subject
		 * @param content
		 */
		public MailAdapter(String normalRecipient
						  ,String replyTO
						  ,String subject 
						  ,String content) {
			super();
			this.normalRecipients.add(normalRecipient);
			this.replyTo = replyTO;
			this.subject = subject;
			this.content = content;
		}
		
		/**
		 * Construtor 2: email sofisticados
		 * @param normalRecipients
		 * @param blindRecipients
		 * @param replyTo
		 * @param subject
		 * @param content
		 */
		public MailAdapter(Set<String> normalRecipients 
				          ,Set<String> blindRecipients
				          ,String replyTo
				          ,String subject 
				          ,String content) {
			super();
			this.normalRecipients = normalRecipients;
			this.blindRecipients = blindRecipients;
			this.replyTo = replyTo;
			this.subject = subject;
			this.content = content;
		}
		
		//acessores...		
		public String getReplyTo() {
			return replyTo;
		}
		public String getSubject() {
			return subject;
		}
		public String getContent() {
			return content;
		}
		

		/* **************************
		 * Adapatadores para JavaMail	
		 ****************************/
		public InternetAddress[] getTOInternetAddressArray() throws UnsupportedEncodingException {
			return getArrayOfInternetAddress( normalRecipients );
		}

		public boolean isFillBlindRecipients() {
			return blindRecipients!=null && !blindRecipients.isEmpty();
		}
		
		public InternetAddress[] getBCCInternetAddressArray() throws UnsupportedEncodingException {
			return getArrayOfInternetAddress( blindRecipients );
		}
		
		public boolean isFillReplyTo() {
			return getReplyTo()!=null && !getReplyTo().trim().isEmpty();
		}
		
		public InternetAddress[] getReplayToInternetAddressArray() throws AddressException {
			return new InternetAddress[] { new InternetAddress(getReplyTo()) };
		}
		

		//util
		private InternetAddress[] getArrayOfInternetAddress(Set<String> emails) throws UnsupportedEncodingException {
			if (emails==null || emails.isEmpty()) {//curto-circuito
				return null;
			}
			InternetAddress[] array = new InternetAddress[ emails.size() ];
			int i=0;
			for (String email : emails ) {
				array[i++] = new InternetAddress(email, String.format("Responsavel %d", i) );
			}
			return array;
		}
		
		
		private String getFirstPartOf(String email) {
			if (email==null || email.isEmpty()) {
				return "";
			}
			String[] split = email.split("@");
			if (split!=null && split.length>0) {
				return split[0];
			} else {
				return email;
			}
		}
	}//fim MailAdapater
	
	

}
