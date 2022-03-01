 package com.example.demo.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.dao.ContactRepository;
import com.example.demo.dao.UserDao;
import com.example.demo.entities.Contact;
import com.example.demo.entities.Users;
import com.example.demo.helper.Message;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserDao userdao;
	
	@Autowired
	private ContactRepository contactRepository;
	
	// Method for adding common data to response..
	@ModelAttribute
	public void addCommonData(Model model,Principal principal) {
		
		String userName = principal.getName();
		System.out.println("USERNAME: "+userName);
		
		//get the user using username(Email)

		Users users = userdao.getUsersByUsername(userName);
		System.out.println("USER: "+users);
		model.addAttribute("users", users);
		
	}
	
	// Dashboard home..
	@RequestMapping("/index")
	public String dashboard(Model model,Principal principal) {
		
//		String userName = principal.getName();
//		System.out.println("USERNAME: "+userName);
//		
//		//get the user using username(Email)
//
//		Users users = userdao.getUsersByUsername(userName);
//		System.out.println("USER: "+users);
//		model.addAttribute("users", users);
//		
		model.addAttribute("tittle", "User Dashboard");
		return "normal/user_dashboard";
	}
	
	// Add contact handler..
	
	@GetMapping("/add-contact")
	public String openAddContactForm(Model model) {
		
		model.addAttribute("tittle", "Add Contact");
		model.addAttribute("contact",new Contact());
		return "normal/add_contact_form";
	}
	
	//processing add contact form..
	
//    @RequestMapping(value = {"/process-contact"}, method = RequestMethod.POST, consumes = {"multipart/form-data"})
	@PostMapping("/process-contact")
	public String processContact(
			@ModelAttribute Contact contact,
			@RequestParam("imageUrl") MultipartFile file,
			Principal principal,
			HttpSession session) {
		
		try {
		String name = principal.getName();
		Users user = userdao.getUsersByUsername(name);
		
		//processing and uploading imagefile...
		
		if(file.isEmpty()) {
			// if the file is empty..
			System.out.println("File is Empty");
			contact.setImage("contact.png");
			
		}
		else {
			// upload the file to folder and update the name to contact...
			contact.setImage(file.getOriginalFilename());
			
			File saveFile = new ClassPathResource("static/img").getFile();
			
			Path path = Paths.get(saveFile.getAbsolutePath()+File.separator+file.getOriginalFilename());
			
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			System.out.println("Image is Uploaded..");
		}
		
		contact.setUser(user);
		
		user.getContacts().add(contact);
		
		userdao.save(user);
		
		
		System.out.println("ContactData"+contact);
		System.out.println("ADD CONTACT SUCCESSFULLY..");
		
		// success message..
		session.setAttribute("message", new Message("Your Contact is added successfully..","success"));
		
		
		
		
		}catch (Exception e) {
			System.out.println("ERROR"+e.getMessage());
			e.printStackTrace();
			
			//Error message..
			session.setAttribute("message", new Message("Something went wrong Try Again..","danger"));

		}
		return "normal/add_contact_form";
	}
	
	// show contacts handler..
	// per page = 5[n]
	// current page = 0[page]
	@GetMapping("/show-contacts/{page}")
	public String showContacts(@PathVariable("page") Integer page, Model model,Principal principal) {
		model.addAttribute("tittle","Show Contacts");
		
		// We have to sent the contact list..
		String userName = principal.getName();
		
		Users users = userdao.getUsersByUsername(userName);
//		List<Contact> contacts = users.getContacts();
		
		//pageable has two function CURRENT PAGE-PAGE,CONTACT PER PAGE - 5
		Pageable pageable = PageRequest.of(page, 10);
		Page<Contact> contacts = contactRepository.findContactsByUser(users.getId(),pageable);
		
		model.addAttribute("contacts", contacts);
		model.addAttribute("currentPage",page);
		
		model.addAttribute("totalPages", contacts.getTotalPages());
		
		return "normal/show_contacts";
	}
	
}
