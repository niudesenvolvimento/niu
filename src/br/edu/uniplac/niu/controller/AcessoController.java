package br.edu.uniplac.niu.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import org.primefaces.context.RequestContext;

import br.edu.uniplac.niu.controller.holder.SessionHolder;
import br.edu.uniplac.niu.controller.security.ActiveDirectoryAutenticator;
import br.edu.uniplac.niu.controller.util.JSFUtil;
import br.edu.uniplac.niu.model.entity.Setor;
import br.edu.uniplac.niu.model.entity.UsuarioNIU;
import br.edu.uniplac.niu.model.service.InventarioService;
import br.edu.uniplac.niu.model.service.UsuarioService;

/**
 * Controller para Autenticar
 * @author Vitor
 * @since 28 dez 2015
 */
@ManagedBean(name="acessoController")
@ViewScoped
public class AcessoController implements Serializable {

	@EJB UsuarioService usuarioService;

	@EJB InventarioService inventarioService;
	
	
	@ManagedProperty("#{sessionHolder}")
	private SessionHolder sessionHolder;
	
	
	private String login;
	private String senha;
	
	private UsuarioNIU usuarioRecemCriado;
	
	private List<Setor> comboSetores;
	
	
	@PostConstruct void init() {
		comboSetores = inventarioService.pesquisarSetorPeloFlagAtivo(true);
	}
	
	
	/**
	 * Realiza o login de usuario
	 * @return
	 */
	public String doLogin() {
		ActiveDirectoryAutenticator autenticador = new ActiveDirectoryAutenticator();
		boolean flagAutenticadoNoAD = autenticador.isAutenticate(login, senha);
		
		if (!flagAutenticadoNoAD) {
			return negarAcesso("Login ou senha inválidos");

		} else {
			
			UsuarioNIU usuario = usuarioService.buscarUsuarioPeloLoginOuCriar(login);
			if (usuario.getFlagRecemCriado() ) {
				prepararUsuarioRecemCriado(usuario);
				return null;
				
			} else {
				if (usuario.getFlagAtivo()) {
					return permitirAcesso(usuario);
				} else {
					return negarAcesso("Usuário desabilitado"); 
				}
			}
		}
	}
	
	
	
	/**
	 * Instanciar usuario recem criado como preparacao 
	 * para entrar os dados faltantes
	 * @param usuario
	 */
	private void prepararUsuarioRecemCriado(UsuarioNIU usuario) {
		//1.instancia
		usuarioRecemCriado = usuario;
		usuarioRecemCriado.setSetor( new Setor() );
		//2.messagem
		JSFUtil.addWarnMessage("Usuário precisa de informações adicionais");
		//3.
		RequestContext.getCurrentInstance().addCallbackParam("flagUsuarioRecemCriado", Boolean.TRUE);	
	}

	
	/**
	 * Liberar o acesso, carregar usuario e guardando na sessao
	 * @param usuario
	 * @return
	 */
	private String permitirAcesso(UsuarioNIU usuario) {
		usuario = usuarioService.carregarUsuario(usuario);
		sessionHolder.initSessao(usuario);
		return irParaHome();
	}

	
	/**
	 * Nega acesso o acesso com msg de erro e redirect para login
	 * @param mensagemErro
	 * @return
	 */
	private String negarAcesso(String mensagemErro) {
		JSFUtil.addErrorMessage(mensagemErro);
		return irParaLogin(false);
	}
	
	
	

	/**
	 * Realiza o logout do usuario, finalizando sua sessao
	 * @return
	 */
	public String doLogout() {
		sessionHolder.finalizarSessao();
		JSFUtil.addInfoMessage("Sua sessão foi encerrada com sucesso");
		return irParaLogin(true);
	}


	/**
	 * Redireciona para pagina HOME
	 * @return
	 */
	private String irParaHome() {
		return "/home";
	}
	
	
	/**
	 * Redireciona para pagina de LOGIN
	 * @param flagRedirect
	 * @return
	 */
	private String irParaLogin(boolean flagRedirect) {
		if (flagRedirect) {
			return "/login?faces-redirect=true";
		} else {
			return "/login";
		}
	}
	
	
	/**
	 * Salva usuario recem criado
	 */
	public String salvarUsuarioRecemCriado() {
		usuarioService.salvarUsuarioRecemCriado(usuarioRecemCriado);
		JSFUtil.addInfoMessage("Usuário recém criado salvo com sucesso");
		return irParaHome();
	}
	
	
	//acessores...
	private static final long serialVersionUID = 1421116987452326810L;
	
	public String getLogin() {
		return login;
	}


	public void setLogin(String login) {
		this.login = login;
	}


	public String getSenha() {
		return senha;
	}


	public void setSenha(String senha) {
		this.senha = senha;
	}


	public void setSessionHolder(SessionHolder sessionHolder) {
		this.sessionHolder = sessionHolder;
	}


	public UsuarioNIU getUsuarioRecemCriado() {
		return usuarioRecemCriado;
	}


	public void setUsuarioRecemCriado(UsuarioNIU usuarioRecemCriado) {
		this.usuarioRecemCriado = usuarioRecemCriado;
	}


	public List<Setor> getComboSetores() {
		return comboSetores;
	}

	

}
