package br.edu.uniplac.niu.controller.email.builder;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import br.edu.uniplac.niu.controller.loader.EmailTemplateLoader;
import br.edu.uniplac.niu.model.entity.Chamado;
import br.edu.uniplac.niu.model.entity.UsuarioNIU;
import br.edu.uniplac.niu.model.service.email.builder.EnhancedEmailBuilder;
import br.edu.uniplac.niu.model.util.StringUtil;

/**
 * Builder de email quando chamado é criado
 * @author vitor.figueiredo
 * @since 11 FEV 2016
 */
public class ChamadoCriadoEmailBuilder extends EnhancedEmailBuilder {

	private static final String FILENAME = "emailTemplateChamadoCriado";
	
	private Set<String> normalRecipients;
	private String emailSubject;
	private String emailBody;
	

	public ChamadoCriadoEmailBuilder(List<UsuarioNIU> usuariosResponsaveis, Chamado chamado) {
		super();
		handleNormalRecipients(usuariosResponsaveis);
		handleEmailSubject(chamado);
		handleEmailBody(chamado);
	}
	
	
	private void handleEmailSubject(Chamado chamado) {
		this.emailSubject = String.format("[NIUGov] Novo Chamado #%s: %s", String.valueOf(chamado.getId()), chamado.getTitulo() );
	}

	
	private void handleEmailBody(Chamado chamado) {
		//1.carrega template
		EmailTemplateLoader loader = new EmailTemplateLoader( getClass() );
		String emailTemplate = loader.loadAsString(FILENAME);
		//2.atribui valores dinamicos
		emailTemplate = StringUtil.replace(emailTemplate, "@ID@", chamado.getId() );
		emailTemplate = StringUtil.replace(emailTemplate, "@TITULO@", chamado.getTitulo() );
		emailTemplate = StringUtil.replace(emailTemplate, "@DETALHE@", chamado.getDetalhe() );
		emailTemplate = StringUtil.replace(emailTemplate, "@CATEGORIA@", chamado.getCategoria().getNome() );
		emailTemplate = StringUtil.replace(emailTemplate, "@PRIORIDADE@", chamado.getPrioridade().getDescricao() );
		emailTemplate = StringUtil.replace(emailTemplate, "@CRIADO_POR@", chamado.getCriadoPor() );
		emailTemplate = StringUtil.replace(emailTemplate, "@CRIADO_EM@", chamado.getCriadoEm() );
		
		this.emailBody = emailTemplate;
	}
	
	

	private void handleNormalRecipients(List<UsuarioNIU> usuariosResponsaveis) {
		normalRecipients = new TreeSet<>();
		for (UsuarioNIU usuarioNIU : usuariosResponsaveis) {
			normalRecipients.add( usuarioNIU.getEmail() );
		}
	}

	
	@Override
	public Set<String> getNormalRecipients() {
		return this.normalRecipients;
	}

	@Override
	public Set<String> getBlindRecipients() {
		return null;
	}

	@Override
	public String getReplyTO() {
		return null;
	}

	@Override
	public String getEmailSubject() {
		return this.emailSubject;
	}

	@Override
	public String getEmailBody() {
		return this.emailBody;
	}

}
