package br.com.servidor.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.omnifaces.util.Messages;

import br.com.servidor.dao.LinhaDAO;
import br.com.servidor.dao.PontoDAO;
import br.com.servidor.dao.Ponto_LinhaDAO;
import br.com.servidor.domain.Linha;
import br.com.servidor.domain.Ponto;
import br.com.servidor.domain.Ponto_Linha;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class Ponto_LinhaBean implements Serializable {

	private Ponto_Linha ponto_linha;

	private List<Ponto_Linha> ponto_linhas;
	private List<Ponto> pontos;
	private List<Linha> linhas;

	public List<Ponto_Linha> getPonto_Linhas() {
		return ponto_linhas;
	}

	public void setPonto_Linhas(List<Ponto_Linha> ponto_linhas) {
		this.ponto_linhas = ponto_linhas;
	}

	public List<Ponto> getPontos() {
		return pontos;
	}

	public void setPontos(List<Ponto> pontos) {
		this.pontos = pontos;
	}

	public Ponto_Linha getPonto_Linha() {
		return ponto_linha;
	}

	public void setPonto_Linha(Ponto_Linha ponto_linha) {
		this.ponto_linha = ponto_linha;
	}

	public List<Linha> getLinhas() {
		return linhas;
	}

	public void setLinhas(List<Linha> linhas) {
		this.linhas = linhas;
	}

	

	@PostConstruct
	public void listar() {

		Ponto_LinhaDAO ponto_linhaDAO = new Ponto_LinhaDAO();
		ponto_linhas = ponto_linhaDAO.listar();

		try {

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar os ponto_linhas");
			erro.printStackTrace();
		}

	}

	public void novo() {

		try {
			ponto_linha = new Ponto_Linha();

			Ponto_LinhaDAO ponto_LinhaDAO = new Ponto_LinhaDAO();
			LinhaDAO linhaDAO = new LinhaDAO();
			PontoDAO pontoDAO = new PontoDAO();
			ponto_linhas = ponto_LinhaDAO.listar();
			linhas = linhaDAO.listar();
			pontos = pontoDAO.listar("pon_numero");
			
			
		} catch (RuntimeException erro) {

			Messages.addGlobalError("Erro ao carregar o ponto_linha");
			erro.printStackTrace();
		}
	}

	public void salvar() {

		try {
			Ponto_LinhaDAO ponto_linhaDAO = new Ponto_LinhaDAO();
			ponto_linhaDAO.merge(ponto_linha);

			// atualizando os dados das tabelas no view
			ponto_linha = new Ponto_Linha();

			PontoDAO pontoDAO = new PontoDAO();
			LinhaDAO linhaDAO = new LinhaDAO();
			pontos = pontoDAO.listar();
			linhas = linhaDAO.listar();
			ponto_linhas = ponto_linhaDAO.listar();

			Messages.addGlobalInfo("Ponto_Linha salvo com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar um nova ponto_linha");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try {
			ponto_linha = (Ponto_Linha) evento.getComponent().getAttributes().get("ponto_linhaSelecionado");

			Ponto_LinhaDAO ponto_linhaDAO = new Ponto_LinhaDAO();
			ponto_linhaDAO.excluir(ponto_linha);

			// atualizando a lista de pontos após a exclusão
			ponto_linhas = ponto_linhaDAO.listar();

			Messages.addGlobalInfo("Ponto_Linha removido com sucesso.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar remover o ponto_linha");
			erro.printStackTrace();
		}
	}

	public void editar(ActionEvent evento) {
		try {
			ponto_linha = (Ponto_Linha) evento.getComponent().getAttributes().get("ponto_linhaSelecionado");

			PontoDAO pontoDAO = new PontoDAO();
			LinhaDAO linhaDAO = new LinhaDAO();
			pontos = pontoDAO.listar();
			linhas = linhaDAO.listar();

		} catch (RuntimeException erro) {

			Messages.addGlobalError("Ocorreu um erro ao tentar carregar um ponto_linha");
			erro.printStackTrace();
		}

	}

}
