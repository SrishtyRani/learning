package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.FormData;



public interface FormDataRepository extends  JpaRepository<FormData, Long> {

	Optional<FormData> findBySubcategory(Long subcategory);

}
