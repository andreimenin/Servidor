package br.com.servidor.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@SuppressWarnings("serial")
@Entity
public class Linha_Dispositivo extends GenericDomain{
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Linha linha;
	
	@ManyToOne
	@JoinColumn(nullable = false)
	private Dispositivo dispositivo;
		
	@Column(nullable = true)	
	private double liDis_tempoFinal;
	
	@Column(nullable = false)
	private Boolean liDis_ativo;

	public Linha getLinha() {
		return linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}

	public Dispositivo getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(Dispositivo dispositivo) {
		this.dispositivo = dispositivo;
	}

	public Boolean getLiDis_ativo() {
		return liDis_ativo;
	}

	public void setLiDis_ativo(Boolean liDis_ativo) {
		this.liDis_ativo = liDis_ativo;
	}		
	
	public double getLiDis_tempoFinal() {
		return liDis_tempoFinal;
	}
	
	public void setLiDis_tempoFinal(double liDis_tempoFinal) {
		this.liDis_tempoFinal = liDis_tempoFinal;
	}
		
}
