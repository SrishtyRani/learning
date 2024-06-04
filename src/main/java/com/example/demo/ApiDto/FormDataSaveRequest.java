package com.example.demo.ApiDto;

import java.util.List;

import com.example.demo.Model.TypeData;

public class FormDataSaveRequest {

	    private Long formid;
	    private String address;
	    private String path;
	    private String title;
	    private List<TypeData> data;
		private boolean active;

	    // Getters and setters

	    public boolean isActive() {
			return active;
		}

		public void setActive(boolean active) {
			this.active = active;
		}

		public Long getFormid() {
	        return formid;
	    }

	    public void setFormid(Long formid) {
	        this.formid = formid;
	    }

	    public String getAddress() {
	        return address;
	    }

	    public void setAddress(String address) {
	        this.address = address;
	    }

	    public String getPath() {
	        return path;
	    }

	    public void setPath(String path) {
	        this.path = path;
	    }

	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title;
	    }

	    public List<TypeData> getData() {
	        return data;
	    }

	    public void setData(List<TypeData> data) {
	        this.data = data;
	    }
	}


