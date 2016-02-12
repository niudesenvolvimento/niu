package br.edu.uniplac.niu.controller;

import java.io.Serializable;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

import br.edu.uniplac.niu.controller.holder.SessionHolder;
import br.edu.uniplac.niu.controller.util.JSFUtil;
import br.edu.uniplac.niu.model.entity.UsuarioNIU;
import br.edu.uniplac.niu.model.service.UsuarioService;

/**
 * Controller para Autenticar
 * @author Vitor
 * @since 28 dez 2015
 */
@ManagedBean(name="acessoController")
@RequestScoped
public class AcessoController implements Serializable {

	@EJB UsuarioService usuarioService;

	@ManagedProperty("#{sessionHolder}")
	private SessionHolder sessionHolder;
	
	
	private String login;
	private String senha;
	
	
	public String doLogin() {
		UsuarioNIU user = usuarioService.buscarUsuarioPeloLoginESenha(login, senha);
		if (user!=null) {
			if (user.getFlagAtivo()) {
				return permitirAcesso(user);
			} else {
				return negarAcesso("Usuário desabilitado"); 
			}
		} else {
			return negarAcesso("Login ou senha inválidos");
		}
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

}
