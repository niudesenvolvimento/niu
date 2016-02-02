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
import br.edu.uniplac.niu.model.entity.BCTutorial;
import br.edu.uniplac.niu.model.service.BaseConhecimentoService;

/**
 * Controller para Gerenciar Tutoriais da Base de Conhecimento
 * @author vitor.figueiredo
 * @since 19 jan 2016
 */
@ManagedBean(name="bctutorialController")
@ViewScoped
public class BCTutorialController implements Serializable {

	@EJB BaseConhecimentoService service;
	
	@ManagedProperty("#{sessionHolder}")
	private SessionHolder sessionHolder;
	
	private BCTutorial tutorial;
	
	private List<BCTutorial> tutoriais;
	
	
	//filtros
	private String filtroTitulo;
	
	
	@PostConstruct void init() {
		popularTutoriais();
	}
	
	private void popularTutoriais() {
		tutoriais = service.pesquisarTutorialPorFiltro(filtroTitulo);
	}
	
	
	//actions...
	public void pesquisar() {
		popularTutoriais();
		JSFUtil.addMessageAboutResult(tutoriais);
	}
	
	
	public void novo() {
		tutorial = new BCTutorial();
	}
	
	
	public void salvar() {
		service.salvarTutorial(tutorial, getLogin() );
		popularTutoriais();
		JSFUtil.addInfoMessage("Tutorial salvo com sucesso");
	}
	
	
	public void editar(BCTutorial tutorialSelecionado) {
		this.tutorial = tutorialSelecionado;
	}
	
	
	public void remover() {
		service.removerTutorial(tutorial);
		popularTutoriais();
		JSFUtil.addInfoMessage("Tutorial removido");
	}
	
	
	
	//utils...
	public String getLogin() {
		return sessionHolder.getUsuario().getLogin();
	}
	
	

	//acessores...
	private static final long serialVersionUID = -8366408922074871341L;
	public BCTutorial getTutorial() {
		return tutorial;
	}
	public void setTutorial(BCTutorial tutorial) {
		this.tutorial = tutorial;
	}
	public String getFiltroTitulo() {
		return filtroTitulo;
	}
	public void setFiltroTitulo(String filtroTitulo) {
		this.filtroTitulo = filtroTitulo;
	}
	public List<BCTutorial> getTutoriais() {
		return tutoriais;
	}
	public void setSessionHolder(SessionHolder sessionHolder) {
		this.sessionHolder = sessionHolder;
	}
	
}
