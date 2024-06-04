package com.example.demo.ApiController;

import java.util.Date;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.ApiDto.ChangePasswordRequest;
import com.example.demo.ApiDto.Emaildto;
import com.example.demo.ApiDto.Response;
import com.example.demo.ApiModel.ForgotPassword;
import com.example.demo.ApiRepository.ForgotPasswordRepository;
import com.example.demo.Model.MyUser;
import com.example.demo.Repository.MyUserRepository;



@RestController
@RequestMapping("/api/v2")
public class ForgotPasswordApiController {
	
	@Autowired
	private JavaMailSender javaMailSender ;
	@Autowired
	private  ForgotPasswordRepository forgotrepo;
	
    @Autowired
    private MyUserRepository myuserrepo;
    
    @Autowired
	private PasswordEncoder passwordEncoder;


    @PostMapping("/sendotp")
    public ResponseEntity<Response> verifyEmail(@RequestBody Emaildto emailDto) {
        String email = emailDto.getEmail();
        Optional<MyUser> user = myuserrepo.findByEmail(email);

        if (!user.isPresent()) {
            Response response = new Response.Builder()
                    .setStatus(false)
                    .setMessage("User not found")
                    .build();
    	  
           
            return ResponseEntity.badRequest().body(response);
        }

        Optional<ForgotPassword> existingForgotPassword = forgotrepo.findByUserId(user.get().getId());
        int otp = otpGenerator();

        if (existingForgotPassword.isPresent()) {
          
            ForgotPassword existingFP = existingForgotPassword.get();
            existingFP.setOtp(otp);
            existingFP.setExpried(new Date(System.currentTimeMillis() + 60 * 60* 1000));
            forgotrepo.save(existingFP);
        } else {
         
            ForgotPassword fp = new ForgotPassword.Builder()
                    .otp(otp)
                    .expired(new Date(System.currentTimeMillis() + 60* 60 * 1000))
                    .user(user.get())
                    .build();
            forgotrepo.save(fp);
        }

        sendForgotPasswordEmail(email, String.valueOf(otp));
        
        Response response = new Response.Builder()
                .setStatus(true)
                .setMessage("OTP sent for forgot password.")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    private void sendForgotPasswordEmail(String email, String otp) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Otp for forget password");
        message.setText("This is the otp for your forgot password request:   " + otp);
        javaMailSender.send(message);
    }

    private Integer otpGenerator() {
        Random random = new Random();
        return 100000 + random.nextInt(900000);
    }
    
    @PostMapping("/verifyotp")
    public ResponseEntity<Response> verifyotp(@RequestBody Emaildto  emaildto ) {

        int otp =  emaildto.getOtp();
        
       
        Optional<ForgotPassword> existingForgotPassword = forgotrepo.findByOtp(otp);

        if (!existingForgotPassword.isPresent()) {
        	   Response response = new Response.Builder()
                       .setStatus(false)
                       .setMessage("Invalid OTP or User")
                       .build();
       	  
              
               return ResponseEntity.badRequest().body(response);
           
        }
        
        ForgotPassword forgotPassword = existingForgotPassword.get();
        if (forgotPassword.getExpried().before(new Date())) {
        
        	   Response response = new Response.Builder()
                       .setStatus(false)
                       .setMessage("otp has expired")
                       .build();
       	  
              
               return ResponseEntity.badRequest().body(response);
        }
        
        Response response = new Response.Builder()
                .setStatus(true)
                .setMessage("OTP verified successfully.")
                .build();
	  
        return ResponseEntity.status(HttpStatus.OK).body(response);
 
    }
    
    
   
    @PostMapping("/changepassword")
    public ResponseEntity<Response> changePassword(@RequestBody ChangePasswordRequest changePasswordRequest) {
        String email = changePasswordRequest.getEmail();
        String newPassword = changePasswordRequest.getNewPassword();
        String confirmPassword = changePasswordRequest.getConfirmPassword();

      
        if (!newPassword.equals(confirmPassword)) {
            Response response = new Response.Builder()
                    .setStatus(false)
                    .setMessage("New password and confirm password do not match")
                    .build();
            return ResponseEntity.badRequest().body(response);
        }

        Optional<MyUser> userOptional = myuserrepo.findByEmail(email);

        if (!userOptional.isPresent()) {
            Response response = new Response.Builder()
                    .setStatus(false)
                    .setMessage("User not found")
                    .build();
            return ResponseEntity.badRequest().body(response);
        }


        MyUser user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        myuserrepo.save(user);

        Response response = new Response.Builder()
                .setStatus(true)
                .setMessage("Password Changed Successfully.")
                .build();
        return ResponseEntity.ok(response);
    }

    

}

