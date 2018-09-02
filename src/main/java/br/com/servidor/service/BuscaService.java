package br.com.servidor.service;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import com.google.gson.Gson;

import br.com.servidor.dao.LinhaDAO;
import br.com.servidor.dao.Linha_DispositivoDAO;
import br.com.servidor.dao.PontoDAO;
import br.com.servidor.dao.Ponto_LinhaDAO;
import br.com.servidor.domain.Linha;
import br.com.servidor.domain.Linha_Dispositivo;
import br.com.servidor.domain.Ponto;
import br.com.servidor.domain.Ponto_Linha;

//http://127.0.0.1:8080/Servidor/rest/busca/busca_dispositivo_da_linha/1/1
@Path("busca")
public class BuscaService {

	@Path("busca_dispositivo_da_linha/{numeroPontoDigitado}/{codigoLinhaSelecionado}")
	@GET
	public String buscaDispositivosDaLinha(@PathParam("numeroPontoDigitado") String numeroPontoDigitado,
			@PathParam("codigoLinhaSelecionado") Long codigoLinhaSelecionado) {

		LinhaDAO linhaDAO = new LinhaDAO();
		Linha linhaSelecionado = linhaDAO.buscar(codigoLinhaSelecionado);

		Linha_DispositivoDAO liDispositivoDAO = new Linha_DispositivoDAO();
		List<Linha_Dispositivo> linha_Dispositivos = liDispositivoDAO.buscarPorLinha(linhaSelecionado.getCodigo());

		Ponto_LinhaDAO ponto_LinhaDAO = new Ponto_LinhaDAO();

		List<Ponto_Linha> ponto_Linhas = ponto_LinhaDAO.listar();

		Linha_DispositivoDAO linha_DispositivoDAO = new Linha_DispositivoDAO();
		linha_Dispositivos = linha_DispositivoDAO.listar();

		ArrayList<Linha_Dispositivo> aListLD = new ArrayList<>();
		ArrayList<Ponto_Linha> aListPL = new ArrayList<>();

		// Filtrando os Pontos da Linha selecionada :: PARA TESTE
		for (Ponto_Linha ponto_li : ponto_Linhas) {
			if (linhaSelecionado.getCodigo() == ponto_li.getLinha().getCodigo()) {
				System.out.println(ponto_li.getPonto().getPon_numero());
				aListPL.add(ponto_li);
			}

		}
		// Filtrando os Dispositivos que fazem parte da Linha selecionada
		for (Linha_Dispositivo linha_Dispositivo : linha_Dispositivos) {
			if ((linhaSelecionado.getCodigo() == linha_Dispositivo.getLinha().getCodigo())
					&& (linha_Dispositivo.getLiDis_ativo() == true)) {
				aListLD.add(linha_Dispositivo);
			}
		}

		PontoDAO pontoDAO = new PontoDAO();
		Ponto ponto = pontoDAO.buscarNumero(numeroPontoDigitado);

		// Pegando o ponto_linha que tem o código igual o ponto digitado
		Ponto_Linha ponto_LinhaDigitado = new Ponto_Linha();

		// Pegando o Ponto_Linha correspondente ao Ponto digitado entre os Ponto_Linha
		// filtrados
		for (Ponto_Linha ponto_Linha : ponto_Linhas) {
			if (ponto_Linha.getPonto().getCodigo() == ponto.getCodigo()) {
				ponto_LinhaDigitado = ponto_Linha;
			}
		}
		ponto_Linhas = aListPL;

		// Filtrando os Dispositivos ativos na Linha selecionada e
		// calculando o Ponto mais próximo para cada Dispositivo,
		// aplicando a fórmula de cálculo entre linhas retas
		Ponto_Linha ponto_LinhaMaisProxima = new Ponto_Linha();
		double r = 6371.0;
		for (Linha_Dispositivo linha_Dispositivo : linha_Dispositivos) {
			double menorValor = Integer.MAX_VALUE;
			for (Ponto_Linha ponto_Linha : ponto_Linhas) {

				double p1LA = linha_Dispositivo.getDispositivo().getDis_latitude();
				double p2LA = ponto_Linha.getPonto().getPon_latitude();

				double p1LO = linha_Dispositivo.getDispositivo().getDis_longitude();
				double p2LO = ponto_Linha.getPonto().getPon_longitude();

				double pi = Math.PI;

				p1LA = p1LA * pi / 180.0;
				p1LO = p1LO * pi / 180.0;
				p2LA = p2LA * pi / 180.0;
				p2LO = p2LO * pi / 180.0;

				double dLat = p2LA - p1LA;
				double dLong = p2LO - p1LO;

				double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
						+ Math.cos(p1LA) * Math.cos(p2LA) * Math.sin(dLong / 2) * Math.sin(dLong / 2);
				double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

				double resultado = (r * c * 1000); // resultado em metros.

				if (resultado < menorValor) {
					menorValor = resultado;
					ponto_LinhaMaisProxima = ponto_Linha;
				}
			}

			// Após identificar o Ponto mais próximo do Dispositivo, serão somados
			// os pontos que estão entre o Ponto mais próximo e o Ponto digitado pelo
			// usuário
			float somaTempo = 0;
			for (Ponto_Linha ponto_Linha : ponto_Linhas) {

				if (ponto_LinhaMaisProxima.getCodigo() != ponto_LinhaDigitado.getCodigo()) {
					
					somaTempo = somaTempo + ponto_LinhaMaisProxima.getPonLi_tempo();
					ponto_LinhaMaisProxima = ponto_LinhaMaisProxima.getProximoPonto();
				}

				else {
					linha_Dispositivo.setLiDis_tempoFinal(somaTempo);
					System.out.println(linha_Dispositivo.getLiDis_tempoFinal());
					break;
				}			
				
			}
		}

		// Historico historico = new Historico();
		// historico.setHis_dataHora(new Date());
		// historico.setLinha(linhaSelecionado);
		// historico.setPonto(ponto);
		
		linha_Dispositivos = aListLD;
		Gson gson = new Gson();
		String json = gson.toJson(linha_Dispositivos);
		return json;
	}
}
