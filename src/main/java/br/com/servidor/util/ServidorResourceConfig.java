package br.com.servidor.util;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;


@ApplicationPath("rest")
public class ServidorResourceConfig extends ResourceConfig{

	public ServidorResourceConfig(){
		packages("br.com.servidor.service");
	}	
	
	
}
