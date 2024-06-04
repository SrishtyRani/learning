package com.example.demo.Model;

import jakarta.persistence.Embeddable;

@Embeddable
public class TypeData {
	   private String type;
	    private String typechildId;
	    private String inputtype;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public TypeData() {
			
		}
		public TypeData(String type, String typechildId, String inputtype) {
			super();
			this.type = type;
			this.typechildId = typechildId;
			this.inputtype = inputtype;
		}
		public String getTypechildId() {
			return typechildId;
		}
		public void setTypechildId(String typechildId) {
			this.typechildId = typechildId;
		}
		public String getInputtype() {
			return inputtype;
		}
		public void setInputtype(String inputtype) {
			this.inputtype = inputtype;
		}
		
		@Override
		public String toString() {
		    return type + ", " + typechildId + ", " + inputtype;
		}
}
