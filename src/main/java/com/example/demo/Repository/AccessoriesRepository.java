package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Accessories;


public interface AccessoriesRepository extends JpaRepository<Accessories, Long> {

	List<Accessories> findByActiveTrue();

}
