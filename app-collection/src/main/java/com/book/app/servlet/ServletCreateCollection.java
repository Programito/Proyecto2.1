package com.book.app.servlet;

import java.io.IOException;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.persistence.EntityExistsException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.app.business.AppServices;

import entities.Collection;
import entities.User;

public class ServletCreateCollection extends HttpServlet {
	/* referencia por inyección */
	@EJB
	private AppServices service; 
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		
		//Collection collection=new Collection();
		Collection collection= HttpHelper.getParameterCollection(request);
		
		checkParameters();
		User user=HttpHelper.getSessionUser(request);
		if(user==null){
			response.sendRedirect("signIn.html");
		}
		
		
		try{
			service.addCollection(user.getId(), collection);
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
	






	private void checkParameters() {
		// TODO Auto-generated method stub
		
	}







	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}
}