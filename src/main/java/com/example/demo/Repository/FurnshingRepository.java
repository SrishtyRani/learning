package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;


import com.example.demo.Model.Furnshing;

public interface FurnshingRepository extends JpaRepository<Furnshing, Long> {

	List<Furnshing> findByActiveTrue();

}
