package com.example.demo.Model;



import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;



@Entity
public class FormData {
	
		@Id
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;	   
		    private String checkedBoxes;

			public FormData() {
				super();
			}

		    private Long subcategory;
		    
		    private Long category;

			public Long getCategory() {
				return category;
			}

			public void setCategory(Long category) {
				this.category = category;
			}

			public Long getId() {
				return id;
			}

			public void setId(Long id) {
				this.id = id;
			}

			public String getCheckedBoxes() {
				return checkedBoxes;
			}

			public void setCheckedBoxes(String checkedBoxes) {
				this.checkedBoxes = checkedBoxes;
			}

			public Long getSubcategory() {
				return subcategory;
			}

			public void setSubcategory(Long subcategory) {
				this.subcategory = subcategory;
			}

			public Long getTypeChildId() {
				// TODO Auto-generated method stub
				return null;
			}

			public String getType() {
				// TODO Auto-generated method stub
				return null;
			}


		    
}

