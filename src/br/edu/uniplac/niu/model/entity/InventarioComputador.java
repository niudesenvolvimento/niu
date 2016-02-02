package br.edu.uniplac.niu.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 * Computador dentro do inventario da universidade
 * @author vitor.figueiredo
 * @since 11 jan 2016
 */
@Entity
public class InventarioComputador implements Serializable {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	
	@NotNull
	private String nomeComputador;
	
	
	private String nomeUsuario;
	
	
	@ManyToOne
	private ComputadorMarca marca;
	
	
	@ManyToOne
	private Setor setor;
	
	
	private String configuracao;
	
	
	private String versaoRMInstalada;
	
	
	@Temporal(TemporalType.DATE)
	private Date rmInstaladoEm;
	
	
	private String rmInstaladoPor;


	
	//acessores...
	private static final long serialVersionUID = 8738807312007432687L;
	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getNomeComputador() {
		return nomeComputador;
	}


	public void setNomeComputador(String nomeComputador) {
		this.nomeComputador = nomeComputador;
	}


	public String getNomeUsuario() {
		return nomeUsuario;
	}


	public void setNomeUsuario(String nomeUsuario) {
		this.nomeUsuario = nomeUsuario;
	}


	public ComputadorMarca getMarca() {
		return marca;
	}


	public void setMarca(ComputadorMarca marca) {
		this.marca = marca;
	}


	public Setor getSetor() {
		return setor;
	}


	public void setSetor(Setor setor) {
		this.setor = setor;
	}


	public String getConfiguracao() {
		return configuracao;
	}


	public void setConfiguracao(String configuracao) {
		this.configuracao = configuracao;
	}


	public String getVersaoRMInstalada() {
		return versaoRMInstalada;
	}


	public void setVersaoRMInstalada(String versaoRMInstalada) {
		this.versaoRMInstalada = versaoRMInstalada;
	}


	public Date getRmInstaladoEm() {
		return rmInstaladoEm;
	}


	public void setRmInstaladoEm(Date rmInstaladoEm) {
		this.rmInstaladoEm = rmInstaladoEm;
	}


	public String getRmInstaladoPor() {
		return rmInstaladoPor;
	}


	public void setRmInstaladoPor(String rmInstaladoPor) {
		this.rmInstaladoPor = rmInstaladoPor;
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
		InventarioComputador other = (InventarioComputador) obj;
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
	
}
