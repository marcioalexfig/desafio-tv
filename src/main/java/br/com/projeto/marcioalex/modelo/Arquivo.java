package br.com.projeto.marcioalex.modelo;

import java.util.Date;

import org.bson.types.ObjectId;

public class Arquivo {
	
	private ObjectId id;
	
	private String title;
	
	private Integer duration;
	
	private Date startTime;
	
	private Date endTime;
	
	private String reconcileKey;
	
	private String name;
	
	private String IdJobCorte;
	
	private String StatusJobCorte;
	
	
	public String getIdJobCorte() {
		return IdJobCorte;
	}

	public void setIdJobCorte(String idJobCorte) {
		IdJobCorte = idJobCorte;
	}

	public String getStatusJobCorte() {
		return StatusJobCorte;
	}

	public void setStatusJobCorte(String statusJobCorte) {
		StatusJobCorte = statusJobCorte;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getDuration() {
		return duration;
	}

	public void setDuration(Integer duration) {
		this.duration = duration;
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

	public String getReconcileKey() {
		return reconcileKey;
	}

	public void setReconcileKey(String reconcileKey) {
		this.reconcileKey = reconcileKey;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Arquivo criarId() {
		setId(new ObjectId());
		return this;
	}
	
}
