package com.book.app.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.app.business.AppServices;

import entities.User;

public class ServletSignIn extends HttpServlet {
	/* referencia por inyección */
	@EJB
	private AppServices service; 
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		/*User user= Htttp.getSession(request);
		if(user != null){*/
		
		String emailUser=request.getParameter("email");
		checkParameters();
		
		User user;
		
		try{
			user= service.signInUser(emailUser);
			
		}catch(EJBException e){
			e.getCausedByException();
			if(e.getClass().isAssignableFrom(EntityNotFoundException.class)){
				//El usuario no existe, responder adecuadamente y/o renviar al signUP
				//TODO
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
