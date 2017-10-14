package com.book.test;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.embeddable.EJBContainer;
import javax.inject.Inject;
import javax.naming.NamingException;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.book.app.business.AppServices;
import com.book.app.business.ImageService;
import com.book.app.business.InfAppServices;
import com.book.test.tools.MockHelper;
import com.book.test.tools.TestEjbHelper;

import entities.User;

public class UserServicesTest {
	
	
	@Inject
	private InfAppServices service; 
 


   @Before
    public void before() throws NamingException{   
    	EJBContainer ejbContainer = TestEjbHelper.getEjbContainer();  	
    	ejbContainer.getContext().bind("inject", this);    	
    	service.removeAll(User.class);	
    }
        
     
     @Test
     public void  signUpTestOK(){
    	 
    	 User user = MockHelper.mockUser("User Test",MockHelper.TEST_USER_EMAIL);     	 
    	 service.signUpUser(user);     	   	 
    	  List<User> list = service.getAll(User.class);  
    	  Assert.assertEquals(1, list.size());         
     }
     
     
     
     @Test(expected=EntityExistsException.class)
     public void  signUpTestAlreadyRegistered() throws Exception{
    	 
    	 try{
		 User user = MockHelper.mockUser("User Test",MockHelper.TEST_USER_EMAIL);     	 
    	 service.signUpUser(user); 
    	  	 
    	 User user2 = new User(); 
    	 user2.setName("Clone user"); 
    	 user2.setEmail(MockHelper.TEST_USER_EMAIL);  
    	 service.signUpUser(user2);
    	 }catch (EJBException e) {
    		 Exception caused = e.getCausedByException(); 
    		 throw caused;   		 
		}
     }
     
     
     @Test(expected=IllegalArgumentException.class)
     public void  signUpTestBadEmail() throws Exception{
    	 
    	 try{
    	 User user = MockHelper.mockUser("User Test",MockHelper.TEST_USER_EMAIL);  
    	 user.setEmail(null); 
    	 service.signUpUser(user); 
    
    	 }catch (EJBException e) {
    		 Exception caused = e.getCausedByException(); 
    		 throw caused;   		 
		}
     }
     
     
     
     @Test
     public void  signInTestOK(){
    	 
    	 User user = MockHelper.mockUser("User Test",MockHelper.TEST_USER_EMAIL);
    	 service.signUpUser(user);    
    	 
    	 
    	 User result = service.signInUser(user.getEmail());  
    	 Assert.assertNotNull(result);    
    	 Assert.assertEquals(user.getId(), result.getId()); 
    	 Assert.assertEquals(user.getEmail(), result.getEmail()); 
    	 Assert.assertEquals(user.getName(), result.getName()); 
     }
   
   
     
     @Test(expected=EntityNotFoundException.class)
     public void  signInTestEmailNotFound() throws Exception{
    	 
    	 try{
     
    	 User user = service.signInUser("test@test.com");  
    
    	 }catch (EJBException e) {
    		 Exception caused = e.getCausedByException(); 
    		 throw caused;   		 
		}
     }
	    

}
