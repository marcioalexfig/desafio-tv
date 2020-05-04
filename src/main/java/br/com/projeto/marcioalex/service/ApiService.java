package br.com.projeto.marcioalex.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projeto.marcioalex.dto.ArquivoDTO;
import br.com.projeto.marcioalex.modelo.Arquivo;
import br.com.projeto.marcioalex.repository.ApiRepository;

@Service
public class ApiService {

	@Autowired
	private ApiRepository apiRepository;
	
	public void metodoTeste() {
		System.out.println("#####  Metodo de Teste  #####");
	}
	/**
	 * busca na base de dados (retorna arquivo)
	 * @param title
	 * @return
	 */
	public Arquivo retornaArquivoPorTitle (String title) {
		Arquivo e = apiRepository.findArquivoByTitle(title);
		return e;
	}
	
	
	/**
	 * busca na base de dados (retorna arquivo)
	 * @param title
	 * @return
	 */
	public Arquivo retornaArquivoPorName (String name) {
		Arquivo e = apiRepository.findArquivoByName(name);
		return e;
	}
	/**
	 * Cadastrar arquivo e retornar DTO para o Json/Rest
	 * @param arquivo
	 * @return
	 */
	public Arquivo cadastrarArquivo(ArquivoDTO arquivoDTO) {
		Arquivo arquivo = new Arquivo();
		//preenchendo objeto com dados do parametros e dados gerados
		arquivo.setTitle(arquivoDTO.getTitle());
		arquivo.setDuration(arquivoDTO.getDuration());
		arquivo.setStartTime(arquivoDTO.getStartTime());
		arquivo.setEndTime(arquivoDTO.getEndTime());
		arquivo.setReconcileKey(arquivoDTO.getReconcileKey());
		arquivo.setName(arquivoDTO.getName());
		
		//retorno do cadastro
		Arquivo arquivoCadastrado = apiRepository.salvar(arquivo);
		return arquivoCadastrado;
	}
	
	/**
	 * Verifica se um alias já existe na base de dados
	 * @param alias
	 * @return
	 */
	public boolean validarTitle(String title) {
		boolean existe = false;
		Arquivo arquivoCadastrado = apiRepository.findArquivoByTitle(title);
		if (arquivoCadastrado!=null) {
			existe = true;
		}
		return existe;
	}

	
}
