package com.example.demo.ApiController;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ApIConfigration.DetailService;
import com.example.demo.ApIConfigration.JwtHelper;
import com.example.demo.ApiDto.JwtRequest;
import com.example.demo.ApiDto.JwtResponse;
import com.example.demo.ApiDto.Response;
import com.example.demo.Model.MyUser;
import com.example.demo.Repository.MyUserRepository;



@RestController
@RequestMapping("/api/v2")
public class LoginApiController {

	@Autowired
	private DetailService userDetailsService;

	@Autowired
	private MyUserRepository myUserRepository;

	@Autowired
	private JwtHelper jwtHelper;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@PostMapping("/user/login")
	public ResponseEntity<?> login(@RequestBody JwtRequest request) {
		
		
	    try {
	        System.out.println("Email: " + request.getEmail());
	        System.out.println("Password: " + request.getPassword());
	        System.out.println("Path: " + request.getPath());
	        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
	        
	        MyUser user = myUserRepository.findByEmail(request.getEmail())
	                .orElseThrow(() -> {
	                    System.out.println("User not found for email: " +"mai v kam kar rha hu");
	                    return new BadCredentialsException("User not found with email: " + request.getEmail());
	                });

	        if (!user.isVerified()) {
	        	
	            throw new BadCredentialsException("User not verified: " + request.getEmail());
	        }

	        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
	            System.out.println("Invalid password" + "i am working bro");
	            throw new BadCredentialsException("Invalid password");
	        }

	

	        String token = jwtHelper.generateToken(userDetails);

	        JwtResponse response = new JwtResponse.Builder().status(true)
	                .message("Login successful! Welcome, " + user.getUsername()).user(user).jwtToken(token)
	                .username(userDetails.getUsername()).build();

	        return ResponseEntity.ok(response);
	    } catch (UsernameNotFoundException e) {
	        if (e.getMessage().equals("User not found with email: " + request.getEmail())) {
	        	  System.out.println(e.getMessage() +"aa jaa bhai");
	        	  Response response = new Response.Builder()
                        .setStatus(false)
                        .setMessage("User not found")
                        .build();
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( response);
	        } else if (e.getMessage().startsWith("User not verified: " + request.getEmail())) {
	       	  Response response = new Response.Builder()
                      .setStatus(false)
                      .setMessage("User not verified")
                      .build();
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	        } else {
	            System.out.println("Unknown UsernameNotFoundException: " + e.getMessage());
	            
	            Response response = new Response.Builder()
	                      .setStatus(false)
	                      .setMessage("Invalid username or password")
	                      .build();
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	        }
	    } catch (BadCredentialsException e) {
	    	if (e.getMessage().equals("User not found with email: " + request.getEmail())) {
	        	  System.out.println(e.getMessage() +"aa jaa bhai");
	        	  Response response = new Response.Builder()
                        .setStatus(false)
                        .setMessage("user not found")
                        .build();
	    		  
	    		   	  
	            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response); }
	    	 else if (e.getMessage().startsWith("User not verified: " + request.getEmail())) {
	        	  System.out.println(e.getMessage() +"aa gya  bhai");
	        	  Response response = new Response.Builder()
	                      .setStatus(false)
	                      .setMessage("User not verified")
	                      .build();
		            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);}
	    	else {
	        System.out.println("Invalid password");
	  	  Response response = new Response.Builder()
                  .setStatus(false)
                  .setMessage("Invalid password")
                  .build();
  		  
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);}
	    }
	}

			
		
}
	
	
