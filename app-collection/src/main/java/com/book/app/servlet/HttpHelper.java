package com.book.app.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import entities.Collection;
import entities.Item;
import entities.User;

public class HttpHelper {

	

	
	private static final String USER_ID = "user_id";
	private static final String USER_EMAIL = "user_email";
	private static final String USER_NAME = "user_name";
	private static final String COLLECTION_ID = "collection_id";


	public static String getStyleTable(){
		return "<style>" 
		+"table {" 
		+"    font-family: arial, sans-serif;"
		+"    border-collapse: collapse;"
		+"    width: 100%;"
		+"}"

		+"td, th {"
		+"    border: 1px solid #dddddd;"
		+ "   text-align: left;"
		+ "   padding: 8px;"
		+"}"

		+"tr:nth-child(even) {"
		+"    background-color: #dddddd;"
		+"}"
		+"</style>"; 
	}
	
	
	
	public static void deleteSessionUser(HttpServletRequest request){
		HttpSession session = request.getSession(); 	
		session.setAttribute(USER_EMAIL, null); 
		session.setAttribute(USER_ID, null); 
		session.setAttribute(USER_NAME,null); 		
	}
	
	public static void saveSessionUser(HttpServletRequest request, User user){
		HttpSession session = request.getSession(); 		
		session.setAttribute(USER_ID, user.getId()); 
		session.setAttribute(USER_EMAIL, user.getEmail()); 
		session.setAttribute(USER_NAME, user.getName()); 	
	}
	
	
	public static User getSessionUser(HttpServletRequest request){
		HttpSession session = request.getSession(); 		
		String id = (String) session.getAttribute(USER_ID); 
		String email = (String) session.getAttribute(USER_EMAIL); 
		String name = (String) session.getAttribute(USER_NAME); 
		
		User user =null; 
		
		if(name!=null && id!=null && email!=null){	
			user = new User();
			user.setId(id);
			user.setName(name);
			user.setEmail(email); 
		}
		
		return user;  
	}
	
	public static String getEmail(HttpServletRequest request){
		HttpSession session = request.getSession(); 	
		String salida=session.getAttribute(USER_EMAIL).toString();	
		return salida;
	}
	
	public static String getId(HttpServletRequest request){
		HttpSession session = request.getSession(); 	
		String salida=session.getAttribute(USER_ID).toString();	
		return salida;
	}



	public static Collection getParametesCollection(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return null; 
	}
	
	public static void saveSessionCollection(HttpServletRequest request, String idCollection){
		HttpSession session = request.getSession(); 		
		session.setAttribute(COLLECTION_ID, idCollection);  	
	}
	
	public static void deleteSessionCollection(HttpServletRequest request){
		HttpSession session = request.getSession(); 	
		session.setAttribute(COLLECTION_ID, null); 
	}
	
	public static String getCollectionId(HttpServletRequest request){
		HttpSession session = request.getSession(); 
		String salida=null;
		if(COLLECTION_ID!=null){
			salida=session.getAttribute(COLLECTION_ID).toString();	
		}
		return salida;
	}
	
}
