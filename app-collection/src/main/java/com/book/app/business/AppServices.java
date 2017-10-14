package com.book.app.business;

import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

import entities.Collection;
import entities.Image;
import entities.Item;
import entities.User;

@Stateless
@SuppressWarnings("unchecked")
public class AppServices implements InfAppServices  {

	@PersistenceContext(unitName = "persistence-unit" )
    private EntityManager entityManager;
	
	
	@Override
	public void signUpUser(@NotNull User user) {

		 if(user==null || user.getEmail()==null){
			 throw new IllegalArgumentException("> "
			 		+ "El parametro User no debe ser null y debe tener un email valido "); 
		 }		
		 //TODO verificar formato correo 
		List<User> list = entityManager.createNamedQuery(User.QUERY_USER_BY_EMAIL) 
    			.setParameter("email",user.getEmail()).getResultList(); 		
		if(list!=null && list.size()>0){
			throw new EntityExistsException("El User tiene un email que esta registrado"); 
		}
		
		entityManager.persist(user); 
	}
	

	@Override
	public User signInUser(@NotNull String email) { 
		
		List<User> list = entityManager.createNamedQuery(User.QUERY_USER_BY_EMAIL) 
    			.setParameter("email",email).getResultList(); 		 
		//TODO verificar formato correo 		
		if(list==null || list.size()!=1){
			throw new EntityNotFoundException(""
					+ "No se encuentra un usuario con el email: " + email); 
		}
		
		return list.get(0); 		
	}

	@Override
	public void signOut() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addCollection(@NotNull String userId, 
								@NotNull Collection collection) {
		
		User user = entityManager.find(User.class, userId); 
		if(user==null){
			throw new EntityNotFoundException(""
					+ "No se encuentra un usuario con el userId: " + userId); 
		}
		
		entityManager.persist(collection); 
		collection.setUser(user); 
		user.getCollections().add(collection);		
	}
	
	@Override
	public void removeCollection(String collectionId) {			 
		 Collection collec = entityManager.find(Collection.class,collectionId);
		 User user = entityManager.find(User.class,collec.getUser().getId());		 
		 Set<Collection> list = user.getCollections();  
		 
		 
		 for (Collection c : list) {
			if(c.equals(collec)){
				list.remove(c);
				break; 
			}
		}		 	
	}
	
	
	@Override
	public void updateCollection(Collection collection) {
		 String collectionId = collection.getId();
		 Collection collectionOld = entityManager.find(Collection.class,collectionId);		 
		 if(collection.getName()!=null &&  !collection.getName().equals(""))
			 collectionOld.setName(collection.getName());
		 if(collection.getDescription()!=null &&  !collection.getDescription().equals(""))
			 collectionOld.setDescription(collection.getDescription());
			
	}
	


	@Override
	public void addItem(String collectionId, Item item, byte[] bytes) {
		// TODO Auto-generated method stub
		Collection collection = entityManager.find(Collection.class, collectionId); 
		if(collection==null){
			throw new EntityNotFoundException(""
					+ "No se encuentra una collection con el userId: " + collectionId); 
		}
		
		collection.getItems().add(item);
		item.setCollection(collection);
		
		Image imagen=null;
		
		if(bytes!=null){
			imagen=new Image();
			imagen.setBytes(bytes);
			item.setImage(imagen);
			entityManager.flush();	
			
			String url = "image_" + imagen.getId() + ".jpg";
		    imagen.setUrl(url); 			
		}
						
	}
	
	
	
	@Override
	public void addImage(String itemId, Image image) {
		// TODO Auto-generated method stub
		Item item = entityManager.find(Item.class, itemId); 
		if(item==null){
			throw new EntityNotFoundException(""
					+ "No se encuentra un item con el itemId: " + itemId); 
		}
		
		
		
		entityManager.persist(image); 
		// No existe el item en image image.setItem(item); 
		if(image.getBytes()!=null){
			 String url = "image_" + image.getId() + ".jpg";
		     image.setUrl(url); 
		}
	}

	@Override
	public void updateItem(Item item, byte[] bytes) {
		// TODO Auto-generated method stub
		String itemId = item.getId();
		//Peta en el find!!!
		Item itemOld= entityManager.find(Item.class,itemId);
		if(item.getName()!=null &&  !item.getName().equals(""))
			 itemOld.setName(item.getName());
		if(item.getDescription()!=null &&  !item.getDescription().equals(""))
			 itemOld.setDescription(item.getDescription());
		
		
		if(bytes!=null){
			if(itemOld.getImage()!=null){
				itemOld.getImage().setBytes(bytes); 				
			}else{
				Image imagen=new Image();
				imagen.setBytes(bytes);
				itemOld.setImage(imagen);
				entityManager.flush();	
				String url = "image_" + imagen.getId() + ".jpg";
			    imagen.setUrl(url); 	
			}
		}
	}

	@Override
	public void removeItem(String itemId) {
		// TODO Auto-generated method stub
		Item item=entityManager.find(Item.class,itemId);
		Collection collec=entityManager.find(Collection.class,item.getCollection().getId());
		Set<Item> list=collec.getItems();
		
		for(Item i:list){
			if(i.equals(item)){
				list.remove(i);
				System.out.println("Borrado el " + i.getName());
				break;
			}
		}
	}
	
	/** Services intented only for test  */
	
	//************************************************************************************
	
	@Override
	public <T> void remove(Class<T> clazz, Object id) {
	     T  entity=	entityManager.find(clazz,id);
	     if(entity!=null)
	    	 entityManager.remove(entity); 
	}
	
	

	@Override
	public <T> T find(Class<T> clazz, Object id) {		
		return entityManager.find(clazz, id); 
	}
	
	@Override
	public <T> List<T> getAll(Class<T> clazz) {
		String clasName = clazz.getSimpleName(); 
		return entityManager.createQuery("SELECT o FROM " + clasName  + " o")
				.getResultList(); 	
	}


	/**
	 * Remove all objets by use { @code entityManager.createQuery("DELETE FROM " +clasName) }
	 */
	@Override
	public <T> void deleteAll(Class<T> clazz) {
		String clasName = clazz.getSimpleName(); 
		entityManager.createQuery("DELETE FROM " +clasName).executeUpdate();
	}


	/**
	 * Remove all objets by user  { @code entityManager.remove(t); }
	 */
	@Override
	public <T> void removeAll(Class<T> clazz) {
		List<T> list = getAll(clazz);  
		//Cada elemento de la lista esta managed dado que se recupero con query
		for (T t : list) {
			entityManager.remove(t); 
		}		
	}
	
	public Boolean logExiste(@NotNull String email) { 
		boolean salida=true;
		/*try{
			List<User> list = entityManager.createNamedQuery(User.QUERY_USER_BY_EMAIL) 
    			.setParameter("email",email).getResultList();
			//TODO verificar formato correo 		
			if(list==null || list.size()!=1){
				salida=false;
			}
		}
		catch(Exception e){
			salida=false;
			System.out.println("la lista da error por null point exception");
		}*/
		try{
		List<User> usuarios=getAll(User.class);
		return salida;
		}
		catch(Exception e){
			salida=false;
			return salida;
		}
		
		
		
		
		
		
	}

	
	
	
	


}
