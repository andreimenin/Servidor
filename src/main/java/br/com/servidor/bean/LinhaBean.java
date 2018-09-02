package br.com.servidor.bean;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;

import org.omnifaces.util.Messages;
import br.com.servidor.dao.LinhaDAO;
import br.com.servidor.domain.Linha;


@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class LinhaBean implements Serializable{

	private Linha linha;
	
	private List<Linha> linhas;
	
	
	
	public Linha getLinha() {
		return linha;
	}
	
	public void setLinha(Linha linha) {
		this.linha = linha;
	}
	
	
	public List<Linha> getLinhas() {
		return linhas;
	}
	public void setLinhas(List<Linha> linhas) {
		this.linhas = linhas;
	}
	
	@PostConstruct
	public void listar(){
		
		try{
			LinhaDAO linhaDAO = new LinhaDAO();
			linhas = linhaDAO.listar(); 
		}
		catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar listar as linhas");
			erro.printStackTrace();
		}	
		
	}
	
	
	public void novo(){		
		 linha = new Linha();		
	}
	
		
	public void salvar(){	
		try{
		LinhaDAO linhaDAO = new LinhaDAO();	
		linhaDAO.merge(linha);
		
		//atualizando a listagem após salvar		
		linhas = linhaDAO.listar(); 
		
		Messages.addGlobalInfo("Linha salva com sucesso !");
		novo();		

			
		}
		catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar salvar a linha");
			erro.printStackTrace();
		}
	}
	
	
	public void excluir(ActionEvent evento){		
		
		try{
		linha = (Linha) evento.getComponent().getAttributes().get("linhaSelecionado");
		
		LinhaDAO linhaDAO = new LinhaDAO();
		linhaDAO.excluir(linha);
		
		//atualizando a lista de linhas após a exclusão
		linhas = linhaDAO.listar(); 
		
		Messages.addGlobalInfo("Nome: " + linha.getLin_nome());
		}
		catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar remover a linha");
			erro.printStackTrace();
		}
		
	}
	
	
	
	public void editar(ActionEvent evento){
		linha = (Linha) evento.getComponent().getAttributes().get("linhaSelecionado");
		
		
	}
	
}
