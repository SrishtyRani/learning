package com.example.demo.ApiDto;

public class SubcategoryDto {
	
	   private Long id;
	    private String name;
	    private boolean active;
		public Long getId() {
			return id;
		}
		public SubcategoryDto() {
			super();
			// TODO Auto-generated constructor stub
		}
		public SubcategoryDto(Long id, String name, boolean active) {
			super();
			this.id = id;
			this.name = name;
			this.active = active;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public boolean isActive() {
			return active;
		}
		public void setActive(boolean active) {
			this.active = active;
		}

}

