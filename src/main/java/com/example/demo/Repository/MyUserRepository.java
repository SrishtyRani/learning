package com.example.demo.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.Model.MyUser;

public interface MyUserRepository extends JpaRepository<MyUser, Long> {

    Optional<MyUser> findByUsername(String username);

  Optional<MyUser> findByEmail(String email);
//    MyUser findByEmail(String username);

	MyUser findByVerificationToken(String token);

	Optional<MyUser> findByOtp(int otp);

	boolean existsByEmail(String email);

}
