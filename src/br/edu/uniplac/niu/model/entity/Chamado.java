package br.edu.uniplac.niu.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import br.edu.uniplac.niu.model.entity.enumeration.ChamadoPrioridade;
import br.edu.uniplac.niu.model.entity.enumeration.ChamadoStatus;
import br.edu.uniplac.niu.model.exception.NegocioException;

/**
 * Chamado aberto por um usuario
 * @author Vitor
 * @since 04 jan 2016
 */
@Entity
public class Chamado implements Serializable {

	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	
	@NotNull
	private String titulo;
	
	
	@NotNull
	@Size(max=5000)
	private String detalhe;
	

	@Enumerated(EnumType.STRING)
	private ChamadoPrioridade prioridade = ChamadoPrioridade.BAIXA;
	
	
	@Enumerated(EnumType.STRING)
	private ChamadoStatus status = ChamadoStatus.CRIADO;
	
	
	private String responsavel;
	
	
	@ManyToOne
	private ChamadoCategoria categoria;
	
	

	//log dos status...
	
	@Temporal(TemporalType.TIMESTAMP)
	private Date criadoEm;
	
	@NotNull
	private String criadoPor;

	
	@Temporal(TemporalType.TIMESTAMP)
	private Date executandoEm;
	
	private String executandoPor;
	

	@Temporal(TemporalType.TIMESTAMP)
	private Date testandoEm;
	
	private String testandoPor;
	

	@Temporal(TemporalType.TIMESTAMP)
	private Date devolvidoEm;
	
	private String devolvidoPor;
	

	@Temporal(TemporalType.TIMESTAMP)
	private Date resolvidoEm;
	
	private String resolvidoPor;
	

	
	@Temporal(TemporalType.TIMESTAMP)
	private Date dataUltimaAtualizacao;
	
	
	
	

	//listeners...
	@PrePersist void onCreate() {
		setCriadoEm( new Date() );
	}
	
	@PreUpdate void onUpdate() {
		setDataUltimaAtualizacao( new Date() );
	}
	
	
	

	//acessores...
	private static final long serialVersionUID = -7565066869209119188L;
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ChamadoCategoria getCategoria() {
		return categoria;
	}

	public void setCategoria(ChamadoCategoria categoria) {
		this.categoria = categoria;
	}

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

	public Date getExecutandoEm() {
		return executandoEm;
	}

	public void setExecutandoEm(Date executandoEm) {
		this.executandoEm = executandoEm;
	}

	public String getExecutandoPor() {
		return executandoPor;
	}

	public void setExecutandoPor(String executandoPor) {
		this.executandoPor = executandoPor;
	}

	public Date getTestandoEm() {
		return testandoEm;
	}

	public void setTestandoEm(Date testandoEm) {
		this.testandoEm = testandoEm;
	}

	public String getTestandoPor() {
		return testandoPor;
	}

	public void setTestandoPor(String testandoPor) {
		this.testandoPor = testandoPor;
	}

	public Date getDevolvidoEm() {
		return devolvidoEm;
	}

	public void setDevolvidoEm(Date devolvidoEm) {
		this.devolvidoEm = devolvidoEm;
	}

	public String getDevolvidoPor() {
		return devolvidoPor;
	}

	public void setDevolvidoPor(String devolvidoPor) {
		this.devolvidoPor = devolvidoPor;
	}

	public Date getResolvidoEm() {
		return resolvidoEm;
	}

	public void setResolvidoEm(Date resolvidoEm) {
		this.resolvidoEm = resolvidoEm;
	}

	public String getResolvidoPor() {
		return resolvidoPor;
	}

	public void setResolvidoPor(String resolvidoPor) {
		this.resolvidoPor = resolvidoPor;
	}

	public Date getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}

	public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDetalhe() {
		return detalhe;
	}

	public void setDetalhe(String detalhe) {
		this.detalhe = detalhe;
	}

	public ChamadoPrioridade getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(ChamadoPrioridade prioridade) {
		this.prioridade = prioridade;
	}

	public ChamadoStatus getStatus() {
		return status;
	}

	public void setStatus(ChamadoStatus status) {
		this.status = status;
	}

	public String getResponsavel() {
		return responsavel;
	}

	public void setResponsavel(String responsavel) {
		this.responsavel = responsavel;
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
		Chamado other = (Chamado) obj;
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
	
	
	//flag de status
	
	public Boolean getFlagCriado() {
		return ChamadoStatus.CRIADO.equals( getStatus() );
	}

	public Boolean getFlagExecutando() {
		return ChamadoStatus.EXECUTANDO.equals( getStatus() );
	}

	public Boolean getFlagTestando() {
		return ChamadoStatus.TESTANDO.equals( getStatus() );
	}
	
	public Boolean getFlagDevolvido() {
		return ChamadoStatus.DEVOLVIDO.equals( getStatus() );
	}

	public Boolean getFlagResolvido() {
		return ChamadoStatus.RESOLVIDO.equals( getStatus() );
	}
	
	
	//transicoes status
	public void paraStatusExecutando(String execPor) {
		if (getStatus().equals(ChamadoStatus.CRIADO) || getStatus().equals(ChamadoStatus.DEVOLVIDO) ) {
			setStatus( ChamadoStatus.EXECUTANDO );
			setExecutandoEm( new Date() );
			setExecutandoPor( execPor );
			
		} else {
			String msg = String.format("Inválida a transição de status de %s para %s", getStatus(), ChamadoStatus.EXECUTANDO );
			throw new NegocioException( msg );
		}
	}
	

	public void paraStatusTestando(String testPor) {
		if (getStatus().equals(ChamadoStatus.EXECUTANDO)) {
			setStatus( ChamadoStatus.TESTANDO );
			setTestandoEm( new Date() );
			setTestandoPor( testPor );
			
		} else {
			String msg = String.format("Inválida a transição de status de %s para %s", getStatus(), ChamadoStatus.TESTANDO );
			throw new NegocioException( msg );
		}
	}
	

	public void paraStatusDevolvido(String devPor) {
		if (getStatus().equals(ChamadoStatus.TESTANDO)) {
			setStatus( ChamadoStatus.DEVOLVIDO );
			setDevolvidoEm( new Date() );
			setDevolvidoPor( devPor );
			
		} else {
			String msg = String.format("Inválida a transição de status de %s para %s", getStatus(), ChamadoStatus.DEVOLVIDO );
			throw new NegocioException( msg );
		}
	}
	
	
	
	public void paraStatusResolvido(String resPor) {
		if (getStatus().equals(ChamadoStatus.TESTANDO)) {
			setStatus( ChamadoStatus.RESOLVIDO );
			setResolvidoEm( new Date() );
			setResolvidoPor( resPor );
			
		} else {
			String msg = String.format("Inválida a transição de status de %s para %s", getStatus(), ChamadoStatus.RESOLVIDO );
			throw new NegocioException( msg );
		}
	}
	
}
