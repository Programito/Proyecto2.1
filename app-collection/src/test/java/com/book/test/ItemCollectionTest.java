package com.book.test;

import java.util.ArrayList;
import java.util.Set;

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
		  
		  
		  Item item = MockHelper.mockItem("Item test"); 
		  
		  
		  service.addItem(collection.getId(),item,null); 
		  item.setCollection(collection);
		  
		  Collection result = service.find(Collection.class, collection.getId());  
		  
		  Assert.assertEquals(1, result.getItems().size()); 
		  ArrayList<Item> list =  new ArrayList<>(result.getItems()); 		  
		  Assert.assertEquals(item.getName(),list.get(0).getName() ); 
     }
     
     
     //NO FUNCIONA!!!!
     @Test
     public void  removeItem(){
    	 User user = MockHelper.mockUser("User Test",MockHelper.TEST_USER_EMAIL);    
    	 service.signUpUser(user);
    	 Collection collection = MockHelper.mockCollection("Collection test"); 
		 service.addCollection(user.getId(),collection); 
		 collection.setUser(user);
    	 
    	 
    	 Item item = MockHelper.mockItem("Item test"); 
    	 Item item2 = MockHelper.mockItem("Item test 2");
    	 
    	 service.addItem(collection.getId(),item,null); 
    	 service.addItem(collection.getId(),item2,null);
    	 
    	 item.setCollection(collection);
    	 
    	 Collection result2 = service.find(Collection.class, collection.getId());
    	  Assert.assertEquals(2, result2.getItems().size()); 
    	 
    	 
    	 //Assert.assertEquals(2,collection.getItems().size());
    	 
		  service.removeItem(item.getId()); 
		  
		  Collection result = service.find(Collection.class, collection.getId()); 
		  
		  //Assert.assertEquals(1,result.getCollections().size());
	    	
		  Assert.assertNotNull(collection); 	
		  Set<Item> list = result.getItems();  		  
		  Assert.assertEquals(1,list.size()); 
		  
		  for(Item i:list){
			  System.out.println(i.getName());
		  }
		  
		  
		  Item itemI = service.find(Item.class, item.getId()); 
		  Assert.assertNull(itemI); 	 
		  
     }
     
     
     @Test
     public void  updateItem(){
    	 
    	  User user = MockHelper.mockUser("User Test",MockHelper.TEST_USER_EMAIL);     	 
    	  service.signUpUser(user); 
    	  
    	  Collection collection = MockHelper.mockCollection("Collection test"); 
		  service.addCollection(user.getId(),collection); 
		  
		  
		  Item item = MockHelper.mockItem("Item test"); 
		  
		  
		  service.addItem(collection.getId(),item,null); 
		  item.setCollection(collection);
		 
    	 
    	  Item editedItem = new Item(); 
   	  	  editedItem.setId(item.getId()); 
   	  	  editedItem.setName("Name edited"); 
   	  	  editedItem.setDescription("Description edited");
   	  	  byte[] bytes=null;
   	  	  service.updateItem(editedItem,bytes);
    	
   	  	  Item result  = service.find(Item.class,editedItem.getId()); 
   	  	 
   	  	  Assert.assertEquals("Name edited",result.getName());
   	  	  Assert.assertEquals("Description edited",result.getDescription());
		
     }
     
     
}
