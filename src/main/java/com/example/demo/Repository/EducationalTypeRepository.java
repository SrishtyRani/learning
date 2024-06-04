package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.Model.EducationalType;

public interface EducationalTypeRepository extends JpaRepository<EducationalType, Long> {

	List<EducationalType> findByActiveTrue();

}
