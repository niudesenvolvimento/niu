package br.edu.uniplac.niu.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.uniplac.niu.controller.util.JSFUtil;
import br.edu.uniplac.niu.model.entity.ComputadorMarca;
import br.edu.uniplac.niu.model.entity.InventarioComputador;
import br.edu.uniplac.niu.model.entity.Setor;
import br.edu.uniplac.niu.model.service.InventarioService;

/**
 * Controller para Gerenciar Inventario de Computadores
 * @author vitor.figueiredo
 * @since 11 jan 2016
 */
@ManagedBean(name="inventarioComputadorController")
@ViewScoped
public class InventarioComputadorController implements Serializable {

	@EJB InventarioService service;
	
	
	private InventarioComputador computador;
	
	private List<InventarioComputador> computadores;
	
	
	//combos
	private List<Setor> comboSetores;
	private List<ComputadorMarca> comboMarcas;
	
	
	@PostConstruct void init() {
		popularComboSetores();
		popularComboMarcas();
		
		popularComputadores();
	}


	private void popularComboMarcas() {
		comboMarcas = service.pesquisarComputadorMarcaPeloFlagAtivo(true);
	}


	private void popularComboSetores() {
		comboSetores = service.pesquisarSetorPeloFlagAtivo(true);
	}


	private void popularComputadores() {
		computadores = service.pesquisarInventarioComputador();
	}
	
	//actions...
	
	public void novo() {
		computador = new InventarioComputador();
		computador.setSetor( new Setor() );
		computador.setMarca( new ComputadorMarca() );
	}
	
	
	public void salvar() {
		computador = service.salvarInventarioComputador( computador );
		popularComputadores();
		JSFUtil.addInfoMessage("Computador salvo com sucesso");
	}
	
	public void editar(InventarioComputador computadorSelecionado) {
		this.computador = computadorSelecionado;
	}
	
	public void remover() {
		service.removerInventarioComputador(computador);
		popularComputadores();
		JSFUtil.addInfoMessage("Computador removido");
	}


	//acessores...
	private static final long serialVersionUID = -5118580473067894179L;
	public InventarioComputador getComputador() {
		return computador;
	}
	public void setComputador(InventarioComputador computador) {
		this.computador = computador;
	}
	public List<InventarioComputador> getComputadores() {
		return computadores;
	}
	public List<Setor> getComboSetores() {
		return comboSetores;
	}
	public List<ComputadorMarca> getComboMarcas() {
		return comboMarcas;
	}

	
}
