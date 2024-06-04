package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.Model.Fuel;

public interface FuelRepository   extends JpaRepository<Fuel, Long>{

	List<Fuel> findByActiveTrue();

}
