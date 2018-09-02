package br.com.servidor.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;

import org.hibernate.criterion.Restrictions;

import br.com.servidor.domain.Linha;

import br.com.servidor.domain.Ponto_Linha;
import br.com.servidor.util.HibernateUtil;


public class Ponto_LinhaDAO extends GenericDAO<Ponto_Linha> {
		
	//MÉTODO CRIADO PARA CARREGAR OS PONTO_LINHA DE UM PONTO SELECIONADO 
	@SuppressWarnings("unchecked")
	public List<Ponto_Linha> buscarPorPonto(Long pontoCodigo){	
		
		Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

		try {
			Criteria consulta = sessao.createCriteria(Ponto_Linha.class);
			
			consulta.add(Restrictions.eq("ponto.codigo", pontoCodigo));									
//			consulta.addOrder(Order.asc("nome"));			
			List<Ponto_Linha> resultado = consulta.list();
			return resultado;
		} 
		
		catch (RuntimeException erro) {
			throw erro;
		} 
		finally {
			sessao.close();
		}
				
	}
	
	//MÉTODO CRIADO PARA CARREGAR OS PONTO_LINHA DE UM PONTO SELECIONADO 
		@SuppressWarnings("unchecked")
		public List<Linha> buscarPorPonto_Linha(Long ponto_LinhaCodigo){	
			
			Session sessao = HibernateUtil.getFabricaDeSessoes().openSession();

			try {
				Criteria consulta = sessao.createCriteria(Linha.class);
				
				consulta.add(Restrictions.eq("ponto_linha.codigo", ponto_LinhaCodigo));									
//				consulta.addOrder(Order.asc("nome"));			
				List<Linha> resultado = consulta.list();
				return resultado;
			} 
			
			catch (RuntimeException erro) {
				throw erro;
			} 
			finally {
				sessao.close();
			}
			
			
			
					
		}
	
	
		
		
		
		
	
		
		
		
	
	
	}