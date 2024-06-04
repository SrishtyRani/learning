package com.example.demo.ApiDto;




import java.util.List;


import com.fasterxml.jackson.annotation.JsonView;

public class FormDataSaveResponse {
	
	  @JsonView(ChoosendataDto.View.Summary.class)
	 private List<ChoosendataDto> OtherFileds;
	 
	    private Long formid;
	    @JsonView(ChoosendataDto.View.Summary.class)
	    private String address;
	    @JsonView(ChoosendataDto.View.Summary.class)
	    private List<String> pathsListWithBaseUrl;
	    @JsonView(ChoosendataDto.View.Summary.class)
	    private String title;
	    @JsonView(ChoosendataDto.View.Summary.class)
	    private boolean active;
	


	
	    public FormDataSaveResponse(List<ChoosendataDto> OtherFileds, Long formid, String address, List<String> pathsListWithBaseUrl, String title, boolean active) {
	        this.OtherFileds = OtherFileds;
	        this.formid = formid;
	        this.address = address;
	        this.pathsListWithBaseUrl = pathsListWithBaseUrl;
	        this.title = title;
	        this.active = active;
	    }

	 
	    public List<ChoosendataDto> getData() {
	        return OtherFileds;
	    }

	    public void setOtherFileds(List<ChoosendataDto> otherFileds) {
			OtherFileds = otherFileds;
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

	    public List<String> getPathsListWithBaseUrl() {
	        return pathsListWithBaseUrl;
	    }

	    public void setPathsListWithBaseUrl(List<String> pathsListWithBaseUrl) {
	        this.pathsListWithBaseUrl = pathsListWithBaseUrl;
	    }

	    public String getTitle() {
	        return title;
	    }

	    public void setTitle(String title) {
	        this.title = title;
	    }

	    public boolean isActive() {
	        return active;
	    }

	    public void setActive(boolean active) {
	        this.active = active;
	    }
	}



    