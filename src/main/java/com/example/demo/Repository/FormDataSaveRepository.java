package com.example.demo.Repository;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Model.FormDataSave;



public interface FormDataSaveRepository extends    JpaRepository  <FormDataSave , Long>  {

    @Query("SELECT f FROM FormDataSave f WHERE f.active = true")
    Page<FormDataSave> findActiveFormData(Pageable pageable);

	Page<FormDataSave> findByActiveTrue(Pageable pageable);


    List<FormDataSave> findByTitleContainingIgnoreCase(String title);

	Page<FormDataSave> findByTitleContainingIgnoreCase(String searchTerm, PageRequest of);

	Page<FormDataSave> findByTitleContainingIgnoreCaseAndActiveTrue(String title, Pageable pageable);


}
