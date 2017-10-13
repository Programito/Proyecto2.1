package com.book.test;

import java.util.ArrayList;
import java.util.List;
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
import entities.Image;
import entities.Item;
import entities.User;

public class ItemCollectionTest {

	private static final String TEST_USER_EMAIL = "qbit.player@gmail.com";

	@Inject
	private InfAppServices service;

	@Before
	public void before() throws NamingException {
		EJBContainer ejbContainer = TestEjbHelper.getEjbContainer();
		ejbContainer.getContext().bind("inject", this);
		service.removeAll(User.class);
	}

	//Sin imagen
	@Test
	public void addItem() {
		User user = MockHelper.mockUser("User Test", MockHelper.TEST_USER_EMAIL);
		service.signUpUser(user);

		Collection collection = MockHelper.mockCollection("Collection test");
		service.addCollection(user.getId(), collection);

		Item item = MockHelper.mockItem("Item test");

		service.addItem(collection.getId(), item, null);
		// item.setCollection(collection);

		Collection result = service.find(Collection.class, collection.getId());

		Assert.assertEquals(1, result.getItems().size());
		ArrayList<Item> list = new ArrayList<>(result.getItems());
		Assert.assertEquals(item.getName(), list.get(0).getName());
	}

	@Test
	public void addItemWidBytes() {
		User user = MockHelper.mockUser("User Test", MockHelper.TEST_USER_EMAIL);
		service.signUpUser(user);

		Collection collection = MockHelper.mockCollection("Collection test");
		service.addCollection(user.getId(), collection);

		Item item = MockHelper.mockItem("Item test");
		Image image = MockHelper.mockImage();

		service.addItem(collection.getId(), item, image.getBytes());
		// item.setCollection(collection);

		Collection result = service.find(Collection.class, collection.getId());

		Assert.assertEquals(1, result.getItems().size());
		ArrayList<Item> list = new ArrayList<>(result.getItems());
		Assert.assertEquals(item.getName(), list.get(0).getName());
		Assert.assertArrayEquals(item.getImage().getBytes(), image.getBytes());
	}

	
	@Test
	public void removeItem() {
		User user = MockHelper.mockUser("User Test", MockHelper.TEST_USER_EMAIL);
		Collection collection = MockHelper.mockCollection("Collection test");
		// service.addCollection(user.getId(),collection);
		user.getCollections().add(collection);
		collection.setUser(user);

		Item item = MockHelper.mockItem("Item test");
		Item item2 = MockHelper.mockItem("Item test 2");

		collection.getItems().add(item);
		item.setCollection(collection);
		collection.getItems().add(item2);
		item2.setCollection(collection);

		service.signUpUser(user);

		Collection result2 = service.find(Collection.class, collection.getId());
		Assert.assertEquals(2, result2.getItems().size());

		service.removeItem(item.getId());

		Collection result = service.find(Collection.class, collection.getId());

		Assert.assertNotNull(collection);
		Set<Item> list = result.getItems();
		Assert.assertEquals(1, list.size());

		Item itemI = service.find(Item.class, item.getId());
		Assert.assertNull(itemI);

	}
	
	
	
	@Test
	public void removeItemWithImage() {
		User user = MockHelper.mockUser("User Test", MockHelper.TEST_USER_EMAIL);
		Collection collection = MockHelper.mockCollection("Collection test");
		// service.addCollection(user.getId(),collection);
		user.getCollections().add(collection);
		collection.setUser(user);

		Item item = MockHelper.mockItem("Item test");
		Image image = MockHelper.mockImage();
		image.setUrl("TestURL"); 
		
		collection.getItems().add(item);
		item.setCollection(collection);		
		item.setImage(image); 
		
	
		Item item2 = MockHelper.mockItem("Item test 2");
		collection.getItems().add(item2);
		item2.setCollection(collection);

		service.signUpUser(user);

		Collection result2 = service.find(Collection.class, collection.getId());
		Assert.assertEquals(2, result2.getItems().size());

	
		service.removeItem(item.getId());

		Collection result = service.find(Collection.class, collection.getId());

		Assert.assertNotNull(collection);
		Set<Item> list = result.getItems();
		Assert.assertEquals(1, list.size());

		
		Item itemI = service.find(Item.class, item.getId());
		Assert.assertNull(itemI);
		
		
		 List<Image> resultImage = service.getAll(Image.class);  
		 Assert.assertNotNull( resultImage);
		 Assert.assertEquals(0,  resultImage .size());
		
	

	}
	

	//Sin imagen y no pones sin imagen
	@Test
	public void updateItem() {

		User user = MockHelper.mockUser("User Test", MockHelper.TEST_USER_EMAIL);
		Collection collection = MockHelper.mockCollection("Collection test");
		// service.addCollection(user.getId(),collection);
		user.getCollections().add(collection);
		collection.setUser(user);

		Item item = MockHelper.mockItem("Item test");
		collection.getItems().add(item);
		item.setCollection(collection);

		service.signUpUser(user);

		Item editedItem = new Item();
		editedItem.setId(item.getId());
		editedItem.setName("Name edited");
		editedItem.setDescription("Description edited");

		service.updateItem(editedItem, null);

		Item result = service.find(Item.class, editedItem.getId());

		Assert.assertEquals("Name edited", result.getName());
		Assert.assertEquals("Description edited", result.getDescription());

	}
	
	
	
	//Sin imagen previo y con imagen
	@Test
	public void updateItemWithImagen() {

		User user = MockHelper.mockUser("User Test", MockHelper.TEST_USER_EMAIL);
		Collection collection = MockHelper.mockCollection("Collection test");
		// service.addCollection(user.getId(),collection);
		user.getCollections().add(collection);
		collection.setUser(user);

		Item item = MockHelper.mockItem("Item test");
		collection.getItems().add(item);
		item.setCollection(collection);

		service.signUpUser(user);

		Item editedItem = new Item();
		editedItem.setId(item.getId());
		editedItem.setName("Name edited");
		editedItem.setDescription("Description edited");
		
		byte[] bytImage = new byte[]{1,2,3,4,5,6,7,8,9}; 

		service.updateItem(editedItem, bytImage);

		Item result = service.find(Item.class, editedItem.getId());

		Assert.assertEquals("Name edited", result.getName());
		Assert.assertEquals("Description edited", result.getDescription());
		Assert.assertArrayEquals(bytImage, result.getImage().getBytes());

	}
	
	//Con imagen previo y con imagen despues
	@Test
	public void updateItemWith2Imagen() {

		User user = MockHelper.mockUser("User Test", MockHelper.TEST_USER_EMAIL);
		Collection collection = MockHelper.mockCollection("Collection test");
		user.getCollections().add(collection);
		collection.setUser(user);

		Item item = MockHelper.mockItem("Item test");
		Image image = MockHelper.mockImage();
		image.setUrl("TestURL"); 
		
		collection.getItems().add(item);
		item.setCollection(collection);
		item.setImage(image); 

		service.signUpUser(user);

		Item editedItem = new Item();
		editedItem.setId(item.getId());
		editedItem.setName("Name edited");
		editedItem.setDescription("Description edited");
		
		byte[] bytImage = new byte[]{2,3,4,5,6,7,8,9,0}; 

		service.updateItem(editedItem, bytImage);

		Item result = service.find(Item.class, editedItem.getId());

		Assert.assertEquals("Name edited", result.getName());
		Assert.assertEquals("Description edited", result.getDescription());
		Assert.assertNotEquals(image.getBytes()[1], result.getImage().getBytes()[1]);
		Assert.assertEquals(bytImage[1], result.getImage().getBytes()[1]);
	}	
}
