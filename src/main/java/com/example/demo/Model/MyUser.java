package com.example.demo.Model;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;


@Entity
public class MyUser {

	   @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Long id;
	   @Email(message = "write the valid eamil")
	   @NotBlank(message = "email is mandatory")
	    private String email;
	   @NotBlank(message = "Name is mandatory")
	    private String username;	   
	   @NotBlank(message = "password is mandatory")
	   @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&]).*$", 
    message = "Password must have at least one letter, one number, and one special character: !@#$%^&")
	    private String password;
	  
		private boolean verified;
	    private String verificationToken;
	    private String role; 
	    public String getRole() {
			return role;
		}

		public void setRole(String role) {
			this.role = role;
		}
		private Integer otp;
	    public Integer getOtp() {
			return otp;
		}

		public void setOtp(Integer otp) {
			this.otp = otp;
		}
		private String path;
	    
	  
	 
		public String getPath() {
			return  "http://localhost:8080/" +path;
		}

		public void setPath(String path) {
			this.path = path;
		}

		public boolean isVerified() {
			return verified;
		}
		public void setVerified(boolean verified) {
			this.verified = verified;
		}
		public String getVerificationToken() {
			return verificationToken;
		}
		public void setVerificationToken(String verificationToken) {
			this.verificationToken = verificationToken;
		}
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
		public String getPassword() {
			return password;
		}
		public void setPassword(String password) {
			this.password = password;
		}
		
}
