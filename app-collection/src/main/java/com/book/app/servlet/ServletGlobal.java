package com.book.app.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.ArrayList;

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

import com.book.app.business.AppServices;
import com.book.app.business.ImageService;
import com.book.app.business.InfAppServices;

import entities.Collection;
import entities.Image;
import entities.User;

public class ServletGlobal extends HttpServlet{
	private static final long serialVersionUID = 1L;
       
	
	
	
	@EJB
	private  AppServices service; 
	
	@Inject
	private InfAppServices services; 
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		
		String type = request.getParameter("type");
		//si hay una colleccion activa
		String idCollection = request.getParameter("idColection");
		
		User user = HttpHelper.getSessionUser(request);
		
		if(user==null){
			response.sendRedirect("/app-book/index.html");
			return;
		}else if(type!=null && type.equals("singOut")){
			HttpHelper.deleteSessionUser(request);
			response.sendRedirect("/app-book/index.html");
		}else if(type!=null && type.equals("newCollection")){;
			response.sendRedirect("/app-book/servlet/ServletCreateCollection");
		}else if(type!=null && type.equals("remove") && idCollection!=null){
			removeCollecction(idCollection);
			response.sendRedirect("/app-book/servlet/global");
		}else if(idCollection==null){
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			out.println(paginaGlobal(request,"-1",services));
		}else{
			response.setContentType("text/html");
			PrintWriter out=response.getWriter();
			out.println(paginaGlobal(request,idCollection,services));
		}
		
		
	}
	





	protected void doPost(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		doGet(request, response);
	}
	
	
	private String paginaGlobal(HttpServletRequest request, String idCollection,InfAppServices services){
		String salida="";
		
		
		salida="<!DOCTYPE html>"
				+ "<html>"
		        + "<head>"
		        + "<title>Global</title>"
		       + "<meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>"
		      +  "</head>"
		      + "<style>"
			  + "body{ background-color:#FFC;}"
			  + "#arribaIzq{position: absolute;top:2%;left:2%;padding:10px 10px 10px 50px;background-color:#F93;"	
			  			+ "box-shadow: 10px 10px 5px 0px #999999;border-radius: 10px;width: 18;align:left;text-align: left;}"
			
 			+ "#arribaDerecha{position: absolute;top:3%;left:53%;padding:10px 10px 10px 50px;background-color:#F93;	"
				+ "box-shadow: 10px 10px 5px 0px #999999;border-radius: 10px;width: 30%;align:right;text-align: center;}"
 			+"#abajoIzquierda{position: absolute;top:15%;left:2%;padding:10px 10px 10px 50px;background-color:#F93;"	
				+ "box-shadow: 10px 10px 5px 0px #999999;border-radius: 10px;width: 20%;height: 80%;align:left;text-align: center;overflow: auto;}"
			+ "#abajoDerecha{position: absolute;top:15%;left:40%;padding:10px 10px 10px 50px;background-color:#F93;	box-shadow: 10px 10px 5px 0px #999999;"
			+"border-radius: 10px;width: 55%;height: 80%;align:left;text-align: center;overflow: auto;}"
			+ ".botonImagen{background-image:url('../img/Editar.png');background-repeat:no-repeat;height:55px;width:58px;background-position:center;}"
			+ ".borrarImagen{background-image:url('../img/borrar.png');background-repeat:no-repeat;height:55px;width:58px;background-position:center;}"
			+ ".anadirImagen{background-image:url('../img/anadir.png');background-repeat:no-repeat;height:55px;width:58px;background-position:center;}"
			+ ".botonTamany{height:50px;width:200px;background-position:center;color: #003366; background-color: #99CCFF}"
			+ "h1{text-align:center;} </style>"
		   + "<body>"
				+"<div id=arribaIzq>"
					+ "<label>" + HttpHelper.getEmail(request) + "</label>"
					+ "<br>"
					+ "<br>"
					+ "<button onclick='signOut()' class='botonTamany'>Sign Out</button>" 
				+ "</div>";
		
			if(!idCollection.equals("-1")){
				
				salida= salida + "<div id=arribaDerecha>"
					+ "<label>"+ getNombreCollection(idCollection,services) + "<label>"
					+ "<button onclick='update("+idCollection+")' class='botonImagen'></button>"
					+ "<button onclick='borrar("+idCollection+")' class='borrarImagen'></button>"	
				+ "</div>";
			}
			
			salida = salida + "<div id=abajoIzquierda>";
			salida = salida + textoCollecciones(request);
			
			salida= salida	
					 + "<br>"
					 + "<br>"
					 + "<label> Adiccionar Coleccion </label>"
					 + "<button onclick='nuevaColeccion()' class='anadirImagen'></button>"
				+ "</div>";
			
			if(!idCollection.equals("-1")){
				salida = salida + "<div id=abajoDerecha>"
						+ "<label> Titulo </label>"
					    +"<br>"
						+"<label> Descripcion </label>"
						+"<img src='../img/borrar.png' align='center'>"
						+ "<br>"
						+ "<button onclick='myFunction()' class='botonImagen'></button>"
						+ "<button onclick='myFunction()' class='borrarImagen'></button>"
						+ "<br>"
						+ "<br>"
						+ "<br>"
						+ "<label> Titulo </label>"
						+ "<br>"
						+ "<label> Descripcion </label>"
						+ "<img src='../img/Editar.png' align='center' alt='error al cargar imagen'>"
						+ "<br>"
						+ "<button onclick='myFunction()' class='botonImagen'></button>"
						+ "<button onclick='myFunction()' class='borrarImagen'></button>"
						+ "<br>"
						+ "<br>"
						+ "<br>"
						+ "<label> Adiccionar Item </label>"
						+ "<button onclick='addItem("+idCollection+")' class='anadirImagen'></button>"
				+ "</div>";
			}
			salida = salida + "</body>"
			
			+ "<script>"
				+ "function myFunction() {"
					+ "window.location='http://www.google.com';"
					+ "}"
				+ "function signOut() {"
					+ "alert('Salistes del usuario');"
					+ "window.location='/app-book/servlet/global?type=singOut';"
					+ "}"
				+ "function nuevaColeccion() {"
				+ "alert('nuevaColeccion');"
					+ "window.location='/app-book/servlet/global?type=newCollection';"
					+ "}"
				+ "function seleccionarColeccion(entrada) {"
							+ "window.location='/app-book/servlet/global?idColection='+entrada;"
							+ "}"
							
				+ "function borrar(entrada) {"
				+ "window.location='/app-book/servlet/global?type=remove&&idColection='+entrada;"
				+ "}"
				
				+ "function update(entrada) {"
					+ "window.location='/app-book/servlet/ServletCreateCollection?idColection='+entrada;"
				+ "}"
				
				+ "function addItem(entrada) {"
					+ "window.location='/app-book/servlet/ServletCreateItem?idCollection='+entrada;"
					+ "}"
			+ "</script>"
		+ "</html>";
		
		return salida;
	}
	
	private String textoCollecciones(HttpServletRequest request){
		String salida="";
		User result = services.find(User.class, HttpHelper.getId(request));  
		ArrayList<Collection> list =  new ArrayList<>(result.getCollections()); 
		if(list.size()==0){
			System.out.println("vacia");
		}
		for(int i=0;i<list.size();i++){
			salida=salida+ 
					 " <button onclick='seleccionarColeccion("+list.get(i).getId()+")' class='botonTamany'>"+ list.get(i).getName() +"</button><br>";
		}
		return salida;
	}
	
	private String getNombreCollection(String idCollection,InfAppServices services){
		String nombreCollection="";
		try{
		Collection collection=services.find(Collection.class, idCollection);
		return collection.getName();
		}
		catch(Exception e){
			nombreCollection="Error en collection";
			return nombreCollection;
		}
	}
	

	private void removeCollecction(String idCollection) {
		try{
			services.removeCollection(idCollection);
		}
		catch(Exception e){
			System.out.println("Error al eliminar collecction");
		}
	}
	
}