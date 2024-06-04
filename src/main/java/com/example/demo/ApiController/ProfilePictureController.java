package com.example.demo.ApiController;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.ApiDto.Response;
import com.example.demo.ApiService.FileService;



@RestController
@RequestMapping("/api/v2")
public class ProfilePictureController {
	
    @Autowired
    private FileService fileService;
	
  
   	@PostMapping("/{userId}/upload")
   	public ResponseEntity<Response> uploadImage(@PathVariable String userId, @RequestParam("file") MultipartFile file) {
   	    try {
   	        fileService.saveformFile(file, userId); 
   	        
   	     
	        Response response = new Response.Builder()
                    .setStatus(true)
                    .setMessage("File uploaded successfully!")
                    .build();
  

	        return ResponseEntity.status(HttpStatus.OK).body(response);

   	    } catch (IOException e) {
   	    	
   		  Response response = new Response.Builder()
                  .setStatus(false)
                  .setMessage("Error uploading file: " + e.getMessage())
                  .build();
		  
		  
	       
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
   	     
   	    }
   	}

   	@PutMapping("/{userId}/update")
   	public ResponseEntity<Response> updateProfilePicture(@PathVariable String userId, @RequestParam("file") MultipartFile file) {
   	    try {
   	       
   	     
   	        fileService.saveformFile(file, userId); 
   	        
   	        Response response = new Response.Builder()
   	                .setStatus(true)
   	                .setMessage("Profile picture updated successfully!")
   	                .build();

   	        return ResponseEntity.status(HttpStatus.OK).body(response);
   	    } catch (IOException e) {
   	        Response response = new Response.Builder()
   	                .setStatus(false)
   	                .setMessage("same profile picture: " + e.getMessage())
   	                .build();
   	        
   	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
   	    }
   	}
   	
   	
    
   

}
    

