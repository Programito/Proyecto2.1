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


/**
 * Ver detalles de las anotacion  @GeneratedValue  en 
 * http://www.objectdb.com/java/jpa/entity/generated
 *  
 * @author campino
 *
 */

@Entity
public class Image {
		
	
    @Id
    @GeneratedValue(strategy =GenerationType.AUTO)
    private int id;
    
    private String url;
    
    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(length=17000000)
    private byte[] bytes; 
    
    public int getId() {
        return id;
    }

    public void setId(int bookId) {
        this.id = bookId;
    }

	public byte[] getBytes() {
		return bytes;
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}

