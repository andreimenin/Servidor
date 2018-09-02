package br.com.servidor.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.omnifaces.util.Messages;

import br.com.servidor.dao.LinhaDAO;
import br.com.servidor.dao.DispositivoDAO;
import br.com.servidor.dao.Linha_DispositivoDAO;
import br.com.servidor.domain.Linha;
import br.com.servidor.domain.Dispositivo;
import br.com.servidor.domain.Linha_Dispositivo;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class Linha_DispositivoBean implements Serializable {

	private Linha_Dispositivo linha_dispositivo;
	private List<Linha_Dispositivo> linha_dispositivos;
	private List<Dispositivo> dispositivos;
	private List<Linha> linhas;

	public List<Linha_Dispositivo> getLinha_Dispositivos() {
		return linha_dispositivos;
	}

	public void setLinha_Dispositivos(List<Linha_Dispositivo> linha_dispositivos) {
		this.linha_dispositivos = linha_dispositivos;
	}

	public List<Dispositivo> getDispositivos() {
		return dispositivos;
	}

	public void setDispositivos(List<Dispositivo> dispositivos) {
		this.dispositivos = dispositivos;
	}

	public Linha_Dispositivo getLinha_Dispositivo() {
		return linha_dispositivo;
	}

	public void setLinha_Dispositivo(Linha_Dispositivo linha_dispositivo) {
		this.linha_dispositivo = linha_dispositivo;
	}
	
	public List<Linha> getLinhas() {
		return linhas;
	}	
	
	public void setLinhas(List<Linha> linhas) {
		this.linhas = linhas;
	}

	@PostConstruct
	public void listar() {

		Linha_DispositivoDAO linha_dispositivoDAO = new Linha_DispositivoDAO();
		linha_dispositivos = linha_dispositivoDAO.listar();

		try {

		} catch (RuntimeException erro) {
			Messages.addGlobalError("Erro ao tentar listar as linha_dispositivos");
			erro.printStackTrace();
		}

	}

	public void novo() {

		try {
			linha_dispositivo = new Linha_Dispositivo();

			DispositivoDAO dispositivoDAO = new DispositivoDAO();
			LinhaDAO linhaDAO = new LinhaDAO();
			dispositivos = dispositivoDAO.listar(); 
			linhas = linhaDAO.listar();
			
		} catch (RuntimeException erro) {

			Messages.addGlobalError("Erro ao carregar a linha_dispositivo");
			erro.printStackTrace();
		}
	}

	public void salvar() {

		try {
			Linha_DispositivoDAO linha_dispositivoDAO = new Linha_DispositivoDAO();
			linha_dispositivoDAO.merge(linha_dispositivo);

			// atualizando os dados das tabelas no view
			linha_dispositivo = new Linha_Dispositivo();

			DispositivoDAO dispositivoDAO = new DispositivoDAO();
			LinhaDAO linhaDAO = new LinhaDAO();
			dispositivos = dispositivoDAO.listar(); 
			linhas = linhaDAO.listar();
			linha_dispositivos = linha_dispositivoDAO.listar(); 			

			Messages.addGlobalInfo("Linha_Dispositivo salvo com sucesso");
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar uma nova linha_dispositivo");
			erro.printStackTrace();
		}
	}

	public void excluir(ActionEvent evento) {
		try{
			linha_dispositivo = (Linha_Dispositivo) evento.getComponent().getAttributes().get("linha_dispositivoSelecionado");
			
			Linha_DispositivoDAO linha_dispositivoDAO = new Linha_DispositivoDAO();
			linha_dispositivoDAO.excluir(linha_dispositivo);
			
			//atualizando a lista de dispositivos após a exclusão
			linha_dispositivos = linha_dispositivoDAO.listar(); 
			
			Messages.addGlobalInfo("Linha_Dispositivo removida com sucesso.");
			}
			catch (RuntimeException erro) {
				Messages.addGlobalError("Ocorreu um erro ao tentar remover a linha_dispositivo");
				erro.printStackTrace();
			}
	}
	

	public void editar(ActionEvent evento) {
		try {
			linha_dispositivo = (Linha_Dispositivo) evento.getComponent().getAttributes().get("linha_dispositivoSelecionado");

			DispositivoDAO dispositivoDAO = new DispositivoDAO();
			LinhaDAO linhaDAO = new LinhaDAO();
			dispositivos = dispositivoDAO.listar(); 
			linhas = linhaDAO.listar();
			
		} catch (RuntimeException erro) {

			Messages.addGlobalError("Ocorreu um erro ao tentar carregar uma linha_dispositivo");
			erro.printStackTrace();
		}

	}

	
	
}
