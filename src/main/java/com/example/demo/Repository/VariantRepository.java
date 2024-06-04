package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Variant;

public interface VariantRepository extends JpaRepository <Variant, Long> {

	List<Variant> findByActiveTrue();

}
