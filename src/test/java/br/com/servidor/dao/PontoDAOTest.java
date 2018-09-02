package br.com.servidor.dao;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import com.google.gson.Gson;

import br.com.servidor.domain.Linha;
import br.com.servidor.domain.Linha_Dispositivo;
import br.com.servidor.domain.Ponto;
import br.com.servidor.domain.Ponto_Linha;

public class PontoDAOTest {

	@Ignore
	@Test
	public void salvar() {
		Ponto ponto = new Ponto();

		ponto.setPon_numero("12");

		PontoDAO pontoDAO = new PontoDAO();
		pontoDAO.salvar(ponto);
	}

	@Ignore
	@Test
	public void listar() {

		PontoDAO pontoDAO = new PontoDAO();
		List<Ponto> resultado = pontoDAO.listar();

		// imprimindo os resultados
		System.out.println("Total de registros encontrados: " + resultado.size());
		for (Ponto ponto : resultado) {

			System.out.println("Código: " + ponto.getCodigo() + " Número: " + ponto.getPon_numero());
		}
	}

	@Ignore
	@Test
	public void buscar() {

		Long codigo = 2L;

		PontoDAO pontoDAO = new PontoDAO();

		Ponto ponto = pontoDAO.buscar(codigo);
		if (ponto == null) {
			System.out.println("Nenhum registro encontrado !");
		}

		System.out.println("Registro encontrado: ");
		System.out.println("Código: " + ponto.getCodigo() + " Número: " + ponto.getPon_numero());
	}

	@Ignore
	@Test
	public void excluir() {

		Long codigo = 3L;

		PontoDAO pontoDAO = new PontoDAO();
		Ponto ponto = pontoDAO.buscar(codigo);
		if (ponto == null) {
			System.out.println("Nenhum registro encontrado !");
		} else {
			pontoDAO.excluir(ponto);
			System.out.println("Registro removido: ");
			System.out.println("Código: " + ponto.getCodigo() + " Número: " + ponto.getPon_numero());

		}
	}

	@Ignore
	@Test
	public void editar() {
		Long codigo = 1L;

		PontoDAO pontoDAO = new PontoDAO();
		Ponto ponto = pontoDAO.buscar(codigo);

		if (ponto == null) {
			System.out.println("Nenhum registro encontrado !");
		}

		else {
			pontoDAO.editar(ponto);
			System.out.println("Registro editado (ANTES): ");
			System.out.println("Código: " + ponto.getCodigo() + " Número: " + ponto.getPon_numero());
			ponto.setPon_numero("13");
			pontoDAO.editar(ponto);

			System.out.println("Registro editado (DEPOIS): ");
			System.out.println("Código: " + ponto.getCodigo() + " Número: " + ponto.getPon_numero());

		}
	}

	@Ignore
	@Test
	public void buscarPorNumero() {

		PontoDAO pontoDAO = new PontoDAO();
		Ponto ponto = pontoDAO.buscarNumero("2");

		System.out.println(ponto);
		Assert.assertNull(ponto);
	}



	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	@Test
	public void teste() {
	
	LinhaDAO linhaDAO = new LinhaDAO();
	Linha linhaSelecionado = linhaDAO.buscar(1L);

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
	Ponto ponto = pontoDAO.buscarNumero("1");

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
				System.out.println(ponto_Linha);
				somaTempo = somaTempo + ponto_LinhaMaisProxima.getPonLi_tempo();
				ponto_LinhaMaisProxima = ponto_LinhaMaisProxima.getProximoPonto();
			}

			else {
				linha_Dispositivo.setLiDis_tempoFinal(somaTempo);
				
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
	System.out.println(json);
	
}
}