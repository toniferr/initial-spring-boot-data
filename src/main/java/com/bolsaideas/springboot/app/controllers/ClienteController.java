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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsaideas.springboot.app.models.daoInterface.ClienteDaoInterface;
import com.bolsaideas.springboot.app.models.entity.Cliente;
import com.bolsaideas.springboot.app.models.serviceInterface.ClienteServiceInterface;

@Controller
@SessionAttributes("cliente") // se guarda en los atributos de la sesión el objeto cliente
public class ClienteController {

	// @Qualifier("clienteDaoImpl") definido el nombre en @Repository
	@Autowired
	private ClienteServiceInterface clienteService;
	// private ClienteDaoInterface clienteDao; se implementa el service y el dao va
	// al service

	@RequestMapping(value = "/listar", method = RequestMethod.GET) // se puede usar tambien getMapping
	public String listar(Model model) {
		model.addAttribute("titulo", "Listado de clientes");
		// model.addAttribute("clientes", clienteDao.findAll());
		model.addAttribute("clientes", clienteService.findAll());
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
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = null;

		if (id > 0) {
			// cliente = clienteDao.findOne(id);
			cliente = clienteService.findOne(id);
			if (cliente==null) {
				flash.addFlashAttribute("error", "No existe el cliente");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute("error", "No existe el cliente");
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Editar cliente");
		return "form";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid Cliente cliente, BindingResult result, RedirectAttributes flash, Model model, SessionStatus status) { // status
																												// si
																												// usas
																												// SessionAttributes

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de cliente");
			return "form";
		}
		
		String flashMessage = (cliente.getId()!=null)? "Cliente editado correctamente" : "Cliente creado correctamente";
		// clienteDao.save(cliente);
		clienteService.save(cliente);
		status.setComplete(); // se elimina el objeto cliente de la session
		flash.addFlashAttribute("success", flashMessage);
		return "redirect:listar";
	}

	@RequestMapping(value = "/eliminar/{id}")
	public String editar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			// clienteDao.delete(id);
			clienteService.delete(id);
		}
		flash.addFlashAttribute("success", "Cliente borrado correctamente");
		return "redirect:/listar";
	}

}
