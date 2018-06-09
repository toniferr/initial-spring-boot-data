package com.bolsaideas.springboot.app.models.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bolsaideas.springboot.app.models.daoInterface.ClienteDaoInterface;
import com.bolsaideas.springboot.app.models.entity.Cliente;

@Repository
public class ClienteDaoImpl implements ClienteDaoInterface {

	@PersistenceContext
	private EntityManager em;
	
	@SuppressWarnings("unchecked")
	@Transactional(readOnly = true) //si es de escritura se suprime
	@Override
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("from Cliente").getResultList();
	}

}
