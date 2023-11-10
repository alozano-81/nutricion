package com.salud.nutricion.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.salud.nutricion.service.UserService;

@RestController
@RequestMapping("test")
public class SecuredController {

	@Autowired
	UserService userService;

	@GetMapping("/xx")
	public String greetings(@RequestParam(value = "name", defaultValue = "World") String name) {
		System.out.println("llega test");
		return "Hello {" + name + "}";
	}
}
