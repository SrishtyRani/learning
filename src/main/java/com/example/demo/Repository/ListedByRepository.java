package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.ListedBy;


public interface ListedByRepository  extends JpaRepository<ListedBy, Long>  {

	List<ListedBy> findByActiveTrue();

}
