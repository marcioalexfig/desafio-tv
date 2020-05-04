package br.com.projeto.marcioalex.processamentos;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import br.com.projeto.marcioalex.dto.ArquivoCorteDTO;
import br.com.projeto.marcioalex.dto.ArquivoDTO;
import br.com.projeto.marcioalex.dto.RetornoCorteDTO;
import br.com.projeto.marcioalex.modelo.Arquivo;
import br.com.projeto.marcioalex.repository.ApiRepository;
import br.com.projeto.marcioalex.response.Response;
import br.com.projeto.marcioalex.service.ApiService;
import br.com.projeto.marcioalex.util.ApiUtils;
import br.com.projeto.marcioalex.util.FileUtils;

@Component
@EnableScheduling
public class ProcessaArquivos {
	@Value("${txt.origem}")
	private String TXTORIGEM;
	
	@Value("${video.destino}")
	private String VIDEODESTINO;
	
	@Value("${txt.processado}")
	private String TXTPROCESSADO;

	@Value("${txt.extensao}")
	private String EXTENSAO;
	
	@Value("${video.type}")
	private String TYPE;
	
	@Value("${url.apicorte}")
	private String URL_APICORTE;
	
	@Value("${porta.apicorte}")
	private String PORTA_APICORTE;
	
	@Value("${path.apicorte}")
	private String PATH_APICORTE;
	
	private final long INTERVALO_PROCESSAMENTO = 1000;
	
	@Autowired
	private ApiRepository apiRepository;
	
	/**
	 * Método lê os arquivos de texto
	 * Grava no repositório os que tenham mais de 30s
	 * Envia post para api de corte
	 */
	@Scheduled(fixedDelay = INTERVALO_PROCESSAMENTO) 
    public void processaArquivos() { 
		File file = new File(TXTORIGEM);
		File[] arquivos = file.listFiles();
		for (File fileTmp : arquivos) {
			String nome = fileTmp.getName();
			String ext = null;
			if(nome!=null)ext = nome.substring(nome.length()-3, nome.length());
			//limita a leitura a arquivos TXT
			if(ext!=null && ext.toUpperCase().equals(EXTENSAO)) {
				//Ler arquivo de texto e pega os vídeos com mais de 30s
				List<Arquivo> listaArquivos = FileUtils.lerArquivo(nome, TXTORIGEM, TYPE);
				if( listaArquivos.size() > 0 ) {
					for(Arquivo a : listaArquivos) {
						//verifica se o arquivo de video já esta cadastrado
						Arquivo arq = apiRepository.findArquivoByReconcileKey(a.getReconcileKey());
						if(arq==null) {
							//caso o arquivo não exista na base, grave.
							apiRepository.salvar(a);
							//envia para a api de corte (POST)
							RestTemplate restTemplate = new RestTemplate();
						    final String baseUrl = URL_APICORTE+":"+PORTA_APICORTE+PATH_APICORTE;
						    URI uri;
							try {
								uri = new URI(baseUrl);
								ArquivoCorteDTO arqc = ApiUtils.converterArquivoEmCorteDTO(a, VIDEODESTINO);
								if( arqc!=null ) {
									ResponseEntity<RetornoCorteDTO> result = restTemplate.postForEntity(uri, arqc, RetornoCorteDTO.class);
									a.setStatusJobCorte(result.getBody().getStatusJobCorte());
									a.setIdJobCorte(result.getBody().getIdJobCorte());
									apiRepository.salvar(a);
								}
							} catch (URISyntaxException e) {
								e.printStackTrace();
							}
						}
					}
				}
				//Mover arquivo texto para processados
				FileUtils.moverArquivo(nome, TXTORIGEM, TXTPROCESSADO);
			}
		}
    }
	
	@Scheduled(fixedDelay = 1000) 
    public void verificaArquivos() {
		
	}
}
