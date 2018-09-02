package br.com.servidor.bean;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.omnifaces.util.Messages;
import br.com.servidor.dao.PontoDAO;
import br.com.servidor.domain.Ponto;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class PontoBean implements Serializable {

	private Ponto ponto;

	private List<Ponto> pontos;

	public Ponto getPonto() {
		return ponto;
	}

	public void setPonto(Ponto ponto) {
		this.ponto = ponto;
	}

	public List<Ponto> getPontos() {
		return pontos;
	}

	public void setPontos(List<Ponto> pontos) {
		this.pontos = pontos;
	}

	@PostConstruct
	public void listar() {

		try {
			PontoDAO pontoDAO = new PontoDAO();
			pontos = pontoDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar pontos");
			erro.printStackTrace();
		}

	}

	public void novo() {
		ponto = new Ponto();
	}

	public void salvar() {
		try {
			PontoDAO pontoDAO = new PontoDAO();
			pontoDAO.merge(ponto);

			// atualizando a listagem após salvar		    
			pontos = pontoDAO.listar();

			novo();

			Messages.addGlobalInfo("Ponto salvo com sucesso !");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o ponto");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			ponto = (Ponto) evento.getComponent().getAttributes().get("pontoSelecionado");

			PontoDAO pontoDAO = new PontoDAO();
			pontoDAO.excluir(ponto);

			// atualizando a lista de pontos após a exclusão
			pontos = pontoDAO.listar();
			Messages.addGlobalInfo("Nome: " + ponto.getPon_numero() + " Latitude: " + ponto.getPon_latitude()
					+ " Longitude: " + ponto.getPon_longitude());
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar remover o ponto");
			erro.printStackTrace();
		}

	}

	public void editar(ActionEvent evento) {
		ponto = (Ponto) evento.getComponent().getAttributes().get("pontoSelecionado");

	}

}
