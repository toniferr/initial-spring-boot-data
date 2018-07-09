package com.bolsaideas.springboot.app.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.bolsaideas.springboot.app.models.daoInterface.ClienteDaoInterface;
import com.bolsaideas.springboot.app.models.entity.Cliente;

@Controller
@SessionAttributes("cliente") // se guarda en los atributos de la sesión el objeto cliente
public class ClienteController {

	// @Qualifier("clienteDaoImpl") definido el nombre en @Repository
	@Autowired
	private ClienteDaoInterface clienteDao;

	@RequestMapping(value = "/listar", method = RequestMethod.GET) // se puede usar tambien getMapping
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de clientes");
		model.addAttribute("clientes", clienteDao.findAll());
		return "listar";
	}

	@RequestMapping(value = "/form")
	public String crear(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put("titulo", "Formulario de cliente");
		return "form";
	}

	@RequestMapping(value = "/form/{id}")
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model) {
		Cliente cliente = null;

		if (id > 0) {
			cliente = clienteDao.findOne(id);
		} else {
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Editar cliente");
		return "form";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, Model model, SessionStatus status) { // status
																												// si
																												// usas
																												// SessionAttributes

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de cliente");
			return "form";
		}
		clienteDao.save(cliente);
		status.setComplete(); // se elimina el objeto cliente de la session
		return "redirect:listar";
	}

	@RequestMapping(value = "/eliminar/{id}")
	public String editar(@PathVariable(value = "id") Long id) {

		if (id > 0) {
			clienteDao.delete(id);
		}
		return "redirect:/listar";
	}

}
