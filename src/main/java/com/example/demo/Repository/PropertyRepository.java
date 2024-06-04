package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Property;



public interface PropertyRepository  extends JpaRepository<Property, Long>{

	List<Property> findByActiveTrue();

}
