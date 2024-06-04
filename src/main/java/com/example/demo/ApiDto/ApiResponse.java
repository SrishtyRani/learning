package com.example.demo.ApiDto;


public class ApiResponse {
	
    private String type;
    private String inputType;
    private Object data;
 
    public ApiResponse(String type, String inputType ,Object data) {
        this.type = type;
        this.inputType = inputType;
        this.data = data;
      
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getInputType() {
        return inputType;
    }

    public void setInputType(String inputType) {
        this.inputType = inputType;
    }
}

