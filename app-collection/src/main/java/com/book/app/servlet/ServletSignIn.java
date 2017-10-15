package com.book.app.servlet;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.book.app.business.AppServices;
import com.book.app.business.ImageService;
import com.book.app.business.InfAppServices;
import com.book.test.tools.TestEjbHelper;

import entities.Image;
import entities.User;


@MultipartConfig
public class ServletSignIn extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	
	/* referencia por inyección */
	@EJB
	private  AppServices service; 
	
	
	
	@Inject
	private InfAppServices services; 
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
          
		
		String emailUser = request.getParameter("email"); 
		
		
		
		//checkParameters(); 
		User user; 
		
		try{
			user = services.signInUser(emailUser); 
			
	    }catch(EJBException e){
	    	
	    	e.getCausedByException(); 
	    	if(e.getClass().isAssignableFrom(EntityNotFoundException.class)){
	    		//El usuario no existe, responder adecuadamente y/o renviar 
	    		//al signUp
	    		//TODO 
	    		response.setContentType("text/html");
	    		
	    		PrintWriter out=response.getWriter();
	    		
	    		out.println(salidaEmailIncorrecto(emailUser));
	    	}else {
	    		//Error, compruebe los datos del formulario o intentelo mas tarde
	    		response.setContentType("text/html");
	    		PrintWriter out=response.getWriter();
	    		out.println(salidaEmailIncorrecto(emailUser));
	    	}
	    	
	    	return; 
	    }
		
		
		response.setContentType("text/html");
		
		PrintWriter out=response.getWriter();
		
		out.println(salidaEmailIncorrecto("Usuario registrado"));
		
		HttpHelper.saveSessionUser(request, user);
		response.sendRedirect("/app-book/servlet/global");
		
	}
	


	private void checkParameters() {
		// TODO Auto-generated method stub
		
	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	private String salidaEmailIncorrecto(String emailUser){
		String salida="";
		salida= "<html>" 
				+ "<head>"
				+ "<title> Error Login </title>"
				+ "</head>"
				+ "<body>"
				+ "<h1> Error al logear  </h1>";
		
		if(emailUser!=null && !emailUser.equals("")){
			salida = salida + "<p> El "+ emailUser + " no esta registrado</p>";
		}
		else{
			salida = salida +  "<p> La entrada no es valida</p>";
		}
		salida=salida + "<a href='/app-book/index.html'>Volver a Login</a>";
		salida=salida + "</body> </html>";
		return salida;
	}
	
	


}
