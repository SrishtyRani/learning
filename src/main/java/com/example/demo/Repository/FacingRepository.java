package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.Model.Facing;

public interface FacingRepository  extends JpaRepository<Facing, Long>{

	List<Facing> findByActiveTrue();

}
