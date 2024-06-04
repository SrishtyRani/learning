package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.Model.Position;

public interface PositionRepository extends JpaRepository<Position, Long>  {

	List<Position> findByActiveTrue();

}
