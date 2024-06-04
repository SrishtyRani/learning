package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Brand;

public interface BrandRepository  extends JpaRepository<Brand, Long>{

	List<Brand> findByActiveTrue();

}
