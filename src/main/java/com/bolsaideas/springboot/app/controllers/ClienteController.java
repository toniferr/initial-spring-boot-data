package com.bolsaideas.springboot.app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.bolsaideas.springboot.app.models.daoInterface.ClienteDaoInterface;

@Controller
public class ClienteController {
	
//	@Qualifier("clienteDaoImpl") definido el nombre en @Repository
	@Autowired
	private ClienteDaoInterface clienteDao;
	
	@RequestMapping(value="listar", method=RequestMethod.GET) //se puede usar tambien getMapping
	public String listar(Model model) {
		model.addAttribute("titulo", "listado de clientes");
		model.addAttribute("clientes", clienteDao.findAll());
		return "listar";
	}

}
