package com.book.test;

import java.util.ArrayList;

import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.book.app.business.InfAppServices;
import com.book.test.tools.MockHelper;
import com.book.test.tools.TestEjbHelper;

import entities.Collection;
import entities.Item;
import entities.User;

public class ItemCollectionTest {
	
	
	private static final String TEST_USER_EMAIL = "qbit.player@gmail.com";
	
	@Inject
	private InfAppServices service; 

   @Before
    public void before() throws NamingException{   
    	EJBContainer ejbContainer = TestEjbHelper.getEjbContainer();  	
    	ejbContainer.getContext().bind("inject", this); 
    	service.removeAll(User.class); 
    }
   
   
  

     @Test
     public void  addItem(){
    	  User user = MockHelper.mockUser("User Test",MockHelper.TEST_USER_EMAIL);     	 
    	  service.signUpUser(user); 
    	  
    	  Collection collection = MockHelper.mockCollection("Collection test"); 
		  service.addCollection(user.getId(),collection); 
		  
		  
		  Item item = MockHelper.mockItem("Collection test"); 
		  
		  
		  service.addItem(collection.getId(),item,null); 
		  
		  
		  Collection result = service.find(Collection.class, collection.getId());  
		  
		  Assert.assertEquals(1, result.getItems().size()); 
		  ArrayList<Item> list =  new ArrayList<>(result.getItems()); 		  
		  Assert.assertEquals(item.getName(),list.get(0).getName() ); 
     }
     
	
}
