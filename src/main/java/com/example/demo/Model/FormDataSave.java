package com.example.demo.Model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;


@Entity
public class FormDataSave {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id ;
	private Long formid;
	private String Address;

	 private String paths; 
	private String title;
    public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}




	public Long getFormid() {
		return formid;
	}




	public void setFormid(Long formid) {
		this.formid = formid;
	}




	public String getAddress() {
		return Address;
	}




	public void setAddress(String address) {
		Address = address;
	}






	



	public String getTitle() {
		return title;
	}




	public void setTitle(String title) {
		this.title = title;
	}




	public String getDataJson() {
		return dataJson;
	}




	public void setDataJson(String dataJson) {
		this.dataJson = dataJson;
	}




	public boolean isActive() {
		return active;
	}




	public void setActive(boolean active) {
		this.active = active;
	}




	@Lob
    @Column(columnDefinition = "TEXT")
    private String dataJson;
	private boolean active;

	


	public FormDataSave() {
		
	}




	public Long getTypeChildId() {
		// TODO Auto-generated method stub
		return null;
	}




	public String getType() {
		// TODO Auto-generated method stub
		return null;
	}




	public List<String> getPathsList() {
	    List<String> pathsList = new ArrayList<>();
	    if (this.paths != null && !this.paths.isEmpty()) {
	        String[] pathsArray = this.paths.split(",");
	        for (String path : pathsArray) {
	            pathsList.add(path);
	        }
	    }
	    return pathsList;
	}

	public List<String> getPathsListWithBaseUrl() {
	    List<String> pathsWithBaseUrl = new ArrayList<>();
	    List<String> paths = getPathsList();
	    for (String path : paths) {
	        pathsWithBaseUrl.add("http://localhost:8080/" + path);
	    }
	    return pathsWithBaseUrl;
	}


	
	public void setPathsList(List<String> pathsList) {
	    if (pathsList == null || pathsList.isEmpty()) {
	        this.paths = ""; 
	    } else {
	        this.paths = String.join(",", pathsList);
	    }
	}
}


