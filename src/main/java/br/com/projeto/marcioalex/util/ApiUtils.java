package br.com.projeto.marcioalex.util;

import br.com.projeto.marcioalex.dto.ArquivoCorteDTO;
import br.com.projeto.marcioalex.dto.ArquivoDTO;
import br.com.projeto.marcioalex.modelo.Arquivo;

public class ApiUtils {
	
	/**
	 * retira caracteres especiais, pontos e espaços em branco antes, depois e no meio da string
	 * @param aliasBruto
	 * @return
	 */
	private static String retirarCaracteresEsp(String aliasBruto) {
		//TODO - Otimizar expressão regular
		String aliasLapidado = aliasBruto
				.replaceAll("/(?!\\w|\\s)./g","") //remove o que não e palavra ou espaco em branco
				.replaceAll("/\\s+/g", " ") // acha um ou mais espaços e transforma em um só espaço
				.replaceAll("/^(\\s*)([\\W\\w]*)(\\b\\s*$)/g", "") //remove espaços antes e depois tambem
				.replaceAll("[/^$.|?*+()]", ""); //remove os caracteres especiais restantes que podem ser gerados pelo bcript no momento do encode
		return aliasLapidado;
	}
	
	public static ArquivoDTO converterArquivoEmDTO(Arquivo arquivoCadastrado) {
		ArquivoDTO arquivoDto = new ArquivoDTO();
		arquivoDto.setTitle(arquivoCadastrado.getTitle());
		arquivoDto.setDuration(arquivoCadastrado.getDuration());
		arquivoDto.setStartTime(arquivoCadastrado.getStartTime());
		arquivoDto.setEndTime(arquivoCadastrado.getEndTime());
		arquivoDto.setReconcileKey(arquivoCadastrado.getReconcileKey());
		arquivoDto.setName(arquivoCadastrado.getName());
		arquivoDto.setId(arquivoCadastrado.getId());
		arquivoDto.setStatusJobCorte(arquivoCadastrado.getStatusJobCorte());
		return arquivoDto;
	}
	
	public static ArquivoCorteDTO converterArquivoEmCorteDTO(Arquivo arquivoCadastrado, String path) {
		ArquivoCorteDTO arquivoDto = new ArquivoCorteDTO();
		arquivoDto.setStartTime(arquivoCadastrado.getStartTime());
		arquivoDto.setEndTime(arquivoCadastrado.getEndTime());
		arquivoDto.setName(arquivoCadastrado.getName());
		arquivoDto.setPath(path);
		return arquivoDto;
	}


	

	
}

