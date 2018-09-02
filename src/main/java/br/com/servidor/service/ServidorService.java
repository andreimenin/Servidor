package br.com.servidor.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

//http://localhost:8080/Servidor/rest/servidor
@Path("servidor")
public class ServidorService {

	@GET
	public String exibir(){
		
		return "Curso de Java";
		
	}
	
	
}
