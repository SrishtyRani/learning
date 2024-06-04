package com.example.demo.ApiDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class MyUserdto {
	   @Email(message = "write the valid eamil")
	   @NotBlank(message = "email is mandatory")
	private String email;
	   @NotBlank(message = "password is mandatory")
	   @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*[0-9])(?=.*[!@#$%^&]).*$", 
       message = "Password must have at least one letter, one number, and one special character: !@#$%^&")
	   private String password;
	   private String verificationToken;
	   private String username;
		private Integer otp;
	public Integer getOtp() {
			return otp;
		}
		public void setOtp(Integer otp) {
			this.otp = otp;
		}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getVerificationToken() {
		return verificationToken;
	}
	public void setVerificationToken(String verificationToken) {
		this.verificationToken = verificationToken;
	}
	public MyUserdto() {

	}
	
	public MyUserdto(String email, String password, String verificationToken, String username) {
		super();
		this.email = email;
		this.password = password;
		this.verificationToken = verificationToken;
		this.username = username;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	   


}
