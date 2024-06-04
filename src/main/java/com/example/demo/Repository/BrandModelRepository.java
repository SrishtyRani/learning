package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.BrandModel;



public interface BrandModelRepository extends JpaRepository<BrandModel, Long> {

	List<BrandModel> findByActiveTrue();

}
