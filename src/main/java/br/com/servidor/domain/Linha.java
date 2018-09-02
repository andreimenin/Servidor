package br.com.servidor.domain;

import javax.persistence.Column;
import javax.persistence.Entity;

@SuppressWarnings("serial")
@Entity
public class Linha extends GenericDomain{
		
	
	@Column(length = 35, nullable = false)
	private String lin_nome;
	
	

	public String getLin_nome() {
		return lin_nome;
	}

	public void setLin_nome(String lin_nome) {
		this.lin_nome = lin_nome;
	}	
	
	
}
