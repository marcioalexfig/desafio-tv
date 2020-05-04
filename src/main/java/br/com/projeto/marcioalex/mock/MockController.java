package br.com.projeto.marcioalex.mock;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projeto.marcioalex.dto.ArquivoCorteDTO;
import br.com.projeto.marcioalex.dto.RetornoCorteDTO;
import br.com.projeto.marcioalex.modelo.Arquivo;
import br.com.projeto.marcioalex.repository.ApiRepository;
import br.com.projeto.marcioalex.response.Response;
import br.com.projeto.marcioalex.util.FileUtils;


/**
 * Simula serviço de geração de e corte de vídeos
 * @author marcio.rodrigues
 *
 */
@EnableScheduling
@RestController
@RequestMapping("/api/mock")
public class MockController {
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
	
	private final long INTERVALO_GERACAO_VIDEO = 5000;
	
	@Autowired
	private ApiRepository apiRepository;
	
	/**
	 * Método lê os arquivos na base para gerar os arquivos de video e mudar o status do Job de corte
	 */
	@Scheduled(fixedDelay = INTERVALO_GERACAO_VIDEO) 
    public void geraArquivoVideo() { 
		List<Arquivo> arquivos = apiRepository.findAll();
		for (Arquivo arq : arquivos) {
			//se o arquivo estiver em processo de corte... gerar novo mp4
			if(arq.getStatusJobCorte().equals(CORTANDO)) {
				System.out.println("-----------------------GERANDO ARQUIVO DE VIDEO----------------------------");
				System.out.println(arq.getName());
				FileUtils.gravarArquivo(arq.getName(), VIDEODESTINO);
				arq.setStatusJobCorte(CORTADO);
				apiRepository.salvar(arq);
			}
		} 
	}
	
	/**
	 * Mock da API de Corte 
	 * @param arquivoDto
	 * @param resultadoValidacao
	 * @return
	 */
	@PostMapping (value = "/corte")
	public ResponseEntity<Response<RetornoCorteDTO>> cortarVideo(@Valid @RequestBody ArquivoCorteDTO arquivoDto, BindingResult resultadoValidacao)	{
		
		Instant horaChamada = Instant.now(); 
		Response<RetornoCorteDTO> resposta = new Response<RetornoCorteDTO>();
		List<String> erros = new ArrayList<String>();
		
		try {
			BCryptPasswordEncoder bcript = new BCryptPasswordEncoder();
			String idJob = bcript.encode(arquivoDto.getName());
			resposta.setIdJobCorte(idJob);
			resposta.setStatusJobCorte(CORTANDO);
		} catch (Exception e) {
			erros.forEach(erro -> resposta.getErros().add(erro));
			//para tudo e retorna os erros
			RetornoCorteDTO retornoCorteDTO = new RetornoCorteDTO();
			resposta.setDados(retornoCorteDTO);
			resposta.setErr_description("ERRO AO CORTAR VIDEO");
			return ResponseEntity.badRequest().body(resposta);
		}
		return ResponseEntity.ok().body(resposta);
	}
}
