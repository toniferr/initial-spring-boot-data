package com.bolsaideas.springboot.app.controllers;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bolsaideas.springboot.app.models.entity.Cliente;
import com.bolsaideas.springboot.app.models.serviceInterface.ClienteServiceInterface;
import com.bolsaideas.springboot.app.util.paginator.PageRender;

@Controller
@SessionAttributes("cliente") // se guarda en los atributos de la sesión el objeto cliente
public class ClienteController {

	// @Qualifier("clienteDaoImpl") definido el nombre en @Repository
	@Autowired
	private ClienteServiceInterface clienteService;
	// private ClienteDaoInterface clienteDao; se implementa el service y el dao va
	// al service
	
	private final Logger log = LoggerFactory.getLogger(getClass());
	
	@GetMapping(value="/ver/{id}")
	public String ver(@PathVariable(value="id") Long id, Map<String,Object> model, RedirectAttributes flash) {
		
		Cliente cliente = clienteService.findOne(id);
		if (cliente==null) {
			flash.addFlashAttribute("error","El cliente no existe en la base de datos");
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put("titulo", "Detalle cliente: " + cliente.getNombre());
		return "ver";
	}

	@RequestMapping(value = "/listar", method = RequestMethod.GET) // se puede usar tambien getMapping
	public String listar(@RequestParam(name="page", defaultValue="0") int page, Model model) {
		
		Pageable pageRequest = new PageRequest(page, 5);
		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		
		PageRender<Cliente> pageRender = new PageRender<>("/listar",clientes);
		
		model.addAttribute("titulo", "Listado de clientes");
		// model.addAttribute("clientes", clienteDao.findAll());
//		model.addAttribute("clientes", clienteService.findAll());
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
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
	public String guardar(@Valid Cliente cliente, BindingResult result, RedirectAttributes flash, Model model, @RequestParam("file") MultipartFile foto, SessionStatus status) { // status
																												// si
																												// usas
																												// SessionAttributes

		if (result.hasErrors()) {
			model.addAttribute("titulo", "Formulario de cliente");
			return "form";
		}
		
		if(!foto.isEmpty()) {
//			Path directorioRecursos = Paths.get("src//main//resources//static//upload"); path interno al proyecto
//			String rootPath = directorioRecursos.toFile().getAbsolutePath(); path interno al proyecto
//			String rootPath = "C://Temp//upload"; //directorio externo pero en cliente, no en servidor
			String uniqueFilename = UUID.randomUUID().toString()+"_"+foto.getOriginalFilename();
			Path rootPath = Paths.get("upload").resolve(uniqueFilename); /*C-SpringBoot-workspace-spring-boot-data-upload*/
			Path rootAbsolutPath = rootPath.toAbsolutePath();
			
			log.info("rootPath: "+ rootPath);
			log.info("rootAbsolutPath: "+rootAbsolutPath);
			
			try {
				Files.copy(foto.getInputStream(), rootAbsolutPath);
//				byte[] bytes = foto.getBytes();
//				Path rutaCompleta = Paths.get(rootPath + "//" + foto.getOriginalFilename());
//				Files.write(rutaCompleta, bytes);
				flash.addFlashAttribute("info","Ha subido correctamente "+foto.getName());
				
				cliente.setFoto(foto.getOriginalFilename());
			} catch (IOException e) {
				flash.addFlashAttribute("error","No se ha subido correctamente "+foto.getName());
			}
			
		}
		
		String flashMessage = (cliente.getId()!=null)? "Cliente editado correctamente" : "Cliente creado correctamente";
		// clienteDao.save(cliente);
		clienteService.save(cliente);
		status.setComplete(); // se elimina el objeto cliente de la session
		flash.addFlashAttribute("success", flashMessage);
		return "redirect:listar";
	}

	@RequestMapping(value = "/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {

		if (id > 0) {
			// clienteDao.delete(id);
			clienteService.delete(id);
		}
		flash.addFlashAttribute("success", "Cliente borrado correctamente");
		return "redirect:/listar";
	}

}
