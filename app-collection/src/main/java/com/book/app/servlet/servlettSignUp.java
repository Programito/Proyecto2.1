package com.book.app.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.persistence.EntityExistsException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.book.app.business.AppServices;


import entities.Image;
import entities.User;

public class servlettSignUp extends HttpServlet {
	/* referencia por inyección */
	@EJB
	private AppServices service; 
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String messageResponse="";
		String email=request.getParameter("email");
		String name=request.getParameter("name");
		
		checkParameters();
		
		User user= new User();
		user.setEmail(email);
		user.setName(name);
		try{
			service.signUpUser(user);
		}catch(EJBException e){
			e.getCausedByException();
			if(e.getClass().isAssignableFrom(EntityExistsException.class)){
				//El usuario ya existe, responder adecuadamente
				
			}else{
				//Error compruebe los datos del formulario o intentelo mas tarde
			}
			return;
		}
		
		HttpHelper.saveSessionUser(request, user);
		response.sendRedirect("/ServletHOme");
	}
	






	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	private void checkParameters() {
		// TODO Auto-generated method stub
		
	}
	

}