package br.edu.uniplac.niu.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.uniplac.niu.controller.util.JSFUtil;
import br.edu.uniplac.niu.model.entity.ComputadorMarca;
import br.edu.uniplac.niu.model.service.InventarioService;

/**
 * Controller para gerenciar marca de computadores
 * @author vitor.figueiredo
 * @since 11 jan 2016
 */
@ManagedBean(name="computadorMarcaController")
@ViewScoped
public class ComputadorMarcaController implements Serializable {
	
	@EJB InventarioService service;
	
	private ComputadorMarca marca;
	
	private List<ComputadorMarca> marcas;
	
	
	@PostConstruct void init() {
		popularMarcas();
	}


	private void popularMarcas() {
		marcas = service.pesquisarComputadorMarca();
	}
	
	
	public void novo() {
		marca = new ComputadorMarca();
	}
	
	public void salvar() {
		marca = service.salvarComputadorMarca(marca);
		popularMarcas();
		JSFUtil.addInfoMessage("Marca de computador salva com sucesso");
	}
	
	public void editar(ComputadorMarca marcaSelecionada) {
		this.marca = marcaSelecionada;
	}
	
	public void remover() {
		service.removerComputadorMarca(marca);
		popularMarcas();
		JSFUtil.addInfoMessage("Marca de computador removida");
	}


	
	//acessores...
	private static final long serialVersionUID = 6944509246618265641L;
	public ComputadorMarca getMarca() {
		return marca;
	}
	public void setMarca(ComputadorMarca marca) {
		this.marca = marca;
	}
	public List<ComputadorMarca> getMarcas() {
		return marcas;
	}
}
