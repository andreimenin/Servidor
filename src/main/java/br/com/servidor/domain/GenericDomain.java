package br.com.servidor.domain;

import java.io.Serializable;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

//Classe de apoio para gerar a chave primária de outras tabelas

@SuppressWarnings("serial")
@MappedSuperclass // anotação para dizer que essa classe não corresponde à uma
					// tabela, mas que corresponde à outras classes para gerar
					// tabelas.
public class GenericDomain implements Serializable {

	@Id // serve para dizer para o código que ele é uma classe primária
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long codigo;

	public Long getCodigo() {
		return codigo;
	}

	public void setCodigo(Long codigo) {
		this.codigo = codigo;
	}

	// CONVERTER GENÉRICO PARA IDENTIFICAR OS OBJETOS NA CAIXA DE SELEÇÃO
	// (COMBOBOX)
	@Override
	public String toString() {
		return String.format("%s[codigo=%d]", getClass().getSimpleName(), getCodigo());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codigo == null) ? 0 : codigo.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GenericDomain other = (GenericDomain) obj;
		if (codigo == null) {
			if (other.codigo != null)
				return false;
		} else if (!codigo.equals(other.codigo))
			return false;
		return true;
	}
	

}
