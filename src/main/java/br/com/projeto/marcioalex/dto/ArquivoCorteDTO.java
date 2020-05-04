package br.com.projeto.marcioalex.dto;

import java.util.Date;

public class ArquivoCorteDTO {
	private Date startTime;
	private Date endTime;
	private String name;
	private String path;

	public ArquivoCorteDTO() {}
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArquivoCorteDTO(Date startTime, Date endTime, String name, String path) {
		super();
		this.startTime = startTime;
		this.endTime = endTime;
		this.name = name;
		this.path = path;
	}
	
	
}
