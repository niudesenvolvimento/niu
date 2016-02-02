package br.edu.uniplac.niu.model.entity;

import java.io.Serializable;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.edu.uniplac.niu.model.entity.enumeration.UsuarioPerfil;

/**
 * Representa o usuario do sistema: seu email, sua senha e perfil
 * @author Vitor
 * @since 28 dez 2015
 */
@Entity
public class UsuarioNIU implements Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	

	@NotNull
	@Size(max=20)
	private String login;


	@Size(max=500)
	private String senha;


	@Enumerated(EnumType.STRING)
	private UsuarioPerfil perfil = UsuarioPerfil.FUN;
	
	
	private Boolean flagAtivo = true;
	
	
	@Embedded
	private InfoLog infoLog;
	

	
	//acessores...
	private static final long serialVersionUID = -389323302794695781L;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}


	public InfoLog getInfoLog() {
		if (infoLog==null) {
			infoLog=new InfoLog();
		}
		return infoLog;
	}

	public Boolean getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Boolean flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public void setInfoLog(InfoLog infoLog) {
		this.infoLog = infoLog;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public UsuarioPerfil getPerfil() {
		return perfil;
	}

	public void setPerfil(UsuarioPerfil perfil) {
		this.perfil = perfil;
	}



	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UsuarioNIU other = (UsuarioNIU) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public boolean isTransient() {
		return getId()==null;
	}

	
	//flag de perfil
	public Boolean getFlagPerfilInformatica() {
		return getPerfil().equals( UsuarioPerfil.INF );
	}
	
	public Boolean getFlagPerfilFuncionario() {
		return getPerfil().equals( UsuarioPerfil.FUN );
	}
	
	
}
