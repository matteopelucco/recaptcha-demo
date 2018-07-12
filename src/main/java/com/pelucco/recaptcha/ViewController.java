package com.pelucco.recaptcha;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller 
public class ViewController {
	
	@GetMapping("/standard")
	public String standard(Model model) {
		model.addAttribute("user", new User());
		return "standard";
	}
	
	@GetMapping("/invisible")
	public String invisible(Model model) {
		model.addAttribute("user", new User());
		return "invisible";
	}
}
