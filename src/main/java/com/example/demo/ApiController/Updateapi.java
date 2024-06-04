package com.example.demo.ApiController;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ApiDto.Response;
import com.example.demo.Model.MyUser;
import com.example.demo.Repository.MyUserRepository;


@RestController
@RequestMapping("/api/v2")
public class Updateapi {
	
	 @Autowired
	    private MyUserRepository myUserRepository;


	    @GetMapping("/{id}")
	    public ResponseEntity<?> getUserById(@PathVariable Long id) {
	        Optional<MyUser> optionalUser = myUserRepository.findById(id);
	        if (optionalUser.isPresent()) {
	            MyUser user = optionalUser.get();
	            
	            return ResponseEntity.status(HttpStatus.OK).body(user);
	        } else {
	        	  Response response = new Response.Builder()
	                        .setStatus(false)
	                        .setMessage("User not found")
	                        .build();
	        	  
	            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	        }
	    }
	
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody MyUser updatedUser) {
        if (myUserRepository.existsById(id)) {
            MyUser existingUser = myUserRepository.findById(id).get();
            existingUser.setUsername(updatedUser.getUsername());
//            existingUser.setEmail(updatedUser.getEmail());
            myUserRepository.save(existingUser);
            
            Response response = new Response.Builder()
                    .setStatus(true)
                    .setMessage("User with ID %s has been updated." + id)
                    .build();
        	
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
        	
        	   Response response = new Response.Builder()
                       .setStatus(false)
                       .setMessage("User not found")
                       .build();
        	   
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

}
