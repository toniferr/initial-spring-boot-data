package com.bolsaideas.springboot.app.models.daoInterface;

import java.util.List;

import com.bolsaideas.springboot.app.models.entity.Cliente;

public interface ClienteDaoInterface {
	
	public List<Cliente> findAll();
	public void save(Cliente cliente);
}
