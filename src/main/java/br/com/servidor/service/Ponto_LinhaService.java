package br.com.servidor.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;

import br.com.servidor.dao.Ponto_LinhaDAO;
import br.com.servidor.domain.Ponto_Linha;



//http://127.0.0.1:8080/Servidor/rest/ponto_linha
@Path("ponto_linha")
public class Ponto_LinhaService {

	@GET
	public String listar(){		
		Ponto_LinhaDAO ponto_linhaDAO = new Ponto_LinhaDAO();		
		List<Ponto_Linha> ponto_linhas = ponto_linhaDAO.listar();		
		Gson gson = new Gson();		
		String json = gson.toJson(ponto_linhas);
		
		return json;
	}
	
	
	//http://127.0.0.1:8080/Drogaria/rest/ponto_linha/10
	@Path("{codigo}")
	@GET	
	public String buscarPorCodigo(@PathParam("codigo") Long codigo){
		
		Ponto_LinhaDAO ponto_linhaDAO = new Ponto_LinhaDAO();
		Ponto_Linha ponto_linha = ponto_linhaDAO.buscar(codigo);
		
		Gson gson = new Gson();
		String json = gson.toJson(ponto_linha);
		
		return json;
	}
		
	//http://127.0.0.1:8080/Drogaria/rest/ponto_linha
	@POST
	public String salvar(String json){
		
		Gson gson = new Gson();
		Ponto_Linha ponto_linha = gson.fromJson(json, Ponto_Linha.class);
				
		Ponto_LinhaDAO ponto_linhaDAO = new Ponto_LinhaDAO();
		ponto_linhaDAO.merge(ponto_linha);
		
		//imprimindo no navegador as informações salvas 
		String jsonSaida = gson.toJson(ponto_linha);
		return jsonSaida;			
	}
	
	
	//MÉTODO USADO SOMENTE PARA TESTAR NO Advanced REST Client, para usá-lo é necessário criar outro form no xhtml
	//e mudar o método editar do Ponto_LinhaBean
	//http://127.0.0.1:8080/Drogaria/rest/ponto_linha
//	@PUT
//	public String editar(String json){
//		
//		Gson gson = new Gson();
//		Ponto_Linha ponto_linha = gson.fromJson(json, Ponto_Linha.class);
//				
//		Ponto_LinhaDAO ponto_linhaDAO = new Ponto_LinhaDAO();
//		ponto_linhaDAO.editar(ponto_linha);
//		
//		//imprimindo no navegador as informações salvas 
//		String jsonSaida = gson.toJson(ponto_linha);
//		return jsonSaida;			
//	}
	
	
	//http://127.0.0.1:8080/Drogaria/rest/ponto_linha
	@DELETE
	public String excluir(String json){
		
		Gson gson = new Gson();
		Ponto_Linha ponto_linha = gson.fromJson(json, Ponto_Linha.class);
		
		
		Ponto_LinhaDAO ponto_linhaDAO = new Ponto_LinhaDAO();
		ponto_linha = ponto_linhaDAO.buscar(ponto_linha.getCodigo());		
		
		ponto_linhaDAO.excluir(ponto_linha);
		
		String jsonSaida = gson.toJson(ponto_linha);
		
		return jsonSaida;	
		
	}
	
	
}
