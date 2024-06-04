package com.example.demo.ApiDto;


public class UserWithProfilePicDTO {
	
	 private Long userId;
	    private String email;
	    private String username;
	    private String profilePicPath;
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
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
	
		public UserWithProfilePicDTO(Long userId, String email, String username, String profilePicPath) {
			super();
			this.userId = userId;
			this.email = email;
			this.username = username;
			this.profilePicPath = profilePicPath;
		}
		public UserWithProfilePicDTO() {
			// TODO Auto-generated constructor stub
		}
		public String getProfilePicPath() {
			return profilePicPath;
		}
		public void setProfilePicPath(String profilePicPath) {
			this.profilePicPath = profilePicPath;
		}

}
