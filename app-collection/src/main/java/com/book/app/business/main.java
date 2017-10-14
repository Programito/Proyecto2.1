package com.book.app.business;

import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJBException;
import javax.ejb.embeddable.EJBContainer;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import com.book.test.tools.TestEjbHelper;

import entities.User;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		AppServices service=new AppServices();
		EJBContainer ejbContainer = TestEjbHelper.getEjbContainer();  	 
		User user=new User();
		user.setEmail("paco@jose.com");
		service.signUpUser(user);
		
		
	}

}
