package br.com.servidor.dao;



import org.hibernate.Criteria;
import org.hibernate.Session;

import org.hibernate.criterion.Restrictions;

import br.com.servidor.domain.Ponto;
import br.com.servidor.util.HibernateUtil;

public class PontoDAO extends GenericDAO<Ponto>{

public Ponto buscarNumero(String numeroParam){		
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();
		
		try {
			Criteria consulta = sessao.createCriteria(Ponto.class);
			consulta.add(Restrictions.eq("pon_numero", numeroParam));			
			Ponto resultado = (Ponto) consulta.uniqueResult();
			return resultado;			
		} 
		
		catch (RuntimeException erro) {
		throw erro;
		}
		finally{
			sessao.close();
		}		
	}	




	
}
