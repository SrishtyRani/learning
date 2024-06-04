package com.example.demo.ApiDto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonView;

public class SearchResponse {
	
    private int page;
	  @JsonView(ChoosendataDto.View.Summary.class)
    private int totalPages;
	  @JsonView(ChoosendataDto.View.Summary.class)
    private int totalResults;
	  @JsonView(ChoosendataDto.View.Summary.class)
    private List<FormDataSaveResponse> Data;
	  

	  

	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}
	public int getTotalResults() {
		return totalResults;
	}
	public void setTotalResults(int totalResults) {
		this.totalResults = totalResults;
	}
	public List<FormDataSaveResponse> getData() {
		return Data;
	}
	public void setData(List<FormDataSaveResponse> Data) {
		this.Data = Data;
	}
	public SearchResponse(int page, int totalPages, int totalResults, List<FormDataSaveResponse> Data) {
		super();
		this.page = page;
		this.totalPages = totalPages;
		this.totalResults = totalResults;
		this.Data = Data;
	}
	public SearchResponse() {
		
	}





}