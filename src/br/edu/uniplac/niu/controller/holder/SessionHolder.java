package br.edu.uniplac.niu.controller.holder;

import java.io.Serializable;


import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.edu.uniplac.niu.controller.util.JSFUtil;
import br.edu.uniplac.niu.model.entity.UsuarioNIU;

/**
 * Objeto que guarda infos relevantes na sessao
 * @author Vitor
 * @since 28 dez 2015
 */
@ManagedBean(name="sessionHolder")
@SessionScoped
public class SessionHolder implements Serializable {
	
	private static final String USER_KEY = "USER";

	private UsuarioNIU usuario;
	
	
	
	public void initSessao(UsuarioNIU usuario) {
		this.usuario = usuario;
		guardarUsuarioNaSessao(usuario);
	}
	

	public void finalizarSessao() {
		invalidarSessao();
		retirarUsuarioDaSessao();
	}




	
	
	//util
	private void guardarUsuarioNaSessao(UsuarioNIU user) {
		JSFUtil.getHttpSession().setAttribute(USER_KEY, user);
	}
	
	private void invalidarSessao() {
		JSFUtil.getHttpSession().invalidate();
	}

	private void retirarUsuarioDaSessao() {
		JSFUtil.getHttpSession().removeAttribute(USER_KEY);
	}
	
	
	//acessores...
	private static final long serialVersionUID = 317919622145173395L;

	public UsuarioNIU getUsuario() {
		return usuario;
	}


	public Boolean getFlagLogado() {
		return usuario!=null;
	}
	

	
}
