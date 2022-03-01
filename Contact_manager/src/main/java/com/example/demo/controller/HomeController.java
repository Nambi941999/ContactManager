package com.example.demo.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.dao.UserDao;
import com.example.demo.entities.Users;
import com.example.demo.helper.Message;

@Controller
public class HomeController {
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDao userdao;
	
	@RequestMapping("/")
	public String home( Model model) {
		
		model.addAttribute("tittle", "This is Contact Manager");
		return "home";
	}
	
//	@RequestMapping("/about")
//	public ModelAndView about() {
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("about", "About-Contact Manager");
//		return mav;
//	}
	
	@RequestMapping("/about")
	public String about(Model model) {
		model.addAttribute("about", "About - Contact Manager");
		return "about";
		
	}
	
	@RequestMapping("/signup")
	public String signup(Model model) {
		model.addAttribute("signup", "Signup - Contact Manager");
		model.addAttribute("users",new Users());
		return "signup";
	}
	
	//handler for register users..
	@RequestMapping(value="/do_register",method = RequestMethod.POST)
	public String registerUser(@Valid @ModelAttribute("users") Users users ,BindingResult result1,@RequestParam(value = "agreement",defaultValue = "false") boolean agreement,Model model,HttpSession session) {
		
		try {
			if(!agreement) {
				System.out.println("You are not agree with the terms and condition.");
				throw new Exception("You are not agree with the terms and condition.");
			}
			
			if(result1.hasErrors()) {
				System.out.println("ERROR"+result1.toString());
				model.addAttribute("users",users);
				return "signup";
			}
			
			users.setRole("ROLE_USER");
			users.setEnabled(true);
			users.setImageUrl("default.png");
			users.setPassword(passwordEncoder.encode(users.getPassword()));
			
			
			System.out.println("Agreement"+agreement);
			System.out.println("USER"+users);
			
			Users result = userdao.save(users);
			model.addAttribute("users", new Users());
			session.setAttribute("message", new Message("Successfully Registrated", "alert-success"));
			return "signup";
			
			
		} catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("users", users);
			session.setAttribute("message", new Message("Something went Wrong !!"+e.getMessage(), "alert-danger"));
			return "signup";
		}
		
	}

	//handler for custom login..
	@GetMapping("/signin")
	public String customLogin(Model model) {
		
		model.addAttribute("tittle", "Login page");
		return "login";
	}
	
}
