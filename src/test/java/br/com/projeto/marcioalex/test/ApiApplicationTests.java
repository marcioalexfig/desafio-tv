package br.com.projeto.marcioalex.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;


public class ApiApplicationTests {

	private String PASTAORIGEM;
	private String PASTAPROCESSADO;
	private String EXTENSAO;
	private String VIDEOORIGEM;
	private String VIDEODESTINO;
	private String PREPARANDO;
	private String CORTANDO;
	private String CORTADO;
	
	@Before
	public void carregarVariaveis() throws FileNotFoundException {
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
			assertNull(e);
		}
	}
	
	
	@Test
	public void verificaVariaveis() {
		assertNotEquals("",PASTAORIGEM);
		assertNotEquals("", PASTAPROCESSADO);
		assertNotEquals("", EXTENSAO);
		assertNotEquals("", VIDEOORIGEM);
		assertNotEquals("", VIDEODESTINO);
		assertNotEquals("", PREPARANDO);
		assertNotEquals("", CORTANDO);
		assertNotEquals("", CORTADO);
	}
	
	@Test
	public void verificaPastas() {
		File file = new File(PASTAORIGEM);
		File[] arquivos = file.listFiles();
		assertNotNull(arquivos);	
		
		file = new File(PASTAPROCESSADO);
		arquivos = file.listFiles();
		assertNotNull(arquivos);
			
		file = new File(VIDEOORIGEM);
		arquivos = file.listFiles();
		assertNotNull(arquivos);
		
		file = new File(VIDEODESTINO);
		arquivos = file.listFiles();
		assertNotNull(arquivos);
			

	}
	
	@Test
	public void verificaTXTOrigem() {
		File file = new File(PASTAORIGEM);
		File[] arquivos = file.listFiles();
		assertNotNull(arquivos);
		if (arquivos!=null) {
			for (File fileTmp : arquivos) {
				String nome = fileTmp.getName();
				String ext = null;
				if(nome!=null)ext = nome.substring(nome.length()-3, nome.length());
				assertEquals(EXTENSAO, ext.toUpperCase());
			}
		}
	}

}
