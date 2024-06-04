package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.Model.ConstructionStatus;

public interface ConstructionStatusRepository  extends JpaRepository<ConstructionStatus, Long>{

	List<ConstructionStatus> findByActiveTrue();

}
