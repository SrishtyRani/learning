package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Vehicles;

public interface VehiclesRepository  extends JpaRepository<Vehicles, Long>{

	List<Vehicles> findByActiveTrue();

}
