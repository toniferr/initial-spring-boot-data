package com.bolsaideas.springboot.app.models.daoInterface;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bolsaideas.springboot.app.models.entity.Cliente;

public interface ClienteDaoInterface extends CrudRepository<Cliente, Long> {
//
//	public List<Cliente> findAll();
//
//	public void save(Cliente cliente);
//
//	public Cliente findOne(Long id);
//	
//	public void delete(Long id);
}
