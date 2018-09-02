package br.com.servidor.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;

import br.com.servidor.dao.LinhaDAO;
import br.com.servidor.domain.Linha;


//http://127.0.0.1:8080/Servidor/rest/linha
@Path("linha")
public class LinhaService {

	@GET
	public String listar(){		
		LinhaDAO linhaDAO = new LinhaDAO();		
		List<Linha> linhas = linhaDAO.listar();		
		Gson gson = new Gson();		
		String json = gson.toJson(linhas);
		
		return json;
	}
	
	
	//http://127.0.0.1:8080/Servidor/rest/linha/10
	@Path("{codigo}")
	@GET	
	public String buscarPorCodigo(@PathParam("codigo") Long codigo){
		
		LinhaDAO linhaDAO = new LinhaDAO();
		Linha linha = linhaDAO.buscar(codigo);
		
		Gson gson = new Gson();
		String json = gson.toJson(linha);
		
		return json;
	}
		
	//http://127.0.0.1:8080/Servidor/rest/linha
	@POST
	public String salvar(String json){
		
		Gson gson = new Gson();
		Linha linha = gson.fromJson(json, Linha.class);
				
		LinhaDAO linhaDAO = new LinhaDAO();
		linhaDAO.merge(linha);
		
		//imprimindo no navegador as informa√ß√µes salvas 
		String jsonSaida = gson.toJson(linha);
		return jsonSaida;			
	}
	
	
	
	//M…TODO USADO SOMENTE PARA TESTAR NO Advanced REST Client, para us·-lo È necess·rio criar outro form no xhtml
	//e mudar o mÈtodo editar do LinhaBean
	//http://127.0.0.1:8080/Servidor/rest/linha
//	@PUT
//	public String editar(String json){
//		
//		Gson gson = new Gson();
//		Linha linha = gson.fromJson(json, Linha.class);
//				
//		LinhaDAO linhaDAO = new LinhaDAO();
//		linhaDAO.editar(linha);
//		
//		//imprimindo no navegador as informa√ß√µes salvas 
//		String jsonSaida = gson.toJson(linha);
//		return jsonSaida;			
//	}
	
	
	//http://127.0.0.1:8080/Servidor/rest/linha
	@DELETE
	public String excluir(String json){
		
		Gson gson = new Gson();
		Linha linha = gson.fromJson(json, Linha.class);
		
		
		LinhaDAO linhaDAO = new LinhaDAO();
		linha = linhaDAO.buscar(linha.getCodigo());		
		
		linhaDAO.excluir(linha);
		
		String jsonSaida = gson.toJson(linha);
		
		return jsonSaida;	
		
	}
	
	
}
