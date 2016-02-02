package br.edu.uniplac.niu.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * Embutivel com as informações de log
 * @author Vitor
 * @since 04 jan 2016
 */
@Embeddable
public class InfoLog implements Serializable {


	@Temporal(TemporalType.TIMESTAMP)
	private Date criadoEm;

	private String criadoPor;

	
	@Temporal(TemporalType.TIMESTAMP)
	private Date atualizadoEm;
	
	private String atualizadoPor;
	
	
	


	
	//acessores...
	private static final long serialVersionUID = -4585775216418265479L;
	public Date getCriadoEm() {
		return criadoEm;
	}

	public void setCriadoEm(Date criadoEm) {
		this.criadoEm = criadoEm;
	}

	public String getCriadoPor() {
		return criadoPor;
	}

	public void setCriadoPor(String criadoPor) {
		this.criadoPor = criadoPor;
	}

	public Date getAtualizadoEm() {
		return atualizadoEm;
	}

	public void setAtualizadoEm(Date atualizadoEm) {
		this.atualizadoEm = atualizadoEm;
	}

	public String getAtualizadoPor() {
		return atualizadoPor;
	}

	public void setAtualizadoPor(String atualizadoPor) {
		this.atualizadoPor = atualizadoPor;
	}
	
	
	//util
	public Boolean getFlagJaFoiAtualizado() {
		return getAtualizadoEm()!=null;
	}
	
	
	
}
