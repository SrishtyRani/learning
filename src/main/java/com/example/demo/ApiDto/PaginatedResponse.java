package com.example.demo.ApiDto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonView;

public class PaginatedResponse <T> {
	   @JsonView(ChoosendataDto.View.Summary.class)
	 private List<T> content;
	   @JsonView(ChoosendataDto.View.Summary.class)
	    private int pageNumber;
	   @JsonView(ChoosendataDto.View.Summary.class)
	    private int pageSize;
	   @JsonView(ChoosendataDto.View.Summary.class)	   
	    private long totalElements;
	   @JsonView(ChoosendataDto.View.Summary.class)
	    private int totalPages;

	    public PaginatedResponse(List<T> content, int pageNumber, int pageSize, long totalElements, int totalPages) {
	        this.content = content;
	        this.pageNumber = pageNumber;
	        this.pageSize = pageSize;
	        this.totalElements = totalElements;
	        this.totalPages = totalPages;
	    }

		public List<T> getContent() {
			return content;
		}

		public void setContent(List<T> content) {
			this.content = content;
		}

		public int getPageNumber() {
			return pageNumber;
		}

		public void setPageNumber(int pageNumber) {
			this.pageNumber = pageNumber;
		}

		public int getPageSize() {
			return pageSize;
		}

		public void setPageSize(int pageSize) {
			this.pageSize = pageSize;
		}

		public long getTotalElements() {
			return totalElements;
		}

		public void setTotalElements(long totalElements) {
			this.totalElements = totalElements;
		}

		public int getTotalPages() {
			return totalPages;
		}

		public void setTotalPages(int totalPages) {
			this.totalPages = totalPages;
		}

}
