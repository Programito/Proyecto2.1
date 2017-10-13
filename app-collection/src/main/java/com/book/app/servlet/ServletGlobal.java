package com.book.app.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.book.app.business.ImageService;
import com.book.app.business.InfAppServices;

import entities.Image;
import entities.User;

public class ServletGlobal extends HttpServlet{
	private static final long serialVersionUID = 1L;
       
	
	
	
	@Inject
	private InfAppServices service;
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
           String messageResponse="";
		   String option = request.getParameter("option");  
		   
		   
		   if(option.equals("signin")){
			   String email = request.getParameter("email");  
			   service.signInUser(email);
			   HttpSession httpSession = request.getSession(true);
		   }
		   else if(option.equals("signup")){
			   String email= request.getParameter("email");  
			   String nombre=request.getParameter("nombre");
			   User user=new User();
			   user.setEmail(email);
			   user.setName(nombre);
			   service.signUpUser(user);
		   }
		   else if(option.equals("insert")){			  
		   		  //Enviar a ServletServerImages   
		   }
		   else if(option.equals("nuevacoleccion")){
			   
		   }
		   
		
		   
		 response.setContentType("text/html");
	        PrintWriter out = response.getWriter();
	        out.println("<html>");
	        out.println("<head>");
	        out.println(HttpHelper.getStyleTable()); 
	        out.println("<title>Insert Image</title>");
	        out.println("</head>");
	        out.println("<body>");
	       
	        out.println("<p>");    
			out.println(messageResponse);
	        out.println("<p>");    
	        
	        out.println("<p>");    
	        	out.println("<a href=../index.html> Menu </a>");
	        out.println("<p>");
	        out.println("</body>");
	        out.println("</html>");
	}
	


	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}
	

	
	
	private static byte[] inputStreamToByte(InputStream is){
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		int nRead;
		byte[] data = new byte[16384];

		try {
			while ((nRead = is.read(data, 0, data.length)) != -1) {
			  buffer.write(data, 0, nRead);
			}
			
			buffer.flush();
		} catch (IOException e) {
			
		}

		return buffer.toByteArray();
	}
	
	private static String nuevaColeccion(){
		return "<!DOCTYPE html>"
				+ "<html>"
				+ "<head>"
				+ "<title>Acceso</title>"
				+ "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>"
				+ "</head>"
				+ "<body>"
				+ "<h1>Nueva Coleccion</h1>"
				+ "<form action='/servlet/global?option=signin' method='post'>"
				+ "Nombre: <input type='text' name='Nombre'><br>"
				+ "Descripcion: <input type='text' name='Descripcion' ><br>"
				+ "<input type='submit' value='Crear Coleccion'>"
				+ "</form>"		
		         + "</body>"
		        + "</html>";
	}
	
	/*private  Servicios getStatefulServicio(HttpServletRequest request)
	         throws ServletException {
		
	     HttpSession httpSession = request.getSession(true);
	     Servicios statefulTestBean = 
	             (Servicios) httpSession.getAttribute(STATEFUL_CLICK_BEAN_KEY);
	     
	     
	     if (statefulTestBean == null) {
	         try {
	        	 
	             InitialContext ic = new InitialContext();
	             statefulTestBean =   (Servicios) ic.lookup("java:module/Servicios");
	             httpSession.setAttribute(STATEFUL_CLICK_BEAN_KEY, statefulTestBean);	          	       
	         } catch (NamingException e) {
	             throw new ServletException(e);
	         }
	     }
	     return statefulTestBean;
	 }*/
	
	


}