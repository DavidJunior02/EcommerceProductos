package com.curso.ecommerce.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.curso.ecommerce.model.Orden;
import com.curso.ecommerce.model.Usuario;
import com.curso.ecommerce.repository.IDetalleOrdenRepository;
import com.curso.ecommerce.service.IDetalleOrdenService;
import com.curso.ecommerce.service.IOrdenService;
import com.curso.ecommerce.service.IUsuarioService;
import com.curso.ecommerce.service.ReportService;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {
	
	private final Logger logger= LoggerFactory.getLogger(UsuarioController.class);
	
	@Autowired
	private IUsuarioService usuarioService;
	
	@Autowired
	private IOrdenService ordenService;
	
	@Autowired
	private IDetalleOrdenService detalleService;
	
	
	@Autowired
	private ReportService reportservice;

	BCryptPasswordEncoder passEncode= new BCryptPasswordEncoder();
	
	
	// /usuario/registro
	@GetMapping("/registro")
	public String create() {
		return "usuario/registro";
	}
	
	@PostMapping("/save")
	public String save(Usuario usuario, RedirectAttributes redirectAttributes) {
		try {
			
			logger.info("Usuario registro: {}", usuario);
			usuario.setTipo("USER");
			usuario.setPassword( passEncode.encode(usuario.getPassword()));
			usuarioService.save(usuario);	
			redirectAttributes.addFlashAttribute("bien",true);
		}catch(Exception e) {
			redirectAttributes.addFlashAttribute("unsuccess",true);
			
		}
		
		return "redirect:/usuario/login";
	}
	
	@GetMapping("/login")
	public String login() {
		return "usuario/login";
	}
	 
	  @GetMapping("/acceder")
	public String acceder(Usuario usuario, HttpSession session,RedirectAttributes redirectAttributes,Model model,Authentication auth) {
		logger.info("Accesos : {}", usuario);
		
		Optional<Usuario> user=usuarioService.findById(Integer.parseInt(session.getAttribute("idusuario").toString()));
		//logger.info("Usuario de db: {}", user.get());
       
		
	    
		if (user.isPresent()) {
			session.setAttribute("idusuario", user.get().getId());
			model.addAttribute("rol", auth.getAuthorities().toString());
			if (user.get().getTipo().equals("ADMIN")) {
				redirectAttributes.addFlashAttribute("success",true);
			  
				return "redirect:/administrador";
			}else {
				redirectAttributes.addFlashAttribute("success",true);
				// Guardar el nombre de usuario en la sesión
	            session.setAttribute("nombreUsuario", user.get().getNombre());
	            
				return "redirect:/";
			}
		}else {
			logger.info("Usuario no existe en la BD");
			
		}
		
		 return "redirect:/usuario/login";
	}
	
	@GetMapping("/compras")
	public String obtenerCompras(Model model, HttpSession session) {
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		
		Usuario usuario= usuarioService.findById(  Integer.parseInt(session.getAttribute("idusuario").toString()) ).get();
		List<Orden> ordenes= ordenService.findByUsuario(usuario);
		logger.info("ordenes {}", ordenes);
		
		model.addAttribute("ordenes", ordenes);
		
		return "usuario/compras";
	}
	
	@GetMapping("/detalle/{id}")
	public String detalleCompra(@PathVariable Integer id, HttpSession session, Model model) {
		logger.info("Id de la orden: {}", id);
		Optional<Orden> orden=ordenService.findById(id);
		
		 model.addAttribute("orden", orden);
		 model.addAttribute("ordenId", orden.get().getId());
		model.addAttribute("detalles", orden.get().getDetalle());
		
		
		//session
		model.addAttribute("sesion", session.getAttribute("idusuario"));
		return "usuario/detallecompra";
	}

	@GetMapping("/reporteOrden")
	public void generateExcelOrden(HttpServletResponse response,Model model) throws IOException {
		
		response.setContentType("application/vnd.ms-excel");
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=ListadoComprasElectro.xls";
		response.setHeader(headerKey, headerValue);
		reportservice.generateExcel2(response);
	}
	
	@GetMapping("/reporte/{id}")
	public void generateExcel(@PathVariable Integer id,HttpServletResponse response,Model model) throws IOException {
		
		//Optional<Orden> orden=ordenService.findById(id);
		
	//	 model.addAttribute("ordenId", orden.get().getId());
		response.setContentType("application/vnd.ms-excel");
		
		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=DetalleComprasElectro"+id+".xls";
		response.setHeader(headerKey, headerValue);
		reportservice.generateExcel(id, response);
	}
	
	@GetMapping("/cerrar")
	public String cerrarSesion( HttpSession session ) {
		session.removeAttribute("idusuario");
		return "redirect:/";
	}
}
