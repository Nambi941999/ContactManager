package com.example.demo.controller;

import java.util.Random;

import javax.mail.Session;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.EmailService.EmailServiceImpl;
import com.example.demo.dao.UserDao;
import com.example.demo.entities.EmailDetails;
import com.example.demo.entities.Users;

@Controller
public class ForgotController {
	
	@Autowired
	private EmailServiceImpl emailServiceImpl;
	
	@Autowired
	private UserDao userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	
	
	Random random = new Random(1000);

	
	
	//email id form open handler
	@RequestMapping("/forgot")
	public String openEmailForm() {
		
		
		return "forgot_email_form";
	}
	
	@PostMapping("/send-otp")
	public String sendOTP(@RequestParam("email") String email,HttpSession session) {
		
		
		System.out.println("Forgot email::"+email);
		
		// Generating four digit OTP
		
		
		int otp = random.nextInt(999999);
		
		System.out.println("OTP: "+otp);
		
		// Write Code for sent otp to email
		
		
		EmailDetails emaildetail = new EmailDetails();
		
		emaildetail.setSubject("OTP From ContactManager");
		emaildetail.setMsgBody("OTP is "+otp);
		emaildetail.setRecipient(email);
		
		boolean sendSimpleMail = emailServiceImpl.sendSimpleMail(emaildetail);
		
		if(sendSimpleMail) {
			 
			session.setAttribute("emailOtp",otp);
			session.setAttribute("email", email);
			
			return "verify_otp";
		}
		else
		{
			session.setAttribute("message","Check your Email id");
			return "forgot_email_form";

		}
		
	}
	
	
	//verify otp
	@PostMapping("/verify-otp")
	public String verifyOtp(@RequestParam("otp") Integer otp,HttpSession session) {
		
		Integer myOtp = (Integer) session.getAttribute("emailOtp");
		String email = (String) session.getAttribute("email");
		
		if(myOtp.equals(otp)) {
			
			//password change form
			Users user = this.userRepository.getUsersByUsername(email);
			
			if(user==null) {
				//send error message this email is not registered
				
				session.setAttribute("message","User does not exist with this email");
				return "forgot_email_form";
				
			}
			else {
				
				// Send to change Password Form
				
			}
			
			System.out.println("Your otp is successfully verifyed");
			
			return "change_password_form";
			
		}
		
		else {
			
			System.out.println("Something went wrong");
			
			session.setAttribute("message", "You have entered wrong password");
			
			return "verify_otp";
			
		}
		
		
		
	}
	
	
	//CHANGE PASSWORD HANDLER
	@PostMapping("/changePassword")
	public String changePassword(@RequestParam("newPassword") String newPassword,HttpSession session) {
		
		String email = (String) session.getAttribute("email");
		
		Users user = this.userRepository.getUsersByUsername(email);
		
		user.setPassword(this.passwordEncoder.encode(newPassword));
		
		this.userRepository.save(user);
		
		
		
		return "redirect:/signin?change=password changed successfully";
		
	}

}
