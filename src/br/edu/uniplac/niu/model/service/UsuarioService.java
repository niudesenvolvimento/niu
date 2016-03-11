package br.edu.uniplac.niu.model.service;

import static br.edu.uniplac.niu.model.util.QueryUtil.isNotBlank;
import static br.edu.uniplac.niu.model.util.QueryUtil.isNotNull;
import static br.edu.uniplac.niu.model.util.QueryUtil.toLikeMatchModeANY;

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

import br.edu.uniplac.niu.model.entity.ChamadoCategoria;
import br.edu.uniplac.niu.model.entity.UsuarioNIU;
import br.edu.uniplac.niu.model.entity.enumeration.UsuarioPerfil;
import br.edu.uniplac.niu.model.exception.NegocioException;

/**
 * Servicos de negocio para Usuario
 * @author Vitor
 * @since 28 dez 2015
 */
@Stateless
public class UsuarioService {
	
	private static final String CRIADOR_AUTOMATICO = "UsuarioService";
	
	
	@PersistenceContext EntityManager manager;
	
	
	
	
	/**
	 * Salva instancia de usuario
	 * @param u
	 * @param criadorOuAtualizador 
	 * @return
	 */
	public UsuarioNIU salvarUsuarioNIU(UsuarioNIU u, String criadorOuAtualizador) {
		verificarSeLoginDoUsuarioJaExiste( u );
		
		gravarInfoLog( u, criadorOuAtualizador );
		return manager.merge(u);
	}
	
	
	/**
	 * Salva usuario recem criado com criado automatico.
	 * @param usuarioRecemCriado
	 * @return
	 */
	public UsuarioNIU salvarUsuarioRecemCriado(UsuarioNIU usuarioRecemCriado) {
		return salvarUsuarioNIU(usuarioRecemCriado, CRIADOR_AUTOMATICO);
	}
	
	
	/**
	 * RN que garante a unicidade
	 * @param u
	 */
	private void verificarSeLoginDoUsuarioJaExiste(UsuarioNIU u) {
		UsuarioNIU usuarioEncontrado = buscarUsuarioNIUPeloLogin( u.getLogin() );
		if (usuarioEncontrado!=null && !usuarioEncontrado.equals(u) ) {
			throw new NegocioException("Login já usado por outro usuário");
		}
	}


	private void gravarInfoLog(UsuarioNIU u, String criadorOuAtualizador) {
		if (u.isTransient()) {
			u.getInfoLog().setCriadoEm( new Date() );
			u.getInfoLog().setCriadoPor( criadorOuAtualizador );;
		} else {
			u.getInfoLog().setAtualizadoEm( new Date() );
			u.getInfoLog().setAtualizadoPor( criadorOuAtualizador );;
		}
	}


	
//	public UsuarioNIU trocarSenha(UsuarioNIU usuario, String novaSenha, String atualizador) {
//		usuario.setSenha( novaSenha );
//		gravarInfoLog(usuario, atualizador);
//		
//		return manager.merge( usuario );
//	}
	
	
	/**
	 * Remove instancia de usuario
	 * @param u
	 */
	public void removerUsuarioNIU(UsuarioNIU u) {
		manager.remove( manager.merge(u) );
	}
	
	

	/**
	 * Recarrega usuario com as categorias
	 * Workarround para evitar LIE
	 * @param usuario
	 * @return
	 */
	public UsuarioNIU carregarUsuario(UsuarioNIU usuario) {
		//1.traz para gerenciado
		usuario = manager.find(UsuarioNIU.class, usuario.getId() );
		//2.força carga
		usuario.getCategorias().size();
		//3.retorna
		return usuario;
	}
	
	
	/**
	 * Busca usuario pelo login
	 * (usado para garantir unicidade de login)
	 * @param login
	 * @return
	 */
	public UsuarioNIU buscarUsuarioNIUPeloLogin(String login) {
		try {
			return manager.createNamedQuery("buscarUsuarioNIUPeloLogin", UsuarioNIU.class)
					.setParameter("pLogin", login)
					.getSingleResult();
			
		} catch (NoResultException e) {
			return null;
		}
	}
	
	
	/**
	 * Busca um usuario pelo login ou cria um novo
	 * @param login
	 * @return
	 */
	public UsuarioNIU buscarUsuarioPeloLoginOuCriar(String login) {
		UsuarioNIU usuarioEncontrado = buscarUsuarioNIUPeloLogin(login);
		if (usuarioEncontrado!=null) {
			return usuarioEncontrado;
			
		} else {
			
			UsuarioNIU novoUsuario = new UsuarioNIU();
			novoUsuario.setPerfil( UsuarioPerfil.FUN );
			novoUsuario.setLogin(login);
			return salvarUsuarioNIU(novoUsuario, CRIADOR_AUTOMATICO);
		}
	}
	
	
	
	/**
	 * Busca um usuario pelo email e senha. Usado na autenticacao
	 * @param login
	 * @param senha
	 * @return
	 */
//	public UsuarioNIU buscarUsuarioPeloLoginESenha(String login, String senha) {
//		try {
//			return manager.createNamedQuery("buscarUsuarioPeloLoginESenha", UsuarioNIU.class)
//					.setParameter("pLogin", login)
//					.setParameter("pSenha", senha)
//					.getSingleResult();
//		} catch (NoResultException e) {
//			return null;
//		}
//	}

	
	/**
	 * Pesquisar usuario pelos filtros usando criteria
	 * @return
	 */
	public List<UsuarioNIU> pesquisarUsuarioNIUPelosFiltros(String login, UsuarioPerfil perfil) {
		CriteriaBuilder builder = manager.getCriteriaBuilder();
		CriteriaQuery<UsuarioNIU> criteria = builder.createQuery(UsuarioNIU.class);
		Root<UsuarioNIU> root = criteria.from(UsuarioNIU.class);
		
		Predicate conjunction = builder.conjunction();
		//1.login
		if (isNotBlank(login)) {
			conjunction = builder.and(conjunction, 
					builder.like(root.<String>get("login"), toLikeMatchModeANY(login))
				);
		}
		//2.profile
		if (isNotNull(perfil)) {
			conjunction = builder.and(conjunction, 
					builder.equal(root.<UsuarioPerfil>get("perfil"), perfil)
				);
		}
		criteria.where( conjunction );
		criteria.orderBy( builder.asc(root.<String>get("login")) );
		return manager.createQuery(criteria).getResultList();
	}
	
	
	
	/**
	 * Pesquisa Usuario pela categoria
	 * (para enviar email sempre que um chamado é criado)
	 * @param categoria
	 * @return
	 */
	public List<UsuarioNIU> pesquisarUsuarioPelaCategoria(ChamadoCategoria categoria) {
		return manager.createNamedQuery("pesquisarUsuarioPelaCategoria", UsuarioNIU.class)
				.setParameter("pCategoria", categoria)
				.getResultList();
	}


	
	
}
