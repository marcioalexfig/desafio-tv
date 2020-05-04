package br.com.projeto.marcioalex.dto;

public class RetornoCorteDTO {
	private String idJobCorte;
	private String statusJobCorte;

	public RetornoCorteDTO() {}
	
	public String getIdJobCorte() {
		return idJobCorte;
	}

	public void setIdJobCorte(String idJobCorte) {
		this.idJobCorte = idJobCorte;
	}

	public String getStatusJobCorte() {
		return statusJobCorte;
	}

	public void setStatusJobCorte(String statusJobCorte) {
		this.statusJobCorte = statusJobCorte;
	}

	
	public RetornoCorteDTO(String idJobCorte, String statusJobCorte) {
		super();
		this.idJobCorte = idJobCorte;
		this.statusJobCorte = statusJobCorte;
	}
	
	
}
