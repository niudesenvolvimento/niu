package br.edu.uniplac.niu.model.service.email.builder;

import java.util.Set;
import java.util.TreeSet;

/**
 * Strategy para construtor de emails para os seminarios
 * 
 * @author Vitor
 * @since 11 FEV 2016
 */
public abstract class EnhancedEmailBuilder {
	
	public abstract Set<String> getNormalRecipients();
	
	public abstract Set<String> getBlindRecipients();
	
	public abstract String getReplyTO();
	
	public abstract String getEmailSubject();

	public abstract String getEmailBody();
	
	
	
	//html content symbols...
	
	public static final String OPEN_PARENTESIS  = "(";
	public static final String CLOSE_PARENTESIS = ")";
	public static final String WHITESPACE       = " ";
	public static final String ONE_POINT        = ". ";
	public static final String TWO_POINTS       = ": ";
	public static final String COMA             = ", ";
	public static final String POINT_COMA       = "; ";
	public static final String ASCII_BREAKLINE  = "\n";
	
	public static final String HTML_FILE_HEADER     = "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>";
	public static final String HTML_BREAKLINE       = "<br />";
	public static final String HTML_OPEN_LIST       = "<ul>";
	public static final String HTML_CLOSE_LIST      = "</ul>";
	public static final String HTML_OPEN_LIST_ITEM  = "<li>";
	public static final String HTML_CLOSE_LIST_ITEM = "</li>";
	

	//html functions...
	
	public String toStrong(String txt) {
		return "<strong>"+txt+"</strong>";
	}

	public String toStrong(Integer txt) {
		return "<strong>"+txt+"</strong>";
	}
	
	public String toItalic(String txt) {
		return "<i>"+txt+"</i>";
	}
	
	public String toIndented(String txt) {
		return "<span style='padding-left: 10px;'>"+txt+"</span>";
	}
	
	public String toH3(String txt) {
		return "<h3>"+txt+"</h3>";
	}


	/**
	 * Monta uma string contendo todos os 
	 * destinatarios normais.
	 * @return
	 */
	public String getNormalRecipientsAsString() {
		StringBuilder builder = new StringBuilder();
		for (String email : getNormalRecipients() ) {
			builder.append( email ).append(", ");
		}
		return builder.toString();
	}
	
	
	
	public Set<String> getTecnologyContacts() {
		TreeSet<String> emails = new TreeSet<String>();
		emails.add( "niu.desenvolvimento@uniplaclages.edu.br" );
		return emails;
	}

}
