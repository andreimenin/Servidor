package br.com.servidor.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import org.hibernate.criterion.Restrictions;

import br.com.servidor.domain.Linha_Dispositivo;
import br.com.servidor.util.HibernateUtil;

public class Linha_DispositivoDAO extends GenericDAO<Linha_Dispositivo> {

	public List<Linha_Dispositivo> buscarPorLinha(Long linhaCodigo) {

		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(Linha_Dispositivo.class);

			consulta.add(Restrictions.eq("linha.codigo", linhaCodigo));
			@SuppressWarnings("unchecked")
			List<Linha_Dispositivo> resultado = consulta.list();

			return resultado;

		} catch (RuntimeException erro) {

			throw erro;
		} finally {
			sessao.close();
		}
	}

}