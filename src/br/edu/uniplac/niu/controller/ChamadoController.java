package br.edu.uniplac.niu.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.edu.uniplac.niu.controller.holder.SessionHolder;
import br.edu.uniplac.niu.controller.util.CookieUtil;
import br.edu.uniplac.niu.controller.util.JSFUtil;
import br.edu.uniplac.niu.model.entity.Chamado;
import br.edu.uniplac.niu.model.entity.ChamadoCategoria;
import br.edu.uniplac.niu.model.entity.UsuarioNIU;
import br.edu.uniplac.niu.model.entity.enumeration.ChamadoStatus;
import br.edu.uniplac.niu.model.entity.enumeration.UsuarioPerfil;
import br.edu.uniplac.niu.model.service.ChamadoService;
import br.edu.uniplac.niu.model.service.UsuarioService;

/**
 * Controller para Gerenciar Chamados
 * @author Vitor
 * @since 04 jan 2016
 */
@ManagedBean(name="chamadoController")
@ViewScoped
public class ChamadoController implements Serializable {
	
	
	@EJB ChamadoService service;
	
	@EJB UsuarioService usuarioService;
	
	
	
	@ManagedProperty("#{sessionHolder}")
	private SessionHolder sessionHolder;
	

	private List<Chamado> chamados;
	
	private Chamado chamado;
	
	
	//combos
	private List<UsuarioNIU> comboUsuarios;
	private List<ChamadoCategoria> comboCategorias;
	
	
	//filtros pesquisa
	private String filtroCriadoPor;
	private List<ChamadoStatus> filtrosStatus;
	private String filtroResponsavel;
	private List<ChamadoCategoria> filtroCategorias;
	
	
	
	@PostConstruct void init() {
		popularFiltroCriadoPor();
		popularChamados();
		popularComboUsuariosInformatica();
		popularComboCategorias();

		novo();
	}

	


	//popular....
	
	private void popularFiltroCriadoPor() {
		//2.define criador:
		if (sessionHolder.getFlagLogado()) {
			filtroCriadoPor = recuperarCriadoPorPeloLogin();
		} else {
			filtroCriadoPor = recuperarCriadoPorUsandoCookie();
		}
	}
	
	private void popularChamados() {
		chamados = service.pesquisarChamadoPeloCriadoPorEStatus(filtroCriadoPor
															  , filtrosStatus
															  , filtroResponsavel
															  , filtroCategorias);
	}
	
	private void popularComboUsuariosInformatica() {
		comboUsuarios = usuarioService.pesquisarUsuarioNIUPelosFiltros(null, UsuarioPerfil.INF);
	}
	
	private void popularComboCategorias() {
		comboCategorias = service.pesquisarChamadoCategoriaPeloFlagAtivo(true);
	}
	
	
	
	//actions...
	private void novo() {
		//1.instancia
		chamado = new Chamado();
		//2.usa o filtro de criado por:
		chamado.setCriadoPor(filtroCriadoPor);
		//3.categoria
		chamado.setCategoria( new ChamadoCategoria() );
	}
	
	
	public void pesquisar() {
		popularChamados();
		JSFUtil.addMessageAboutResult(chamados);
	}
	
	
	public void criar() {
		chamado = service.salvarChamado(chamado);
		guardarCriadorComoCookie();
		novo();
		popularChamados();
		JSFUtil.addInfoMessage("Chamado criado com sucesso");
	}
	
	
	public void salvar() {
		chamado = service.salvarChamado(chamado);
		popularChamados();
		JSFUtil.addInfoMessage("Chamado salvo com sucesso");
	}

	
	public void editar(Chamado chamadoSelecionado) {
		this.chamado = chamadoSelecionado;
	}
	

	public void remover() {
		service.removerChamado(chamado);
		popularChamados();
		JSFUtil.addInfoMessage("Chamado removido");
	}


	
	//transição de status
	public void paraStatusExecutando() {
		chamado.paraStatusExecutando( getLogin() );
		salvarComNovoStatus();
	}
	
	public void paraStatusTestando() {
		chamado.paraStatusTestando( getLogin() );
		salvarComNovoStatus();
	}
	
	public void paraStatusDevolvido() {
		chamado.paraStatusDevolvido( getLogin() );
		salvarComNovoStatus();
	}
	
	public void paraStatusResolvido() {
		chamado.paraStatusResolvido( getLogin() );
		salvarComNovoStatus();
	}
	
	private void salvarComNovoStatus() {
		chamado = service.salvarChamado(chamado);
		popularChamados();
		JSFUtil.addInfoMessage("Status alterado com sucesso");
	}
	
	
	
	
	
	//utils..
	public String getLogin() {
		return sessionHolder.getUsuario().getLogin();
	}
	
	
	private String recuperarCriadoPorPeloLogin() {
		String login = getLogin();
		return login;
	}
	
	
	private String recuperarCriadoPorUsandoCookie() {
		String cookieCriadoPor = CookieUtil.getCookieCriadoPor();
		return cookieCriadoPor;
	}
	
	
	private void guardarCriadorComoCookie() {
		CookieUtil.setCookieCriadoPor( chamado.getCriadoPor() );
	}
	
	
	
	//acessores...
	private static final long serialVersionUID = -5259919941777289903L;
	
	public Chamado getChamado() {
		return chamado;
	}
	public void setChamado(Chamado chamado) {
		this.chamado = chamado;
	}
	public List<Chamado> getChamados() {
		return chamados;
	}
	public void setSessionHolder(SessionHolder sessionHolder) {
		this.sessionHolder = sessionHolder;
	}
	public List<UsuarioNIU> getComboUsuarios() {
		return comboUsuarios;
	}
	public String getFiltroCriadoPor() {
		return filtroCriadoPor;
	}
	public void setFiltroCriadoPor(String filtroCriadoPor) {
		this.filtroCriadoPor = filtroCriadoPor;
	}
	public List<ChamadoStatus> getFiltrosStatus() {
		return filtrosStatus;
	}
	public void setFiltrosStatus(List<ChamadoStatus> filtrosStatus) {
		this.filtrosStatus = filtrosStatus;
	}
	public List<ChamadoCategoria> getComboCategorias() {
		return comboCategorias;
	}
	public String getFiltroResponsavel() {
		return filtroResponsavel;
	}
	public void setFiltroResponsavel(String filtroResponsavel) {
		this.filtroResponsavel = filtroResponsavel;
	}
	public List<ChamadoCategoria> getFiltroCategorias() {
		return filtroCategorias;
	}
	public void setFiltroCategorias(List<ChamadoCategoria> filtroCategorias) {
		this.filtroCategorias = filtroCategorias;
	}
}