package br.edu.uniplac.niu.model.entity.enumeration;

import java.io.Serializable;

/**
 * Perfil de acesso de usuarios
 * @author Vitor
 * @since 28 dez 2015
 */
public enum UsuarioPerfil implements Serializable {

	INF("Informática")
	,
	FUN("Funcionário")
	;
	
	private final String descricao;

	
	private UsuarioPerfil(String descricao) {
		this.descricao = descricao;
	}

	
	public String getDescricao() {
		return descricao;
	}
	

}
