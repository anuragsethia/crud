package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
//		System.out.println(students);
		model.addAttribute("students", students);
		return "home";
	}
	
	@GetMapping("/")
	public String viewLoginPage(Model model) {
		return "login";
	}
	
	@RequestMapping("/getStudent")
	public String getStudent(@RequestParam("id") int id,@RequestParam("password") String password,Model model) {
		user_data user = userrepo.findById(id).orElse(new user_data());
		System.out.println(user);
		String pass = user.getPassword();
		if(pass.equals(password)) {
			if(user.getDesignation().equals("student")) {
				Student student = repo.findById(id).orElse(new Student());
				System.out.println(student);
				model.addAttribute("students", student);
				return "home";
			}else {
				List<Student> students = new ArrayList<>();
				students = repo.findAll();
//				System.out.println(students);
				model.addAttribute("students", students);
				
				return "teacher";
			}
		}else {
			return "login";
		}
		
		
	}
	
	@RequestMapping("/signupstudent")
	public String signupStudentDirect(Model model) {
		user_data user = new user_data();
		model.addAttribute("user", user);
		return "signup";
	}
	
	@PostMapping("/signup")
	public String signupStudent(@ModelAttribute("user") user_data user,Model model) {
		userrepo.save(user);
//		model.addAttribute("students",repo.findAll());
		return "login";
	}	
	
	
	@PostMapping("/save")
	public String saveDetail(@ModelAttribute("student") Student student,Model model) {
		repo.save(student);
		model.addAttribute("students",repo.findAll());
		return "teacher";
	}
	
	@RequestMapping("/edit/{id}")
	public String editStudent(@PathVariable(name = "id",required = false) int id,Model model) {
		Optional<Student> student = repo.findById(id);
		model.addAttribute("student",student);
		return "editDetail";
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteStudent(@PathVariable("id") int id,Model model) {
		repo.deleteById(id);
		model.addAttribute("students",repo.findAll());
		return "teacher";
	}
	
}

