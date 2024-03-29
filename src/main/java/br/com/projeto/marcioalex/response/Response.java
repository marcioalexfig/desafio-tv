package br.com.projeto.marcioalex.response;

import java.util.ArrayList;
import java.util.List;

public class Response<T> {
	
	private T dados;
	private String err_code;
	private String err_description;
	private List<String> erros;
	private String idJobCorte;
	private String statusJobCorte;
	
	public Response() {}
	
	public T getDados() {
		return dados;
	}
	public void setDados(T dados) {
		this.dados = dados;
	}
	public List<String> getErros() {
		if (this.erros == null) {
			this.erros = new ArrayList<String>();
		}
		return erros;
	}
	public void setErros(List<String> erros) {
		this.erros = erros;
	}

	public String getErr_code() {
		return err_code;
	}

	public void setErr_code(String err_code) {
		this.err_code = err_code;
	}

	public String getErr_description() {
		return err_description;
	}

	public void setErr_description(String err_description) {
		this.err_description = err_description;
	}

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
	
	

}
