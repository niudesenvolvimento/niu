package br.edu.uniplac.niu.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.uniplac.niu.controller.util.JSFUtil;
import br.edu.uniplac.niu.model.entity.Setor;
import br.edu.uniplac.niu.model.service.InventarioService;

/**
 * Controller para gerenciar setores
 * @author vitor.figueiredo
 * @since 11 jan 2016
 */
@ManagedBean(name="setorController")
@ViewScoped
public class SetorController implements Serializable {

	@EJB InventarioService service;
	
	private Setor setor;
	
	private List<Setor> setores;
	
	
	@PostConstruct void init() {
		popularSetores();
	}
	
	
	
	private void popularSetores() {
		setores = service.pesquisarSetor();
	}
	
	public void novo() {
		setor = new Setor();
	}
	
	public void salvar() {
		service.salvarSetor(setor);
		popularSetores();
		JSFUtil.addInfoMessage("Setor salvo com sucesso");
	}
	
	public void editar(Setor setorSelecionado) {
		this.setor = setorSelecionado;
	}
	
	public void remover() {
		service.removerSetor(setor);
		popularSetores();
		JSFUtil.addInfoMessage("Setor removido");
	}



	//acessores...
	private static final long serialVersionUID = -401960200941489204L;
	public Setor getSetor() {
		return setor;
	}
	public void setSetor(Setor setor) {
		this.setor = setor;
	}
	public List<Setor> getSetores() {
		return setores;
	}
}
