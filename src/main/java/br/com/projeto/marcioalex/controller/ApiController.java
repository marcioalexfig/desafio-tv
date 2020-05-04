package br.com.projeto.marcioalex.controller;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.marcioalex.dto.ArquivoDTO;
import br.com.projeto.marcioalex.modelo.Arquivo;
import br.com.projeto.marcioalex.response.Response;
import br.com.projeto.marcioalex.service.ApiService;

//http://localhost:8080/swagger-ui.html

@RestController
@RequestMapping("/api/arquivos")
public class ApiController {
	
	@Autowired
	private ApiService apiService;
	
	@Value("${txt.origem}")
	private String PASTAORIGEM;
	
	@Value("${txt.processado}")
	private String PASTAPROCESSADO;

	@Value("${txt.extensao}")
	private String EXTENSAO;
	
	@Value("${video.origem}")
	private String VIDEOORIGEM;
	
	@Value("${video.destino}")
	private String VIDEODESTINO;
	
	@Value("${status.preparando}")
	private String PREPARANDO;
	
	@Value("${status.cortando}")
	private String CORTANDO;
	
	@Value("${status.cortado}")
	private String CORTADO;
	
	

	
	/**
	 * Shorten URL
	 * @Valid habilita as validações declaradas no DTO
	 * @param resultadoValidacao  é o retorno com as mensagens de erro das validação de dentro do DTO
	 * @return
	 */
	@PutMapping	
	public ResponseEntity<Response<ArquivoDTO>> cadastrarArquivo(@Valid @RequestBody ArquivoDTO arquivoDto, BindingResult resultadoValidacao)	{
		
		Instant horaChamada = Instant.now(); 
		Response<ArquivoDTO> resposta = new Response<ArquivoDTO>();
		List<String> erros = new ArrayList<String>();
		
		try {
			//verifica erros de validação (bean validation) e adiciona o que estiver no response
			if (!resultadoValidacao.getAllErrors().isEmpty()) {
				
				resultadoValidacao.getAllErrors().forEach(erro -> resposta.getErros().add(erro.getDefaultMessage()));
				
				//para o processamento e retorna os erros
				return ResponseEntity.badRequest().body(resposta);
			} 
			
			//verifica se o title enviado já existe na base de dados
			if (arquivoDto.getTitle()!=null) {
				if (apiService.validarTitle(arquivoDto.getTitle())) {
					resposta.setErr_code("001");
					resposta.setErr_description("TITLE JÁ EXISTE NA BASE");
					return ResponseEntity.badRequest().body(resposta);
				}
			}
			
			//se não tem erro, chama o serviço e grava na base de dados
			Arquivo arquivoCadastrado = apiService.cadastrarArquivo(arquivoDto);
			arquivoDto.setTitle(arquivoCadastrado.getTitle());
			Instant horaRetorno = Instant.now(); 
			//arquivoDto.setTempo(TimeUtils.tempoTotal(horaChamada, horaRetorno)); 
			arquivoDto.setDuration(arquivoCadastrado.getDuration());
			arquivoDto.setStartTime(arquivoCadastrado.getStartTime());
			arquivoDto.setEndTime(arquivoCadastrado.getEndTime());
			arquivoDto.setReconcileKey(arquivoCadastrado.getReconcileKey());
			arquivoDto.setName(arquivoCadastrado.getName());
			resposta.setDados(arquivoDto);
		} catch (Exception e) {
			erros.add(e.getLocalizedMessage());
			erros.forEach(erro -> resposta.getErros().add(erro));
			//para tudo e retorna os erros
			return ResponseEntity.badRequest().body(resposta);
		}

		return ResponseEntity.ok().body(resposta);
		
	}
	
	/**
	 * Retrieve title
	 * @param alias
	 * @return
	 */
	@GetMapping (value = "/title/{title}")
	public ResponseEntity retornarArquivoPorTitle( @RequestBody @PathVariable("title") String title)	{
		
		Response<String> resposta = new Response<String>();
		List<String> erros = new ArrayList<String>();
		Arquivo arquivoCadastrado = null;
		
		try {
			
			//verifica se o custon alias enviado existe na base de dados
			if (title!=null) {
				arquivoCadastrado = apiService.retornaArquivoPorTitle(title);
				if (arquivoCadastrado==null) {
					resposta.setErr_code("002");
					resposta.setErr_description("ARQUIVO NÃO ENCONTRADO!");
					return ResponseEntity.badRequest().body(resposta);
				}
			}

		} catch (Exception e) {
			erros.add(e.getLocalizedMessage());
			erros.forEach(erro -> resposta.getErros().add(erro));
			//para tudo e retorna os erros
			return ResponseEntity.badRequest().body(resposta);
		}
		
		return ResponseEntity.ok().body(resposta);

	}
	
	/**
	 * Retrieve name
	 * @param alias
	 * @return
	 */
	@GetMapping (value = "/name/{name}")
	public ResponseEntity retornarArquivoPorName( @RequestBody @PathVariable("name") String name)	{
		
		Response<String> resposta = new Response<String>();
		List<String> erros = new ArrayList<String>();
		Arquivo arquivoCadastrado = null;
		
		try {
			
			//verifica se o custon alias enviado existe na base de dados
			if (name!=null) {
				arquivoCadastrado = apiService.retornaArquivoPorName(name);
				if (arquivoCadastrado==null) {
					resposta.setErr_code("002");
					resposta.setErr_description("ARQUIVO NÃO ENCONTRADO!");
					return ResponseEntity.badRequest().body(resposta);
				}
			}

		} catch (Exception e) {
			erros.add(e.getLocalizedMessage());
			erros.forEach(erro -> resposta.getErros().add(erro));
			//para tudo e retorna os erros
			return ResponseEntity.badRequest().body(resposta);
		}
		
		return ResponseEntity.ok().body(resposta);

	}

	
}
