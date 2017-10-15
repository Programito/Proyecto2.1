package com.book.app.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.inject.Inject;
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

import entities.Collection;
import entities.Image;
import entities.User;


@MultipartConfig
public class ServletCreateCollection extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	
	
	/* referencia por inyección */
	@EJB
	private  AppServices service; 
	
	@Inject
	private InfAppServices services; 
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
          
		
		User user = HttpHelper.getSessionUser(request); 
		if(user==null){
			response.sendRedirect("/app-book/index.html");
			return; 
		}
		/*
		//Collection collection = HttpHelper.getParametesCollection(request); 		   
		//checkParameters(); 
		String idCollection=HttpHelper.getCollectionId(request);
		if(idCollection==null){
			System.out.println("crear nueva collecion");
			String name = request.getParameter("name");
			String description = request.getParameter("description");
			System.out.println("Name:" + name);
			System.out.println("description:" + description);
				if(name!=null && description!=null){
					Collection collection= new Collection();
					try{
						services.addCollection(user.getId(), collection);
					}catch(EJBException e){
							System.out.println("error");
							response.sendRedirect("/app-book/servlet/ServletCreateCollection");
							return; 
						}
				}	
			}else{
				System.out.println("Modificar collecion");
			}*/
		
		/*HttpHelper.saveSessionUser(request, user);
		response.sendRedirect("/ServletHome");*/
		

		String id=request.getParameter("id");
		String description = request.getParameter("description");
		String name = request.getParameter("name");
		if(id==null && description!=null && name!=null){
			Collection collection= new Collection();
			collection.setName(name);
			collection.setDescription(description);
			System.out.println("funciona hasta aqui");
			try{
				services.addCollection(user.getId(), collection);
				response.sendRedirect("/app-book/servlet/global");
			}catch(EJBException e){
					System.out.println("error");
					response.sendRedirect("/app-book/servlet/ServletCreateCollection");
					return; 
			}
		}
		
		
		
		response.setContentType("text/html");
		
		PrintWriter out=response.getWriter();
		
		out.println(htmlAddCollection());
		
	}
	


	private void checkParameters() {
		// TODO Auto-generated method stub
		
	}



	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	private String htmlAddCollection(){
		String salida="";
	
		
		salida="<!DOCTYPE html>"
		+ "<html>"
		    +"<head>"
		        + "<title>Nueva Coleccion</title>"
		        + "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>"
		   + " </head>"
			
			+ "<style>"

			+ "body{background-color:#FFC;}"
			+ "#principal{position: absolute;top:38%;left:38%;padding:10px 10px 10px 50px;background-color:#F93;box-shadow: 10px 10px 5px 0px #999999;border-radius: 10px;width: 20%;"
			+ "align:center;text-align: center;}"
			+ "h1{text-align:center;}"


			+ "</style>"
		   + "<body>"
				+"<div id=principal>"
		        + "<h1>Nueva Coleccion</h1>"
				+ "<form action='/app-book/servlet/ServletCreateCollection' method='post'>"
					+ "Nombre: <input type='text' required='required' name='name'><br>"
					+ "Descripcion: <input type='text' required='required' name='description'><br>"
					+ "<input type='submit' value='Crear Coleccion'>"
				+ "</form>"	
				+ "<br>"
				+ "<a href='/app-book/servlet/global'>Volver </a>"
				+ "</div>"
			+ "</body>"
		+ "</html>";
		
		
		return salida;
	}
	
	


}