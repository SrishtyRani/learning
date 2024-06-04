package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.Transmission;



public interface TransmissionRepository   extends JpaRepository <Transmission, Long>{

	List<Transmission> findByActiveTrue();

}
