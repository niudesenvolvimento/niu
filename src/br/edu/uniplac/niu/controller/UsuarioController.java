package br.edu.uniplac.niu.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.edu.uniplac.niu.controller.holder.SessionHolder;
import br.edu.uniplac.niu.controller.util.JSFUtil;
import br.edu.uniplac.niu.model.entity.ChamadoCategoria;
import br.edu.uniplac.niu.model.entity.Setor;
import br.edu.uniplac.niu.model.entity.UsuarioNIU;
import br.edu.uniplac.niu.model.entity.enumeration.UsuarioPerfil;
import br.edu.uniplac.niu.model.service.ChamadoService;
import br.edu.uniplac.niu.model.service.InventarioService;
import br.edu.uniplac.niu.model.service.UsuarioService;

/**
 * Controller para usuarios
 * @author vitor.figueiredo
 * @since 08 jan 2016
 */
@ManagedBean(name="usuarioController")
@ViewScoped
public class UsuarioController implements Serializable {
	
	@EJB UsuarioService usuarioService;
	@EJB ChamadoService chamadoService;
	@EJB InventarioService inventarioService;
	
	@ManagedProperty("#{sessionHolder}")
	private SessionHolder sessionHolder;
	
	
	public UsuarioNIU usuario;
	
	private List<UsuarioNIU> usuarios;
	
	//conferencia de senha
	private String senha1;
	private String senha2;
	
	//combos
	private List<ChamadoCategoria> comboCategorias;
	private List<Setor> comboSetores;
	


	//filtros
	private String filtroLogin;
	private UsuarioPerfil filtroPerfil;
	
	
	//inits...
	@PostConstruct void init() {
		popularComboCategorias();
		popularComboSetores();
	}


	private void popularComboCategorias() {
		comboCategorias = chamadoService.pesquisarChamadoCategoriaPeloFlagAtivo( true );
	}

	private void popularComboSetores() {
		comboSetores = inventarioService.pesquisarSetorPeloFlagAtivo( true );
	}
	
	

	private void popularUsuarios() {
		usuarios = usuarioService.pesquisarUsuarioNIUPelosFiltros(filtroLogin, filtroPerfil);
	}
	
	
	//actions...
	public void pesquisar() {
		popularUsuarios();
		JSFUtil.addMessageAboutResult(usuarios);
	}
	
	
	public void novo() {
		usuario = new UsuarioNIU();
		usuario.setSetor( new Setor() );
	}


	public void salvar() {
		usuario = usuarioService.salvarUsuarioNIU(usuario, getLoginUsuarioLogado() );
		carregarUsuario();
		popularUsuarios();
		JSFUtil.addInfoMessage("Usuário salvo com sucesso");
	}
	
	public void editar(UsuarioNIU usuarioSelecionado) {
		this.usuario = usuarioSelecionado;
		carregarUsuario();
	}
	

//agora é via AD	
//	public void trocarSenha() {
//		conferirSenha();
//		usuario = usuarioService.trocarSenha(usuario, senha1, getLoginUsuarioLogado() ); 
//		popularUsuarios();
//		JSFUtil.addInfoMessage("Senha alterada com sucesso");
//	}
//	
//	private void conferirSenha() {
//		if (!senha1.equals(senha2)) {
//			throw new NegocioException("Senhas não conferem");
//		}
//	}
	

	public void remover() {
		usuarioService.removerUsuarioNIU(usuario);
		popularUsuarios();
		JSFUtil.addInfoMessage("Usuário removido");
	}


	//util
	public String getLoginUsuarioLogado() {
		if (sessionHolder.getUsuario()==null) {
			return "informatica";
		} else {
			return sessionHolder.getUsuario().getLogin();
		}
	}
	
	private void carregarUsuario() {
		usuario = usuarioService.carregarUsuario(usuario);
		//workarround para transformar PersistBag em ArrayList
		List<ChamadoCategoria> categoriasAux = new ArrayList<>( usuario.getCategorias() );
		usuario.setCategorias( categoriasAux );
	}
	
	
	//acessores...
	private static final long serialVersionUID = 778855815715469922L;
	public UsuarioNIU getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioNIU usuario) {
		this.usuario = usuario;
	}
	public String getFiltroLogin() {
		return filtroLogin;
	}
	public void setFiltroLogin(String filtroLogin) {
		this.filtroLogin = filtroLogin;
	}
	public UsuarioPerfil getFiltroPerfil() {
		return filtroPerfil;
	}
	public void setFiltroPerfil(UsuarioPerfil filtroPerfil) {
		this.filtroPerfil = filtroPerfil;
	}
	public List<UsuarioNIU> getUsuarios() {
		return usuarios;
	}
	public String getSenha1() {
		return senha1;
	}
	public void setSenha1(String senha1) {
		this.senha1 = senha1;
	}
	public String getSenha2() {
		return senha2;
	}
	public void setSenha2(String senha2) {
		this.senha2 = senha2;
	}
	public void setSessionHolder(SessionHolder sessionHolder) {
		this.sessionHolder = sessionHolder;
	}
	public List<ChamadoCategoria> getComboCategorias() {
		return comboCategorias;
	}
	public List<Setor> getComboSetores() {
		return comboSetores;
	}
}
