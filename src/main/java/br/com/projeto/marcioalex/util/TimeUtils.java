package br.com.projeto.marcioalex.util;

import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;

import org.apache.commons.lang3.time.DurationFormatUtils;

public class TimeUtils {

	public static Integer tempoTotal (LocalTime tempoInicial, LocalTime tempoFinal) {
		Duration duracao = Duration.between(tempoInicial, tempoFinal);
		String duracaoFormatada = DurationFormatUtils.formatDuration(duracao.toMillis(), "ss.SS"); 
		return Integer.valueOf(duracaoFormatada.substring(0, duracaoFormatada.indexOf('.')));
	}
	public static boolean maiorTrintaSegundos (LocalTime tempoInicial, LocalTime tempoFinal) {
		return Duration.between(tempoInicial, tempoFinal).getSeconds() > 29.0;
	}
	
	public static Date localTimeToDate(LocalTime local) {
		Instant instant = local.atDate(LocalDate.now()).
		        atZone(ZoneId.systemDefault()).toInstant();
		Date time = Date.from(instant);
		return time;
	}
	
	public static LocalTime statTime(String linha) {
		return LocalTime.parse(linha.substring(17, 25));
	}
	
	public static LocalTime endTime(String linha) {
		return LocalTime.parse(linha.substring(40, 48));
	}
	
}
