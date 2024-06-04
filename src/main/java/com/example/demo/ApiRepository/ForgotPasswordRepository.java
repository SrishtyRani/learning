package com.example.demo.ApiRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.ApiModel.ForgotPassword;



public interface ForgotPasswordRepository  extends JpaRepository<ForgotPassword , Integer> {

	Optional<ForgotPassword> findByUserId(Long id);

	Optional<ForgotPassword> findByOtp(int otp);

}
