package br.edu.uniplac.niu.controller.helper;

import java.io.Serializable;

import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;

import br.edu.uniplac.niu.model.entity.enumeration.ChamadoPrioridade;
import br.edu.uniplac.niu.model.entity.enumeration.ChamadoStatus;
import br.edu.uniplac.niu.model.entity.enumeration.UsuarioPerfil;

/**
 * Auxiliar para enums
 * @author vitor
 * @since 04 jan 2016
 */
@ManagedBean(name="enumHelper")
@ApplicationScoped
public class EnumHelper implements Serializable {
	
	private static final long serialVersionUID = 982660497352825395L;


	
	public ChamadoPrioridade[] getChamadoPrioridades() {
		return ChamadoPrioridade.values();
	}
	
	
	public ChamadoStatus[] getChamadoStatus() {
		return ChamadoStatus.values();
	}
	
	
	public UsuarioPerfil[] getPerfis() {
		return UsuarioPerfil.values();
	}

}
