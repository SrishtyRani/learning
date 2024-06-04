package com.example.demo.ApiDto;

import com.example.demo.Model.MyUser;

public class JwtResponse {
	 private String jwtToken;
	    private boolean status;
	    private String message;
	    private MyUser user; 


		public boolean isStatus() {
			return status;
		}

		public void setStatus(boolean status) {
			this.status = status;
		}

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		public MyUser getUser() {
			return user;
		}

		public void setUser(MyUser user) {
			this.user = user;
		}

		public String getJwtToken() {
			return jwtToken;
		}

		public void setJwtToken(String jwtToken) {
			this.jwtToken = jwtToken;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		private String username;

	    public JwtResponse(String jwtToken, String username,  boolean status,  String message, MyUser user) {
	        this.jwtToken = jwtToken;
	        this.username = username;
	        this.status = status;
	        this.message = message;
	        this.user = user;
	    }

	    public static class Builder {
	        private String jwtToken;
	        private String username;
	        private boolean status;
	        private String message;
	        private MyUser user; 

	        public Builder jwtToken(String jwtToken) {
	            this.jwtToken = jwtToken;
	            return this;
	        }

	        public Builder username(String username) {
	            this.username = username;
	            return this;
	        }
	        

	        public Builder status(boolean status) {
	            this.status = status;
	            return this;
	        }
	        
	        public Builder message(String message) {
	            this.message = message;
	            return this;
	        }
	        
	        public Builder  user(MyUser  user) {
	            this. user =  user;
	            return this;
	        }


	        public JwtResponse  build(){
	            return new JwtResponse(jwtToken, username,status,message, user);
	        }

		
	    }



}
