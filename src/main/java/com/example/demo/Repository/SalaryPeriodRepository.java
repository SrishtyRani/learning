package com.example.demo.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.SalaryPeriod;

public interface SalaryPeriodRepository extends JpaRepository<SalaryPeriod, Long> {

	List<SalaryPeriod> findByActiveTrue();

}
