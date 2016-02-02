package br.edu.uniplac.niu.model.entity.enumeration;

/**
 * Possivel status de um chamado
 * @author vitor
 * @since 04 jan 2016
 */
public enum ChamadoStatus {
	
	CRIADO("Criado", "icone_chamado_status_CRIADO_96.png")
	,
	EXECUTANDO("Executando", "icone_chamado_status_EXECUTANDO_96.png")
	,
	TESTANDO("Testando pelo usuário", "icone_chamado_status_TESTANDO_96.png")
	,
	DEVOLVIDO("Devolvido pelo usuário", "icone_chamado_status_DEVOLVIDO_96.png")
	,
	RESOLVIDO("Resolvido", "icone_chamado_status_RESOLVIDO_96.png")
	;
	
	private final String descricao;
	private final String img;


	private ChamadoStatus(String descricao, String img) {
		this.descricao = descricao;
		this.img = img;
	}

	
	
	public String getDescricao() {
		return descricao;
	}



	public String getImg() {
		return img;
	}
	
	

}
