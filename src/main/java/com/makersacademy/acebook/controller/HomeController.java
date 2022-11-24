package com.makersacademy.acebook.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {
	@RequestMapping(value = "/")
	public String index(Principal principal) {
    if (principal != null) {
      return "redirect:/posts";
    }
		return "home/home";
	}
}
