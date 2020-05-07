package br.com.projeto.marcioalex.test;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.cucumber.java.pt.Dado;
import io.cucumber.java.pt.Entao;
import io.cucumber.java.pt.Quando;

public class TestarAplicacaoCucumber {
	
	private String PASTAORIGEM;
	private String PASTAPROCESSADO;
	private String EXTENSAO;
	private String VIDEOORIGEM;
	private String VIDEODESTINO;
	private String PREPARANDO;
	private String CORTANDO;
	private String CORTADO;
	private static final Logger logger = LoggerFactory.getLogger(TestarAplicacaoCucumber.class);
	
	public TestarAplicacaoCucumber() {
		Properties props = new Properties();
	    FileInputStream file;
		try {
			file = new FileInputStream("./src/main/resources/application.properties");
			props.load(file);
			PASTAORIGEM = props.getProperty("txt.origem");
			PASTAPROCESSADO = props.getProperty("txt.processado");
			EXTENSAO = props.getProperty("txt.extensao");
			VIDEOORIGEM = props.getProperty("video.origem");
			VIDEODESTINO = props.getProperty("video.destino");
			PREPARANDO = props.getProperty("status.preparando");
			CORTANDO = props.getProperty("status.cortando");
			CORTADO = props.getProperty("status.cortado");
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
		}
	}

	
	@Dado("que necessito da pasta DEV")
	public void que_necessito_da_pasta_DEV() {
	    System.out.println("IMPLEMENTAR");
	}

	@Quando("executar o programa")
	public void executar_o_programa() {
		 System.out.println("IMPLEMENTAR");
	}

	@Entao("a pasta DEV necessita existir")
	public void a_pasta_DEV_necessita_existir() {
		File file = new File(PASTAORIGEM);
		File[] arquivos = file.listFiles();
		assertNotNull(arquivos);
	}

}
