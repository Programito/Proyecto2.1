/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 *  contributor license agreements.  See the NOTICE file distributed with
 *  this work for additional information regarding copyright ownership.
 *  The ASF licenses this file to You under the Apache License, Version 2.0
 *  (the "License"); you may not use this file except in compliance with
 *  the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */
package entities;

import javax.persistence.*;

@Entity 
public class Item {    
	
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String itemId;
	private String name; 
    private String description; 
    
    
    
    @ManyToOne
    Collection collection; 
    
  
    @OneToOne(cascade={CascadeType.PERSIST,CascadeType.REMOVE},
			optional=false)
    Image image; 
    
   
    
    
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

    public String getId() { 
        return itemId;
    }
    
    
    public Image getImage(){
    	return image;
    }
    
    public void setImage(Image image){
    	this.image=image;
    }
    
    public Collection getCollection(){
    	return collection;
    }
    
    public void setCollection(Collection collection){
    	this.collection=collection;
    }

  
    @Override
    public String toString() {
        return "Book{" +
                "itemId=" + itemId +
                ", name='" + name + '\'' +
                '}';
    }

	public String setId(String id) {
		// TODO Auto-generated method stub
		return itemId;
		
	}
}

