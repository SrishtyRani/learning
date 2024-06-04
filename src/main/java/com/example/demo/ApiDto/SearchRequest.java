package com.example.demo.ApiDto;

import com.fasterxml.jackson.annotation.JsonView;

public class SearchRequest {
	  @JsonView(ChoosendataDto.View.Summary.class)
	 private String title;
	  @JsonView(ChoosendataDto.View.Summary.class)
	    private int page;
	    
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public SearchRequest(String title, int page) {
			super();
																																				
			this.title = title;
			this.page = page;
		}
		public int getPage() {
			return page;
		}
		public void setPage(int page) {
			this.page = page;
		}
		  
	    public SearchRequest() {
	    }
}
