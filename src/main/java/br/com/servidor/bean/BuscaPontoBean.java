package br.com.servidor.bean;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.omnifaces.util.Messages;

import br.com.servidor.dao.LinhaDAO;
import br.com.servidor.dao.Linha_DispositivoDAO;
import br.com.servidor.dao.PontoDAO;
import br.com.servidor.dao.Ponto_LinhaDAO;

import br.com.servidor.domain.Linha;
import br.com.servidor.domain.Linha_Dispositivo;
import br.com.servidor.domain.Ponto;
import br.com.servidor.domain.Ponto_Linha;

@SuppressWarnings("serial")
@ManagedBean
@ViewScoped
public class BuscaPontoBean implements Serializable {

	Ponto pontoSelecionado = new Ponto();
	private Boolean exibePainelDados;

	private List<Ponto_Linha> ponto_Linhas;
	private List<Ponto_Linha> ponto_Linhas2;
	private List<Linha> linhas;
	private Ponto_Linha ponto_Linha;
	private Linha linha;
	private List<Ponto> pontos;
	private List<Linha_Dispositivo> linha_Dispositivos;
	private Linha linhaSelecionado;

	public Ponto getPontoSelecionado() {
		return pontoSelecionado;
	}

	public void setPontoSelecionado(Ponto pontoSelecionado) {
		this.pontoSelecionado = pontoSelecionado;
	}

	public Boolean getExibePainelDados() {
		return exibePainelDados;
	}

	public void setExibePainelDados(Boolean exibePainelDados) {
		this.exibePainelDados = exibePainelDados;
	}

	public List<Ponto_Linha> getPonto_Linhas() {
		return ponto_Linhas;
	}

	public void setPonto_Linhas(List<Ponto_Linha> ponto_Linhas) {
		this.ponto_Linhas = ponto_Linhas;
	}

	public List<Ponto_Linha> getPonto_Linhas2() {
		return ponto_Linhas2;
	}

	public void setPonto_Linhas2(List<Ponto_Linha> ponto_Linhas2) {
		this.ponto_Linhas2 = ponto_Linhas2;
	}

	public List<Linha> getLinhas() {
		return linhas;
	}

	public void setLinhas(List<Linha> linhas) {
		this.linhas = linhas;
	}

	public Ponto_Linha getPonto_Linha() {
		return ponto_Linha;
	}

	public void setPonto_Linha(Ponto_Linha ponto_Linha) {
		this.ponto_Linha = ponto_Linha;
	}

	public Linha getLinha() {
		return linha;
	}

	public void setLinha(Linha linha) {
		this.linha = linha;
	}

	public List<Ponto> getPontos() {
		return pontos;
	}

	public void setPontos(List<Ponto> pontos) {
		this.pontos = pontos;
	}

	public List<Linha_Dispositivo> getLinha_Dispositivos() {
		return linha_Dispositivos;
	}

	public void setLinha_Dispositivos(List<Linha_Dispositivo> linha_Dispositivos) {
		this.linha_Dispositivos = linha_Dispositivos;
	}

	public Linha getLinhaSelecionado() {
		return linhaSelecionado;
	}

	public void setLinhaSelecionado(Linha linhaSelecionado) {
		this.linhaSelecionado = linhaSelecionado;
	}

	@PostConstruct // método novo vai ser chamado no momento em que a tela ser
					// carregada
	public void novo() {

		pontoSelecionado = new Ponto();
		exibePainelDados = false;
	}

	public void buscar() {
		try {
			linhaSelecionado = new Linha();
			PontoDAO pontoDAO = new PontoDAO();

			Ponto resultado = pontoDAO.buscarNumero(pontoSelecionado.getPon_numero());

			if (resultado == null) {
				Messages.addGlobalWarn("Não existe ponto cadastrado para o número informado");
				exibePainelDados = false;
			} else {
				pontoSelecionado = resultado;
				exibePainelDados = true;
				popular();
				System.out.println("Ponto pesquisado: " + "Código: " + pontoSelecionado.getCodigo() + " - " +

						"Número: " + pontoSelecionado.getPon_numero());
			}

		} catch (Exception erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar buscar o ponto");
			erro.printStackTrace();
		}
	}

	// método criado para popular cidades com base no estado selecionado na
	// combobox
	public void popular() {

		try {
			if (pontoSelecionado != null) {

				Ponto_LinhaDAO ponto_LinhaDAO = new Ponto_LinhaDAO();
				ponto_Linhas = ponto_LinhaDAO.buscarPorPonto(pontoSelecionado.getCodigo());

				LinhaDAO linhaDAO = new LinhaDAO();
				linhas = linhaDAO.listar();

				ArrayList<Linha> aList = new ArrayList<>();

				for (Ponto_Linha ponto_Linha : ponto_Linhas) {
					for (Linha linha : linhas) {
						if (ponto_Linha.getLinha().getCodigo() == linha.getCodigo()) {
							aList.add(linha);

						}
					}
				}
				linhas = aList;
				System.out.println("LISTA DE LINHAS: " + linhas);

			} else {
				ponto_Linhas = new ArrayList<>();
			}
		} catch (RuntimeException erro) {
			Messages.addGlobalError("Ocorreu um erro ao tentar filtrar as linhas");
			erro.printStackTrace();
		}

	}

	public void onRowSelect() {

		Linha_DispositivoDAO liDispositivoDAO = new Linha_DispositivoDAO();
		linha_Dispositivos = liDispositivoDAO.buscarPorLinha(linhaSelecionado.getCodigo());

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
		linha_Dispositivos = aListLD;
		PontoDAO pontoDAO = new PontoDAO();
		Ponto ponto = pontoDAO.buscarNumero(pontoSelecionado.getPon_numero());

		// Pegando o ponto_linha que tem o código igual o ponto digitado
		Ponto_Linha ponto_LinhaDigitado = new Ponto_Linha();

		// Pegando o Ponto_Linha correspondente ao Ponto digitado entre os Ponto_Linha
		// filtrados
		for (Ponto_Linha ponto_Linha : ponto_Linhas) {
			if (ponto_Linha.getPonto().getCodigo() == ponto.getCodigo()
					&& ponto_Linha.getLinha().getCodigo() == linhaSelecionado.getCodigo()) {
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
			for (@SuppressWarnings("unused")
			Ponto_Linha ponto_Linha : ponto_Linhas) {

				if (ponto_LinhaMaisProxima.getCodigo() != ponto_LinhaDigitado.getCodigo()) {
					System.out.println(ponto_LinhaMaisProxima.getPonto().getPon_numero() + " - " + somaTempo);
					somaTempo = somaTempo + ponto_LinhaMaisProxima.getPonLi_tempo();
					ponto_LinhaMaisProxima = ponto_LinhaMaisProxima.getProximoPonto();
				}

				else {
					linha_Dispositivo.setLiDis_tempoFinal(somaTempo);
					System.out.println("Tempo Final: " + linha_Dispositivo.getLiDis_tempoFinal());
					break;
				}

			}

			System.out.println(linha_Dispositivo);
		}

		// Historico historico = new Historico();
		// historico.setHis_dataHora(new Date());
		// historico.setLinha(linhaSelecionado);
		// historico.setPonto(ponto);

		System.out.println(linha_Dispositivos);

	}

}
