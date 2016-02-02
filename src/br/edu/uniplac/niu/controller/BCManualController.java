package br.edu.uniplac.niu.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;

import br.edu.uniplac.niu.controller.holder.SessionHolder;
import br.edu.uniplac.niu.controller.util.JSFUtil;
import br.edu.uniplac.niu.model.entity.BCManual;
import br.edu.uniplac.niu.model.service.BaseConhecimentoService;

/**
 * Controller para Gerenciar Manuais da Base de Conhecimento 
 * @author vitor.figueiredo
 * @since 20 jan 2016
 */
@ManagedBean(name="bcManualController")
@ViewScoped
public class BCManualController implements Serializable {

	@EJB BaseConhecimentoService service;
	
	@ManagedProperty("#{sessionHolder}")
	private SessionHolder sessionHolder;
	
	private BCManual manual;
	
	private List<BCManual> manuais;

	//filtros de pesquisa
	private String filtroTitulo;
	private String filtroPalavrasChaves;
	
	
	@PostConstruct void init() {
		popularManuais();
	}

	private void popularManuais() {
		manuais = service.pesquisarManualPeloFiltro(filtroTitulo, filtroPalavrasChaves);
	}
	
	
	//actions...
	public void novo() {
		manual = new BCManual();
	}
	
	public void pesquisar() {
		popularManuais();
		JSFUtil.addMessageAboutResult(manuais);
	}
	
	public void salvar() {
		manual = service.salvarManual(manual, getLogin() );
		popularManuais();
		JSFUtil.addInfoMessage("Manual salvo com sucesso");
	}
	
	public void editar(BCManual manualSelecionado) {
		this.manual = manualSelecionado;
	}

	public void remover() {
		service.removerManual(manual);
		popularManuais();
		JSFUtil.addInfoMessage("Manual removido");
	}
	
	
	
	//utils...
	private String getLogin() {
		return sessionHolder.getUsuario().getLogin();
	}

	
	//acessores...
	private static final long serialVersionUID = 115947399512564708L;
	public BCManual getManual() {
		return manual;
	}

	public void setManual(BCManual manual) {
		this.manual = manual;
	}

	public String getFiltroTitulo() {
		return filtroTitulo;
	}

	public void setFiltroTitulo(String filtroTitulo) {
		this.filtroTitulo = filtroTitulo;
	}

	public List<BCManual> getManuais() {
		return manuais;
	}

	public void setSessionHolder(SessionHolder sessionHolder) {
		this.sessionHolder = sessionHolder;
	}

	public String getFiltroPalavrasChaves() {
		return filtroPalavrasChaves;
	}

	public void setFiltroPalavrasChaves(String filtroPalavrasChaves) {
		this.filtroPalavrasChaves = filtroPalavrasChaves;
	}
	

}
