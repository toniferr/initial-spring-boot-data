package com.bolsaideas.springboot.app.models.daoImpl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.bolsaideas.springboot.app.models.daoInterface.ClienteDaoInterface;
import com.bolsaideas.springboot.app.models.entity.Cliente;

//@Repository("clienteDaoImp") En el caso de más de una implementación. Se usa en Qualifier
@Repository
public class ClienteDaoImpl implements ClienteDaoInterface {

	@PersistenceContext
	private EntityManager em;

	@SuppressWarnings("unchecked")
	//@Transactional(readOnly = true) // si es de escritura se suprime. Se pasan al service
	@Override
	public List<Cliente> findAll() {
		// TODO Auto-generated method stub
		return em.createQuery("from Cliente").getResultList();
	}

	//@Transactional
	@Override
	public void save(Cliente cliente) {
		if (cliente.getId() != null && cliente.getId() > 0) {
			em.merge(cliente);
		} else {
			em.persist(cliente);
		}
	}

	@Override
	//@Transactional(readOnly = true)
	public Cliente findOne(Long id) {
		return em.find(Cliente.class, id);
	}

	@Override
	//@Transactional
	public void delete(Long id) {
		em.remove(findOne(id));
	}

}
