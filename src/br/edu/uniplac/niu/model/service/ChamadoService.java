package br.edu.uniplac.niu.model.service;

import java.util.Date;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import br.edu.uniplac.niu.model.entity.Chamado;
import br.edu.uniplac.niu.model.entity.ChamadoCategoria;
import br.edu.uniplac.niu.model.entity.enumeration.ChamadoStatus;
import br.edu.uniplac.niu.model.exception.NegocioException;

import static br.edu.uniplac.niu.model.util.QueryUtil.*;

/**
 * Serviços de negócio para Chamados
 * @author Vitor
 * @since 04 jan 2016
 */
@Stateless
public class ChamadoService {
	
	@PersistenceContext EntityManager manager;
	
	
	/* Chamado
	 *********/
	
	public Chamado salvarChamado(Chamado chamado) {
		return manager.merge(chamado);
	}
	
	public void removerChamado(Chamado chamado) {
		manager.remove( manager.merge(chamado) );
	}
	
	
	/**
	 * Pesquisa chamado por 'criadoPor' e lista de status.
	 * Usado em chamado.xhtml
	 * @param filtroCriadoPor
	 * @param filtrosStatus
	 * @return
	 */
	public List<Chamado> pesquisarChamadoPeloCriadoPorEStatus(String filtroCriadoPor, List<ChamadoStatus> filtrosStatus) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Chamado> criteria = builder.createQuery(Chamado.class);
		Root<Chamado> root = criteria.from(Chamado.class);
		
		Predicate conjunction = builder.conjunction();
		//criado por
		if (isNotBlank(filtroCriadoPor)) {
			conjunction = builder.and(conjunction, 
					builder.like( root.<String>get("criadoPor"), toLikeMatchModeANY(filtroCriadoPor))
					);
		}
		//status
		if (isNotEmpty(filtrosStatus) ) {
			conjunction = builder.and(conjunction,
					root.<ChamadoStatus>get("status").in(filtrosStatus)
					);
		}
		
		criteria.where(conjunction);
		
		criteria.orderBy( builder.asc( root.<Date>get("criadoEm")) );
		
		return manager.createQuery(criteria).getResultList();
	}
	
	

	/**
	 * Pesquisa chamados pelos filtos (usado em relatório)
	 * @param filtroCriadoPor
	 * @param filtrosStatus
	 * @return
	 */
	public List<Chamado> pesquisarChamadoPelosFiltros( String filtroCriadoPor
			                                         , List<ChamadoStatus> filtrosStatus
			                                         , Date filtroCriadoEmInicial
			                                         , Date filtroCriadoEmFinal
													 ) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<Chamado> criteria = builder.createQuery(Chamado.class);
		Root<Chamado> root = criteria.from(Chamado.class);
		
		Predicate conjunction = builder.conjunction();
		//criado por
		if (isNotBlank(filtroCriadoPor)) {
			conjunction = builder.and(conjunction, 
					builder.like( root.<String>get("criadoPor"), toLikeMatchModeANY(filtroCriadoPor))
					);
		}
		//status
		if (isNotEmpty(filtrosStatus) ) {
			conjunction = builder.and(conjunction,
					root.<ChamadoStatus>get("status").in(filtrosStatus)
					);
		}
		//criado em
		if (isNotNull(filtroCriadoEmInicial) && isNotNull(filtroCriadoEmFinal)) {
			conjunction = builder.and(conjunction, 
					builder.between(root.<Date>get("criadoEm"), filtroCriadoEmInicial, filtroCriadoEmFinal)
					);
		}
		
		
		
		criteria.where(conjunction);
		
		criteria.orderBy( builder.asc( root.<Date>get("criadoEm")) );
		
		return manager.createQuery(criteria).getResultList();
	}
	
	
	
	/* Categoria
	 * *********/
	
	public ChamadoCategoria salvarChamadoCategoria(ChamadoCategoria categoria) {
		verificarSeNomeCategoriaJaExiste( categoria );
		return manager.merge( categoria );
	}
	
	/**
	 * RN para garantir a unicidade do nome da categoria
	 * @param categoria
	 */
	private void verificarSeNomeCategoriaJaExiste(ChamadoCategoria categoria) {
		ChamadoCategoria categoriaEncontrada = buscarChamadoCategoriaPeloNome(categoria.getNome());
		if (categoriaEncontrada!=null && !categoriaEncontrada.equals(categoria)) {
			throw new NegocioException("Nome já usado em outra categoria");
		}
	}

	
	public void removerChamadoCategoria(ChamadoCategoria categoria) {
		verificarSeExisteChamadoParaCategoria(categoria);
		manager.remove( manager.merge(categoria) );
	}
	
	
	/**
	 * RN que evita a remoção de categoria se existirem chamados.
	 * @param categoria
	 */
	private void verificarSeExisteChamadoParaCategoria(ChamadoCategoria categoria) {
		List<Chamado> chamados = pesquisarChamadoPelaCategoria(categoria);
		if (!chamados.isEmpty()) {
			throw new NegocioException("Existem chamados associados a esta categoria");
		}
	}

	
	
	public ChamadoCategoria buscarChamadoCategoriaPeloNome(String nome) {
		try {
			return manager.createNamedQuery("buscarChamadoCategoriaPeloNome", ChamadoCategoria.class)
					.setParameter("pNome", nome)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	
	public List<ChamadoCategoria> pesquisarChamadoCategoria() {
		return manager.createNamedQuery("pesquisarChamadoCategoria", ChamadoCategoria.class)
				.getResultList();
	}
	
	
	public List<ChamadoCategoria> pesquisarChamadoCategoriaPeloFlagAtivo(boolean flagAtivo) {
		return manager.createNamedQuery("pesquisarChamadoCategoriaPeloFlagAtivo", ChamadoCategoria.class) 
				.setParameter("pFlagAtivo", flagAtivo)
				.getResultList();
	}
	
	
	
	public List<Chamado> pesquisarChamadoPelaCategoria(ChamadoCategoria categoria) {
		return manager.createNamedQuery("pesquisarChamadoPelaCategoria", Chamado.class)
				.setParameter("pCategoria", categoria)
				.getResultList();
	}
	
	

	

}
