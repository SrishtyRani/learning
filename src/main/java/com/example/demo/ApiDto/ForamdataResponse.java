package com.example.demo.ApiDto;

import com.fasterxml.jackson.annotation.JsonView;

public class ForamdataResponse {
	  @JsonView(ChoosendataDto.View.Summary.class)
    private boolean status;
	  @JsonView(ChoosendataDto.View.Summary.class)
    private String message;
	  @JsonView(ChoosendataDto.View.Summary.class)
    private FormDataSaveResponse result;

    private ForamdataResponse(Builder builder) {
        this.status = builder.status;
        this.message = builder.message;
        this.result = builder.result;
    }


    public void setStatus(boolean status) {
		this.status = status;
	}


	public void setMessage(String message) {
		this.message = message;
	}


	public void setresult(FormDataSaveResponse result) {
		this.result = result;
	}


	public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public FormDataSaveResponse getresult() {
        return result;
    }

    public static class Builder {
        private boolean status;
        private String message;
        private FormDataSaveResponse result;

        public Builder setStatus(boolean status) {
            this.status = status;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setresult(FormDataSaveResponse result) {
            this.result = result;
            return this;
        }

        public ForamdataResponse build() {
            return new ForamdataResponse(this);
        }
    }
}
