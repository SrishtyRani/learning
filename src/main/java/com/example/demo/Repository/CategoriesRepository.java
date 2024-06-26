package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Categories;


public interface CategoriesRepository   extends JpaRepository<Categories, Long> {

	List<Categories> findByActiveTrue();

}
