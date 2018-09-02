package br.com.servidor.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import br.com.servidor.dao.DispositivoDAO;
import br.com.servidor.domain.Dispositivo;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class DispositivoBean implements Serializable {

	private Dispositivo dispositivo;

	private List<Dispositivo> dispositivos;

	public Dispositivo getDispositivo() {
		return dispositivo;
	}

	public void setDispositivo(Dispositivo dispositivo) {
		this.dispositivo = dispositivo;
	}

	public List<Dispositivo> getDispositivos() {
		return dispositivos;
	}

	public void setDispositivos(List<Dispositivo> dispositivos) {
		this.dispositivos = dispositivos;
	}

	@PostConstruct
	public void listar() {

		try {
			DispositivoDAO dispositivoDAO = new DispositivoDAO();
			dispositivos = dispositivoDAO.listar();
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar os dispositivos");
			erro.printStackTrace();
		}

	}

	public void novo() {
		dispositivo = new Dispositivo();
	}

	public void salvar() {
		try {
			DispositivoDAO dispositivoDAO = new DispositivoDAO();
			dispositivoDAO.merge(dispositivo);

			// atualizando a listagem após salvar
			dispositivo = new Dispositivo();
			dispositivos = dispositivoDAO.listar();

			novo();

			Messages.addGlobalInfo("Dispositivo salvo com sucesso !");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar o dispositivo");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {

		try {
			dispositivo = (Dispositivo) evento.getComponent().getAttributes().get("dispositivoSelecionado");

			DispositivoDAO dispositivoDAO = new DispositivoDAO();
			dispositivoDAO.excluir(dispositivo);

			// atualizando a lista de dispositivos após a exclusão
			dispositivos = dispositivoDAO.listar();

			Messages.addGlobalInfo(
					"Identificação: " + dispositivo.getDis_identificacao() + " excluído.");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar remover o dispositivo");
			erro.printStackTrace();
		}

	}

	public void editar(ActionEvent evento) {
		dispositivo = (Dispositivo) evento.getComponent().getAttributes().get("dispositivoSelecionado");

	}

}
