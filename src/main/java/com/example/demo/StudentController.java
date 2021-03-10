package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class StudentController {
	@Autowired
	StudentRepository repo;
	@Autowired
	userDataRepository userrepo;
	
	@GetMapping("/allstudents")
	public String ViewHomePage(Model model) {
		List<Student> students = new ArrayList<>();
		students = repo.findAll();
		System.out.println(students);
		model.addAttribute("students", students);
		return "home.html";
	}
	
	@GetMapping("/")
	public String viewLoginPage(Model model) {
		return "login";
	}
	
	@RequestMapping("/getStudent")
	@ResponseBody
	public String getStudent(@RequestParam("id") int id,@RequestParam("password") String password,Model model) {
		user_data user = userrepo.findById(id).orElse(new user_data());
		String des = user.getDesignation();
		System.out.println(user);
		System.out.println(des);
		if(des.equals("student")) {
			Student student;
			student = repo.findById(id).orElse(new Student());
			System.out.println("Inner Loop : " + repo.findById(id));	
			model.addAttribute("students", student);
			
			return "home.html";
			
		}else {
			return "home.html";
		}
//		return "home.html";
	}
	
}

