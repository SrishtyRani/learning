package com.example.demo.ApiController;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ApiDto.Response;
import com.example.demo.ApiModel.ForgotPassword;
import com.example.demo.ApiRepository.ForgotPasswordRepository;
import com.example.demo.Repository.MyUserRepository;



@RestController
@RequestMapping("/api/v2")
public class Deleteapi {
	
	 @Autowired
	    private MyUserRepository  myUserRepository;
	 
	 
	    @Autowired
	    private ForgotPasswordRepository userProfileRepository;
	
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (myUserRepository.existsById(id)) {
	    
	    Optional<ForgotPassword> userProfileOptional = userProfileRepository.findByUserId(id);
        if (userProfileOptional.isPresent()) {
           
        	ForgotPassword userProfile = userProfileOptional.get();
            userProfileRepository.delete(userProfile);
        }
        	myUserRepository.deleteById(id);
        	
            Response response = new Response.Builder()
                    .setStatus(true)
                    .setMessage("User with ID %s has been deleted." +id)
                    .build();
    	  
           
            return ResponseEntity.status(HttpStatus.OK).body(response);
        
        } else {
        	
            Response response = new Response.Builder()
                    .setStatus(false)
                    .setMessage("User with ID %s has been deleted." +id)
                    .build();
    	  
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }
}

