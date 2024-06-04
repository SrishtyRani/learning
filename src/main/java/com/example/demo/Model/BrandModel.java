package com.example.demo.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BrandModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private boolean active;
	
	
	 @ManyToOne
	    @JoinColumn( nullable = false,name = "parent_id")
	    private Brand ParentBrand;


	public Brand getParentBrand() {
		return ParentBrand;
	}


	public void setParentBrand(Brand parentBrand) {
		ParentBrand = parentBrand;
	}





	public Long getId() {
		return id;
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

	 public Long getParentCategoryId() {
	        if ( ParentBrand!= null) {
	            return ParentBrand.getId();
	        } else {
	        	  throw new IllegalStateException("Parent category is not assigned for this subcategory.");

	        }}


}
