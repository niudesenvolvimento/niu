package br.edu.uniplac.niu.model.entity;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Size;

/**
 * Manual que faz parte da Base de Conhecimento
 * @author Vitor
 * @since 19 jan 2016
 */
@Entity
public class BCManual implements Serializable {
	
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	
	@Size(max=2000)
	private String titulo;
	

	@Size(max=2000)
	private String caminho;
	
	
	@Size(max=2000)
	private String palavrasChaves;
	
	
	@Embedded
	private InfoLog infoLog;



	//acessores...
	private static final long serialVersionUID = -1326783341814145106L;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getCaminho() {
		return caminho;
	}
	public void setCaminho(String caminho) {
		this.caminho = caminho;
	}
	public String getPalavrasChaves() {
		return palavrasChaves;
	}
	public void setPalavrasChaves(String palavrasChaves) {
		this.palavrasChaves = palavrasChaves;
	}
	public InfoLog getInfoLog() {
		if (infoLog==null) {
			infoLog=new InfoLog();
		}
		return infoLog;
	}
	public void setInfoLog(InfoLog infoLog) {
		this.infoLog = infoLog;
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
		BCManual other = (BCManual) obj;
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
