package br.com.servidor.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class Ponto extends GenericDomain{	
	
	
	@Column(length = 3, nullable = false)
	private String pon_numero;	
	
	@Column(length = 15, nullable = false)
	private double pon_latitude;
	
	@Column(length = 15, nullable = false)
	double pon_longitude;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Ponto proximoPonto;
	
	@Column(length = 50, nullable = true)
	private String pon_referencia;
	
	
	public String getPon_numero() {
		return pon_numero;
	}

	public void setPon_numero(String pon_numero) {
		this.pon_numero = pon_numero;
	}

	public double getPon_latitude() {
		return pon_latitude;
	}

	public void setPon_latitude(double pon_latitude) {
		this.pon_latitude = pon_latitude;
	}

	public double getPon_longitude() {
		return pon_longitude;
	}

	public void setPon_longitude(double pon_longitude) {
		this.pon_longitude = pon_longitude;
	}

	public Ponto getProximoPonto() {
		return proximoPonto;
	}
	
	public void setProximoPonto(Ponto proximoPonto) {
		this.proximoPonto = proximoPonto;
	}

	
	public String getPon_referencia() {
		return pon_referencia;
	}
	
	public void setPon_referencia(String pon_referencia) {
		this.pon_referencia = pon_referencia;
	}
	
	
	
	
	
	
	
}
