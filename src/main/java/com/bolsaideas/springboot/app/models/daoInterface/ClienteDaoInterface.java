package com.bolsaideas.springboot.app.models.daoInterface;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bolsaideas.springboot.app.models.entity.Cliente;

public interface ClienteDaoInterface extends PagingAndSortingRepository<Cliente, Long> {
	//
	// public List<Cliente> findAll();
	//
	// public void save(Cliente cliente);
	//
	// public Cliente findOne(Long id);
	//
	// public void delete(Long id);
}
