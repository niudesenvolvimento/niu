package br.edu.uniplac.niu.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.uniplac.niu.controller.util.JSFUtil;
import br.edu.uniplac.niu.model.entity.ChamadoCategoria;
import br.edu.uniplac.niu.model.service.ChamadoService;

/**
 * Controller para gerenciar categoria de chamados
 * @author vitor.figueiredo
 * @since 08 jan 2016
 */
@ManagedBean(name="categoriaController")
@ViewScoped
public class CategoriaController implements Serializable {

	@EJB ChamadoService chamadoService;
	
	
	private ChamadoCategoria categoria;
	
	private List<ChamadoCategoria> categorias;
	
	
	@PostConstruct void init() {
		popularCategorias();
	}

	private void popularCategorias() {
		categorias = chamadoService.pesquisarChamadoCategoria();
	}
	
	
	public void novo() {
		categoria = new ChamadoCategoria();
	}
	
	public void editar(ChamadoCategoria categoriaSelecionada) {
		this.categoria = categoriaSelecionada;
	}
	
	public void salvar() {
		categoria = chamadoService.salvarChamadoCategoria(categoria);
		popularCategorias();
		JSFUtil.addInfoMessage("Categoria salva com sucesso");
	}
	
	public void remover() {
		chamadoService.removerChamadoCategoria(categoria);
		popularCategorias();
		JSFUtil.addInfoMessage("Categoria removida");
	}


	
	//acessores..
	private static final long serialVersionUID = -4979556301781457685L;
	public ChamadoCategoria getCategoria() {
		return categoria;
	}

	public void setCategoria(ChamadoCategoria categoria) {
		this.categoria = categoria;
	}

	public List<ChamadoCategoria> getCategorias() {
		return categorias;
	}

}
