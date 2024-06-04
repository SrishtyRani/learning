package com.example.demo.ApiDto;



import com.fasterxml.jackson.annotation.JsonView;

public class ChoosendataDto {
	
	  public interface View {
	        interface Summary {}
	    }
	  @JsonView(View.Summary.class)
    private String type;
    
																																			
    private String typechildId;
    
    @JsonView(View.Summary.class)
    private String typeChildName;  

    public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getTypechildId() {
		return typechildId;
	}

	public void setTypechildId(String typechildId) {
		this.typechildId = typechildId;
	}

	public String getTypeChildName() {
		return typeChildName;
	}

	public void setTypeChildName(String typeChildName) {
		this.typeChildName = typeChildName;
	}

	public String getInputtype() {
		return inputtype;
	}

	public void setInputtype(String inputtype) {
		this.inputtype = inputtype;
	}

	
    private String inputtype;


}
