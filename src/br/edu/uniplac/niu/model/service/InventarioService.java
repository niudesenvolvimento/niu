package br.edu.uniplac.niu.model.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import br.edu.uniplac.niu.model.entity.ComputadorMarca;
import br.edu.uniplac.niu.model.entity.InventarioComputador;
import br.edu.uniplac.niu.model.entity.Setor;
import br.edu.uniplac.niu.model.exception.NegocioException;

/**
 * Serviços de negocio para Inventario:
 * - Setor
 * - Marca de computador
 * - Inventario
 * @author vitor.figueiredo
 * @since 11 jan 2016
 */
@Stateless
public class InventarioService {
	
	@PersistenceContext
	private EntityManager manager;
	
	
	/* Setor
	 * *****/
	
	public Setor salvarSetor(Setor setor) {
		verificarSeNomeSetorJaExiste( setor );
		return manager.merge( setor );
	}


	private void verificarSeNomeSetorJaExiste(Setor setor) {
		Setor setorEncontrado = buscarSetorPeloNome( setor.getNome() );
		if (setorEncontrado!=null && !setorEncontrado.equals(setor)) {
			throw new NegocioException("Nome de setor já existe");
		}
	}


	public void removerSetor(Setor setor) {
		manager.remove( manager.merge(setor) );
	}
	
	
	private Setor buscarSetorPeloNome(String nome) {
		try {
			return manager.createNamedQuery("buscarSetorPeloNome", Setor.class)
					.setParameter("pNome", nome)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	public List<Setor> pesquisarSetor() {
		return manager.createNamedQuery("pesquisarSetor", Setor.class)
				.getResultList();
	}

	
	public List<Setor> pesquisarSetorPeloFlagAtivo(boolean flagAtivo) {
		return manager.createNamedQuery("pesquisarSetorPeloFlagAtivo", Setor.class)
				.setParameter("pFlagAtivo", flagAtivo)
				.getResultList();
	}
	

	
	/* Computador Marca
	 * ****************/
	
	public ComputadorMarca salvarComputadorMarca(ComputadorMarca marca) {
		verificarSeMarcaJaExiste( marca );
		return manager.merge( marca );
	}


	private void verificarSeMarcaJaExiste(ComputadorMarca marca) {
		ComputadorMarca marcaEncontrada = buscarComputadorMarcaPeloNome( marca.getNome() );
		if (marcaEncontrada!=null && !marcaEncontrada.equals(marca)) {
			throw new NegocioException("Nome da marca já existe");
		}
	}

	public void removerComputadorMarca(ComputadorMarca marca) {
		manager.remove( manager.merge(marca) );
	}
	
	

	private ComputadorMarca buscarComputadorMarcaPeloNome(String nome) {
		try {
			return manager.createNamedQuery("buscarComputadorMarcaPeloNome", ComputadorMarca.class)
					.setParameter("pNome", nome)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	public List<ComputadorMarca> pesquisarComputadorMarca() {
		return manager.createNamedQuery("pesquisarComputadorMarca", ComputadorMarca.class)
				.getResultList();
	}
	

	public List<ComputadorMarca> pesquisarComputadorMarcaPeloFlagAtivo(boolean flagAtivo) {
		return manager.createNamedQuery("pesquisarComputadorMarcaPeloFlagAtivo", ComputadorMarca.class)
				.setParameter("pFlagAtivo", flagAtivo)
				.getResultList();
	}
	

	
	
	/* Inventario de Computadores
	 * **************************/
	
	public InventarioComputador salvarInventarioComputador(InventarioComputador computador) {
		verificarSeNomeComputadorDoInventarioJaExiste(computador);
		return manager.merge( computador );
	}
	
	
	
	private void verificarSeNomeComputadorDoInventarioJaExiste(InventarioComputador computador) {
		InventarioComputador computadorEncontrado = buscarInventarioComputadorPeloNomeComputador( computador.getNomeComputador() );
		if (computadorEncontrado!=null && !computadorEncontrado.equals(computador)) {
			throw new NegocioException("Nome do computador já existe");
		}
	}


	public void removerInventarioComputador(InventarioComputador computador) {
		manager.remove( manager.merge(computador) );
	}
	
	
	public InventarioComputador buscarInventarioComputadorPeloNomeComputador(String nomeComputador) {
		try {
			return manager.createNamedQuery("buscarInventarioComputadorPeloNomeComputador", InventarioComputador.class)
					.setParameter("pNomeComputador", nomeComputador)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	public List<InventarioComputador> pesquisarInventarioComputador() {
		return manager.createNamedQuery("pesquisarInventarioComputador", InventarioComputador.class)
				.getResultList();
	}

	
	

}
