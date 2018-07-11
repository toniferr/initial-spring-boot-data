package com.bolsaideas.springboot.app.models.serviceInterface;

import java.util.List;

import com.bolsaideas.springboot.app.models.entity.Cliente;

public interface ClienteServiceInterface {

	public List<Cliente> findAll();

	public void save(Cliente cliente);

	public Cliente findOne(Long id);
	
	public void delete(Long id);
}
