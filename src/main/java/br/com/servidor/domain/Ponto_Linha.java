package br.com.servidor.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@SuppressWarnings("serial")
@Entity
public class Ponto_Linha extends GenericDomain{

	@ManyToOne
	@JoinColumn(nullable = false)
	private Ponto ponto;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Linha linha;

	@Column(nullable = false)
	private float ponLi_tempo;
	
	@ManyToOne
	@JoinColumn(nullable = true)
	private Ponto_Linha proximoPonto;

	public Ponto getPonto() {
		return ponto;
	}

	public void setPonto(Ponto ponto) {
		this.ponto = ponto;
	}

	public Linha getLinha() {
		return linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}

	public float getPonLi_tempo() {
		return ponLi_tempo;
	}

	public void setPonLi_tempo(float ponLi_tempo) {
		this.ponLi_tempo = ponLi_tempo;
	}
	
	public Ponto_Linha getProximoPonto() {
		return proximoPonto;
	}
	
	public void setProximoPonto(Ponto_Linha proximoPonto) {
		this.proximoPonto = proximoPonto;
	}
	
	
	
}
