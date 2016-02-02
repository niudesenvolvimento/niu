package br.edu.uniplac.niu.model.service;

import static br.edu.uniplac.niu.model.util.QueryUtil.*;


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

import br.edu.uniplac.niu.model.entity.BCManual;
import br.edu.uniplac.niu.model.entity.BCTutorial;
import br.edu.uniplac.niu.model.exception.NegocioException;

/**
 * Serviço de negocio para Base de Conhecimento
 * @author vitor.figueiredo
 * @since 19 jan 2016
 */
@Stateless
public class BaseConhecimentoService {
	
	@PersistenceContext EntityManager manager;
	
	
	/* tutorial 
	 * ********/
	
	public BCTutorial salvarTutorial(BCTutorial tutorial, String criadorOrAtualizador) {
		verificarSeTituloDoTutorialJaExiste(tutorial);
		gravarInfoLog(tutorial, criadorOrAtualizador);
		return manager.merge( tutorial );
	}
	
	
	private void verificarSeTituloDoTutorialJaExiste(BCTutorial tutorial) {
		BCTutorial tutorialEncontrado = buscarTutorialPeloTitulo(tutorial.getTitulo());
		if (tutorialEncontrado!=null && !tutorial.equals(tutorialEncontrado)) {
			throw new NegocioException("Título do Tutorial já existe");
		}
		
	}


	private void gravarInfoLog(BCTutorial tutorial, String criadorOrAtualizador) {
		if (tutorial.isTransient()) {
			tutorial.getInfoLog().setCriadoEm( new Date() );
			tutorial.getInfoLog().setCriadoPor(criadorOrAtualizador);
		} else {
			tutorial.getInfoLog().setAtualizadoEm( new Date() );
			tutorial.getInfoLog().setAtualizadoPor( criadorOrAtualizador );
		}
	}

	
	public void removerTutorial(BCTutorial tutorial) {
		manager.remove( manager.merge(tutorial) );
	}

	
	private BCTutorial buscarTutorialPeloTitulo(String titulo) {
		try {
			return manager.createNamedQuery("buscarTutorialPeloTitulo", BCTutorial.class)
					.setParameter("pTitulo", titulo)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}

	public List<BCTutorial> pesquisarTutorialPorFiltro(String filtroTitulo) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<BCTutorial> criteria = builder.createQuery(BCTutorial.class);
		Root<BCTutorial> root = criteria.from(BCTutorial.class);
		
		Predicate conjunction = builder.conjunction();
		//titulo
		if (isNotBlank(filtroTitulo)) {
			conjunction = builder.and(conjunction
					,builder.like(root.<String>get("titulo"), toLikeMatchModeANY(filtroTitulo) )
					);
		}
		criteria.where( conjunction );
		criteria.orderBy( builder.asc(root.<String>get("titulo")) );
		return manager.createQuery(criteria).getResultList();
	}
	
	
	
	
	
	
	/* manual
	 * ******/
	
	public BCManual salvarManual(BCManual manual, String criadorOuAtualizador) {
		verificarSeTituloDoManualJaExiste(manual);
		gravarInfoLog(manual, criadorOuAtualizador);
		return manager.merge( manual );
	}
	
	
	private void gravarInfoLog(BCManual manual, String criadorOuAtualizador) {
		if (manual.isTransient()) {
			manual.getInfoLog().setCriadoEm( new Date() );
			manual.getInfoLog().setCriadoPor( criadorOuAtualizador );
		} else {
			manual.getInfoLog().setAtualizadoEm( new Date() );
			manual.getInfoLog().setAtualizadoPor( criadorOuAtualizador );
		}
	}


	private void verificarSeTituloDoManualJaExiste(BCManual manual) {
		BCManual manualEncontrado = buscarManualPeloTitulo( manual.getTitulo() );
		if (manualEncontrado!=null && !manualEncontrado.equals(manual)) {
			throw new NegocioException("Título de Manual já existe");
		}
	}

	public void removerManual(BCManual manual) {
		manager.remove( manager.merge(manual) );
	}

	private BCManual buscarManualPeloTitulo(String titulo) {
		try {
			return manager.createNamedQuery("buscarManualPeloTitulo", BCManual.class)
					.setParameter("pTitulo", titulo)
					.getSingleResult();
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	public List<BCManual> pesquisarManualPeloFiltro(String filtroTitulo, String filtrosPalavrasChaves) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<BCManual> criteria = builder.createQuery(BCManual.class);
		Root<BCManual> root = criteria.from(BCManual.class);
		Predicate conjunction = builder.conjunction();
		//titulo
		if (isNotBlank(filtroTitulo)) {
			conjunction = builder.and(conjunction
					, builder.like( root.<String>get("titulo"), toLikeMatchModeANY(filtroTitulo) )
				);
		}
		//palavras chaves
		if (isNotBlank(filtrosPalavrasChaves)) {
			Predicate disjunction = builder.disjunction();
			
			String[] splitPalavrasChaves = filtrosPalavrasChaves.split(" ");
			
			for (String palavraChaveVar : splitPalavrasChaves) {
				disjunction = builder.or(disjunction
						, builder.like(root.<String>get("palavrasChaves"), toLikeMatchModeANY(palavraChaveVar) )
					);
			}
			conjunction = builder.and(conjunction, disjunction);
		}
		
		criteria.where(conjunction);
		criteria.orderBy( builder.asc(root.<String>get("titulo")) );
		
		return manager.createQuery(criteria).getResultList();
	}


	
	
	
	

}
