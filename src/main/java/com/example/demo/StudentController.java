package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StudentController {
	@Autowired
	StudentRepository repo;
	
	@GetMapping("/allstudents")
	public String ViewHomePage(Model model) {
		List<Student> students = new ArrayList<>();
		students = repo.findAll();
		System.out.println(students);
		model.addAttribute("students", students);
//		model.addAttribute("students",students);
		return "home.html";
	}
	
	@GetMapping("/")
	public String viewLoginPage(Model model) {
		return "login";
	}
	
	@GetMapping("/login/{id}")
	@ResponseBody
	public Optional<Student> getStudent(@RequestParam("sid_student") int id) {
		return repo.findById(id);
	}
}
