package com.example.demo.ApiController;

import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ApiDto.MyUserdto;
import com.example.demo.ApiDto.Response;
import com.example.demo.Model.MyUser;
import com.example.demo.Repository.MyUserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v2")
public class OptRegisterApi {
	
	 @Autowired
	    private MyUserRepository myUserRepository;
	    @Autowired
	    private PasswordEncoder passwordEncoder;
		@Autowired
		private JavaMailSender javaMailSender ;
	
	@PostMapping("/register")
	public ResponseEntity<Response> register(@RequestBody @Valid MyUserdto myUserDto) {
	    if (myUserRepository.existsByEmail(myUserDto.getEmail())) {
	        Response response = new Response.Builder()
	                .setStatus(false)
	                .setMessage("Email already exists. Please use a different email address.")
	                .build();
	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	    }

	    MyUser user = new MyUser();
	    user.setEmail(myUserDto.getEmail());
	    user.setUsername(myUserDto.getUsername());

	    String encodedPassword = passwordEncoder.encode(myUserDto.getPassword());
	    System.out.println("Encoded Password: " + encodedPassword);

	    user.setPassword(encodedPassword);
	    user.setVerified(false);

	  
	    Integer otp = generateOTP();
	    user.setOtp(otp);

	    myUserRepository.save(user);

	  
	    sendVerificationEmail(user.getEmail(), otp);

	    Response response = new Response.Builder()
	            .setStatus(true)
	            .setMessage("Verification email sent. Please verify your email address.")
	            .build();

	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	 private Integer generateOTP() {
	        Random random = new Random();
	        return 100000 + random.nextInt(900000);
	    }

	private void sendVerificationEmail(String email, Integer otp) {
	    SimpleMailMessage message = new SimpleMailMessage();
	    message.setTo(email);
	    message.setSubject("Verify Your Account");
	    message.setText("Your One-Time Password (OTP) for account verification is: " + otp);
	    javaMailSender.send(message);
	}

	
	

	
	@PostMapping("/verify")
	public ResponseEntity<Response> verifyOTP(@RequestBody MyUserdto myUserDto) {

	    int otp = myUserDto.getOtp();

	    
	    Optional<MyUser> userOptional = myUserRepository.findByOtp(otp);

	    if (!userOptional.isPresent()) {
	        Response response = new Response.Builder()
	                .setStatus(false)
	                .setMessage("Invalid OTP or User")
	                .build();

	        return ResponseEntity.badRequest().body(response);
	    }
 

	    MyUser user = userOptional.get();
	    user.setVerified(true);
	    myUserRepository.save(user);

	    Response response = new Response.Builder()
	            .setStatus(true)
	            .setMessage("OTP verified. Account is now verified.")
	            .build();

	    return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	  @GetMapping("/{userId}/getuser")
	    public ResponseEntity<MyUser> getUserDetails(@PathVariable Long userId) {
	        MyUser user = myUserRepository.findById(userId).orElse(null);
	        if (user != null) {
	            String userPath = user.getPath();
	            System.out.println("User Path: " + userPath); 
	            return ResponseEntity.ok(user);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }

}

