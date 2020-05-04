package br.com.projeto.marcioalex.dto;

import java.util.Date;

import org.bson.types.ObjectId;
import org.hibernate.validator.constraints.NotEmpty;

public class ArquivoDTO {
	private ObjectId id;
	private String title;
	private String name;
	private Integer duration;
	private Date startTime;
	private Date endTime;
	private String reconcileKey;
	private String idJobCorte;
	private String statusJobCorte;

	public ArquivoDTO() {}
	
	public String getIdJobCorte() {
		return idJobCorte;
	}

	public void setIdJobCorte(String idJobCorte) {
		this.idJobCorte = idJobCorte;
	}

	public String getStatusJobCorte() {
		return statusJobCorte;
	}

	public void setStatusJobCorte(String statusJobCorte) {
		this.statusJobCorte = statusJobCorte;
	}

	@NotEmpty (message = "Title necessita ser fornecido!")
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
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

	public ArquivoDTO(String reconcileKey, String title, Date startTime, Date endTime, Integer duration, String name, String idJobCorte, String statusJobCorte) {
		super();
		this.reconcileKey = reconcileKey;
		this.title = title;
		this.startTime = startTime;
		this.endTime = endTime;
		this.duration = duration;
		this.name = name;
		this.idJobCorte = idJobCorte;
		this.statusJobCorte = statusJobCorte;
	}
	
	
}
