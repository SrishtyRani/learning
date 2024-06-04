package com.example.demo.ApiDto;

import org.springframework.http.ResponseEntity.HeadersBuilder;

import com.fasterxml.jackson.annotation.JsonView;

//public class Response {
//    private boolean status;
//    private String message;
//	public boolean isStatus() {
//		return status;
//	}
//	public void setStatus(boolean status) {
//		this.status = status;
//	}
//	
//	public void setMessage(String message) {
//		this.message = message;
//	}
//	
//	 
//    private Response(Builder builder) {
//        this.status = builder.status;
//        this.message = builder.message;
//    }
//    
//   
//	public boolean getStatus() {
//        return status;
//    }
//    
//    public String getMessage() {
//        return message;
//    }
//    
//    public static class Builder {
//        private boolean status;
//        private String message;
//        
//        public Builder() {
//         
//        }
//        
//        public Builder setStatus(boolean status) {
//            this.status = status;
//            return this;
//        }
//        
//        public Builder setMessage(String searchResponse) {
//            this.message = searchResponse;
//            return this;
//        }
//       
//        public Response build() {
//            return new Response(this);
//        }
//
//	
//    }
//}

public class Response {
	  @JsonView(ChoosendataDto.View.Summary.class)
    private boolean status;
	  @JsonView(ChoosendataDto.View.Summary.class)
    private String message;
	  @JsonView(ChoosendataDto.View.Summary.class)
	  
    private SearchResponse result;

 

    public Response() {
		
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SearchResponse getSearchResponse() {
		return result;
	}

	public void setSearchResponse(SearchResponse result) {
		this.result = result;
	}

	private Response(Builder builder) {
        this.status = builder.status;
        this.message = builder.message;
        this.result = builder.result;
    }

    public static class Builder {
        private boolean status;
        private String message;
        private SearchResponse result;

        public Builder setStatus(boolean status) {
            this.status = status;
            return this;
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setSearchResponse(SearchResponse result) {
            this.result = result;
            return this;
        }

        public Response build() {
            return new Response(this);
        }

		public HeadersBuilder<?> setSearchResponse(FormDataSaveResponse searchResponse) {
			// TODO Auto-generated method stub
			return null;
		}
    }

 
}

