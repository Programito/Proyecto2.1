package com.book.test.tools;

import entities.Collection;
import entities.Image;
import entities.Item;
import entities.User;

public class MockHelper {
	
	public static final String TEST_USER_EMAIL = "qbit.player@gmail.com";
	
	
	 
    public static  User mockUser(String userName, String email){ 
   	 User user = new User();
   	 user.setName(userName); 
   	 user.setEmail(email);     	 
   	 return user; 
    }
     
    public static Collection mockCollection(String nameCollection){
	   	  Collection collection = new Collection();
		  collection.setDescription("Esta es una coleccion de test"); 
		  collection.setName(nameCollection); 
		  return collection; 
    }
    
    
    public static Item mockItem(String nameItem){
    	  Item item = new Item();
		  item.setDescription("Esta es una coleccion de test"); 
		  item.setName(nameItem); 
		  return item; 
    }
    
    
    public static Image mockImage(){ 
    	byte[] bytImage = new byte[]{1,2,3,4,5,6,7,8,9}; 
    	Image image = new Image(); 
    	image.setBytes(bytImage); 
    	return image; 
    }
    
    
    

}
