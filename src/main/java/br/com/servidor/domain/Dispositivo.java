package br.com.servidor.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class Dispositivo extends GenericDomain{

	@Column(length = 30, nullable = false)
	private String dis_identificacao;
	
	@Column(length = 20, nullable = true)
	private double dis_latitude;

	@Column(length = 20, nullable = true)
	private double dis_longitude;

	public String getDis_identificacao() {
		return dis_identificacao;
	}

	public void setDis_identificacao(String dis_identificacao) {
		this.dis_identificacao = dis_identificacao;
	}

	public double getDis_latitude() {
		return dis_latitude;
	}

	public void setDis_latitude(double dis_latitude) {
		this.dis_latitude = dis_latitude;
	}

	public double getDis_longitude() {
		return dis_longitude;
	}

	public void setDis_longitude(double dis_longitude) {
		this.dis_longitude = dis_longitude;
	}
	
	
	
	
	
	
	
}