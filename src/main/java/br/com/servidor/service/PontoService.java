package br.com.servidor.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;

import javax.ws.rs.Path;
import javax.ws.rs.PathParam;



import com.google.gson.Gson;


import br.com.servidor.dao.Linha_DispositivoDAO;
import br.com.servidor.dao.PontoDAO;
import br.com.servidor.dao.Ponto_LinhaDAO;

import br.com.servidor.domain.Linha_Dispositivo;
import br.com.servidor.domain.Ponto;
import br.com.servidor.domain.Ponto_Linha;

//http://127.0.0.1:8080/Servidor/rest/ponto
@Path("ponto")
public class PontoService {
	@GET
	public String listar() {
		PontoDAO pontoDAO = new PontoDAO();
		List<Ponto> pontos = pontoDAO.listar();
		Gson gson = new Gson();
		String json = gson.toJson(pontos);

		return json;
	}

	// http://127.0.0.1:8080/Servidor/rest/ponto/10
	@Path("{codigo}")
	@GET
	public String buscarPorCodigo(@PathParam("codigo") Long codigo) {

		PontoDAO pontoDAO = new PontoDAO();
		Ponto ponto = pontoDAO.buscar(codigo);

		Gson gson = new Gson();
		String json = gson.toJson(ponto);

		return json;
	}

	// http://127.0.0.1:8080/Servidor/rest/ponto
	@POST
	public String salvar(String json) {

		Gson gson = new Gson();
		Ponto ponto = gson.fromJson(json, Ponto.class);

		PontoDAO pontoDAO = new PontoDAO();
		pontoDAO.merge(ponto);

		String jsonSaida = gson.toJson(ponto);
		return jsonSaida;
	}

	// MÉTODO USADO SOMENTE PARA TESTAR NO Advanced REST Client, para usá-lo é
	// necessário criar outro form no xhtml
	// e mudar o método editar do PontoBean
	// http://127.0.0.1:8080/Servidor/rest/ponto
	// @PUT
	// public String editar(String json){
	//
	// Gson gson = new Gson();
	// Ponto ponto = gson.fromJson(json, Ponto.class);
	//
	// PontoDAO pontoDAO = new PontoDAO();
	// pontoDAO.editar(ponto);
	//
	// //imprimindo no navegador as informaÃ§Ãµes salvas
	// String jsonSaida = gson.toJson(ponto);
	// return jsonSaida;
	// }

	// http://127.0.0.1:8080/Servidor/rest/ponto/10
	//http://192.168.1.142.:8080/Servidor/rest/ponto/2
	@DELETE
	public String excluir(String json) {

		Gson gson = new Gson();
		Ponto ponto = gson.fromJson(json, Ponto.class);

		PontoDAO pontoDAO = new PontoDAO();
		ponto = pontoDAO.buscar(ponto.getCodigo());

		pontoDAO.excluir(ponto);

		String jsonSaida = gson.toJson(ponto);

		return jsonSaida;

	}

	// http://127.0.0.1:8080/Servidor/rest/ponto/10
	//http://192.168.1.142.:8080/Servidor/rest/ponto/2
	@Path("buscanumero/{pon_numero}")
	@GET
	public String buscarPorNumero(@PathParam("pon_numero") String pon_numero) {

		PontoDAO pontoDAO = new PontoDAO();
		Ponto ponto = pontoDAO.buscarNumero(pon_numero);

		Gson gson = new Gson();
		String json = gson.toJson(ponto);

		return json;
	}
	
	
	
	//http://192.168.237.2:8080/Servidor/rest/ponto/buscatotal/pt1
	//http://192.168.1.142.:8080/Servidor/rest/ponto/buscatotal/pt1
	@Path("buscatotal/{pon_numero}")
	@GET
	public String buscaTotal(@PathParam("pon_numero") String pon_numero) {
	
		Linha_DispositivoDAO linha_DispositivoDAO = new Linha_DispositivoDAO();
		List<Linha_Dispositivo> linhas_Dispositivos = linha_DispositivoDAO.listar();
				
		PontoDAO pontoDAO = new PontoDAO();
		Ponto ponto = pontoDAO.buscarNumero(pon_numero);

		Ponto_LinhaDAO ponto_LinhaDAO = new Ponto_LinhaDAO();
		List<Ponto_Linha> ponto_Linhas = ponto_LinhaDAO.buscarPorPonto(ponto.getCodigo());

		//DispositivoDAO dispositivoDAO = new DispositivoDAO();
		//List<Dispositivo> dispositivos = dispositivoDAO.listar();

		ArrayList<Linha_Dispositivo> aList = new ArrayList<>();

		for (Ponto_Linha ponto_Linha : ponto_Linhas) {
			for (Linha_Dispositivo linha_Dispositivo : linhas_Dispositivos) {
				if (ponto_Linha.getLinha().getCodigo() == linha_Dispositivo.getLinha().getCodigo()) {
					aList.add(linha_Dispositivo);

				}
			}
		}
		linhas_Dispositivos = aList;
				
		
		System.out.println("LISTA DE LINHAS_DISPOSITIVOS: " + linhas_Dispositivos);
		System.out.println("ARRAY DE LINHAS_DISPOSITIVOS" + aList);
			
			
		Gson gson = new Gson();
		String json = gson.toJson(linhas_Dispositivos);
		return json;
	}
	
	
	
	
}


