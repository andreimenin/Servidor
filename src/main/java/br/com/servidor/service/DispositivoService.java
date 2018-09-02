package br.com.servidor.service;

import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;

import br.com.servidor.dao.DispositivoDAO;
import br.com.servidor.domain.Dispositivo;


//http://127.0.0.1:8080/Servidor/rest/dispositivo
@Path("dispositivo")
public class DispositivoService {

	@GET
	public String listar(){		
		DispositivoDAO dispositivoDAO = new DispositivoDAO();		
		List<Dispositivo> dispositivos = dispositivoDAO.listar();		
		Gson gson = new Gson();		
		String json = gson.toJson(dispositivos);
		
		return json;
	}
	
	
	//http://127.0.0.1:8080/Servidor/rest/dispositivo/10
	@Path("{codigo}")
	@GET	
	public String buscarPorCodigo(@PathParam("codigo") Long codigo){
		
		DispositivoDAO dispositivoDAO = new DispositivoDAO();
		Dispositivo dispositivo = dispositivoDAO.buscar(codigo);
		
		Gson gson = new Gson();
		String json = gson.toJson(dispositivo);
		
		return json;
	}
		
	//http://127.0.0.1:8080/Servidor/rest/dispositivo
	@POST
	public String salvar(String json){
		
		Gson gson = new Gson();
		Dispositivo dispositivo = gson.fromJson(json, Dispositivo.class);
				
		DispositivoDAO dispositivoDAO = new DispositivoDAO();
		dispositivoDAO.merge(dispositivo);
		
		//imprimindo no navegador as informa√ß√µes salvas 
		String jsonSaida = gson.toJson(dispositivo);
		return jsonSaida;			
	}
	
	
	
	//M…TODO USADO SOMENTE PARA TESTAR NO Advanced REST Client, para us·-lo È necess·rio criar outro form no xhtml
	//e mudar o mÈtodo editar do DispositivoBean
	//http://127.0.0.1:8080/Servidor/rest/dispositivo
//	@PUT
//	public String editar(String json){
//		
//		Gson gson = new Gson();
//		Dispositivo dispositivo = gson.fromJson(json, Dispositivo.class);
//				
//		DispositivoDAO dispositivoDAO = new DispositivoDAO();
//		dispositivoDAO.editar(dispositivo);
//		
//		//imprimindo no navegador as informa√ß√µes salvas 
//		String jsonSaida = gson.toJson(dispositivo);
//		return jsonSaida;			
//	}
	
	
	//http://127.0.0.1:8080/Servidor/rest/dispositivo
	@DELETE
	public String excluir(String json){
		
		Gson gson = new Gson();
		Dispositivo dispositivo = gson.fromJson(json, Dispositivo.class);
		
		
		DispositivoDAO dispositivoDAO = new DispositivoDAO();
		dispositivo = dispositivoDAO.buscar(dispositivo.getCodigo());		
		
		dispositivoDAO.excluir(dispositivo);
		
		String jsonSaida = gson.toJson(dispositivo);
		
		return jsonSaida;	
		
	}
	
	
}
