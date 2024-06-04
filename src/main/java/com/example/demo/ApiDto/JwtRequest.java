package com.example.demo.ApiDto;



public class JwtRequest {
	 private String email;
	    private String password;

	    public JwtRequest() {
	    }

	    public JwtRequest(String email, String password) {
	        this.email = email;
	        this.password = password;
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
	    
	    public JwtRequest build() {
	        JwtRequest jwtRequest = new JwtRequest();
	        jwtRequest.setEmail(email);
	        jwtRequest.setPassword(password);
	        return jwtRequest;
	    }

		public String getPath() {
			// TODO Auto-generated method stub
			return null;
		}

}

