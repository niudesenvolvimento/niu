package br.edu.uniplac.niu.model.entity.enumeration;

/**
 * Prioridade de um chamado
 * @author Vitor
 * @since 04 jan 2016
 */
public enum ChamadoPrioridade {
	
	BAIXA("Baixa")
	,
	MEDIA("Média")
	,
	ALTA("Alta")
	;
	
	
	private final String descricao;

	
	private ChamadoPrioridade(String descricao) {
		this.descricao = descricao;
	}


	public String getDescricao() {
		return descricao;
	}


}
