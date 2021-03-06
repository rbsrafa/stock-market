package com.cct.stockmarket.api.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException{
	
	/**
	 * Create a default ResourceNotFoundException
	 */
	public ResourceNotFoundException() {}

	/**
	 * Create a new ResourceNotFoundException
	 * @param resource
	 * @param id
	 */
	public ResourceNotFoundException(String resource, Long id){
        super(resource + " id " + id + " not found");
    }
	
}
