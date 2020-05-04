package br.com.projeto.marcioalex.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import br.com.projeto.marcioalex.modelo.Arquivo;

public class FileUtils {

	public static void moverArquivo(String nomeArquivo, String pastaOrigem, String pastaDestino) {
	    InputStream in;
	    OutputStream out;
	    try{
	        File entrada = new File(pastaOrigem+"//"+nomeArquivo);
	        File saida = new File(pastaDestino+"//"+nomeArquivo);
	        if(!saida.exists()){
	            if(!saida.getParentFile().exists()){
	                saida.getParentFile().mkdir();
	            }
	            saida.createNewFile();
	        }
	        in = new FileInputStream(entrada);
	        out = new FileOutputStream(saida);
	        byte[] buffer = new byte[1024];
	        int length;
	        while((length = in.read(buffer)) > 0 ){
	            out.write(buffer, 0 , length);
	        }
	        in.close();
	        out.close();
	        entrada.delete();
	    }catch(IOException e){
	        e.printStackTrace();
	    }
	}
	
	public static List<Arquivo> lerArquivo(String nomeArquivo, String pastaOrigem, String TYPE) {
		List<Arquivo> arquivos = new ArrayList<Arquivo>();
	    try{
	        FileReader arq = new FileReader(pastaOrigem+"//"+nomeArquivo);
	        BufferedReader lerArq = new BufferedReader(arq);
	        String linha = lerArq.readLine();
	        while (linha != null) {
	        	if(linha.length() > 3 && linha.substring(0, 3).indexOf(TYPE) > -1 && TimeUtils.maiorTrintaSegundos(TimeUtils.statTime(linha), TimeUtils.endTime(linha))){
	        		Arquivo arquivo = new Arquivo();
	        		arquivo.setDuration(TimeUtils.tempoTotal(TimeUtils.statTime(linha), TimeUtils.endTime(linha)));
	        		arquivo.setStartTime(TimeUtils.localTimeToDate(TimeUtils.statTime(linha)));
	        		arquivo.setEndTime(TimeUtils.localTimeToDate(TimeUtils.endTime(linha)));
	        		arquivo.setTitle(linha.substring(106, 138));
	        		arquivo.setReconcileKey(linha.substring(279, 311));
	        		arquivo.setName(linha.substring(106, 138).replace(" ", "_")+".mp4");
	        		//TODO - Criar enum com STATUS (LIDO, ENVIALDO PARA CORTE, EM CORTE, CORTE PROCESSADO, FINALIZADO)
	        		arquivos.add(arquivo);
	        	}
	          linha = lerArq.readLine();
	        }
	        arq.close();
	    }catch(IOException e){
	        e.printStackTrace();
	    }
	    return arquivos;
	}
	
	/**
	 * Arquivo de video mock
	 * @param nomeArquivo
	 * @param pastaDestino
	 */
	public static void gravarArquivo(String nomeArquivo, String pastaDestino) {
	    OutputStream out;
	    try{
	        File saida = new File(pastaDestino+"//"+nomeArquivo);
	        if(!saida.exists()){
	            if(!saida.getParentFile().exists()){
	                saida.getParentFile().mkdir();
	            }
	            saida.createNewFile();
	        }
	        
	        out = new FileOutputStream(saida);
	        byte[] buffer = new byte[1024];
	        int length = 0;
            out.write(buffer, 0 , length);
	        out.close();
	    }catch(IOException e){
	        e.printStackTrace();
	    }
	}
}
