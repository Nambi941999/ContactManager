package com.example.demo.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
//@Table(name = "CONTACT")
public class Contact {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int contact_id;
	private String firstName;
	private String lastName;
	private String work;
	private String email;
	private String phone;
	private String image;
	@Column(length = 1000)
	private String description;
	
	@ManyToOne
	private Users users;

	
	public Contact() {
		// TODO Auto-generated constructor stub
	}


//	public Contact(String firstName, String lastName, String work, String email, String phone, String image,
//			String description) {
//		super();
//		this.firstName = firstName;
//		this.lastName = lastName;
//		this.work = work;
//		this.email = email;
//		this.phone = phone;
//		this.image = image;
//		this.description = description;
//	}


	public int getId() {
		return contact_id;
	}


	public void setId(int contact_id) {
		this.contact_id = contact_id;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public String getWork() {
		return work;
	}


	public void setWork(String work) {
		this.work = work;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPhone() {
		return phone;
	}


	public void setPhone(String phone) {
		this.phone = phone;
	}


	public String getImage() {
		return image;
	}


	public void setImage(String image) {
		this.image = image;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public Users getUser() {
		return users;
	}


	public void setUser(Users user) {
		this.users = user;
	}


//	@Override
//	public String toString() {
//		return "Contact [contact_id=" + contact_id + ", firstName=" + firstName + ", lastName=" + lastName + ", work="
//				+ work + ", email=" + email + ", phone=" + phone + ", image=" + image + ", description=" + description
//				+ ", users=" + users + "]";
//	}
//	
	
	
	
	
}
