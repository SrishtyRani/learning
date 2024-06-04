//package com.example.demo.Service;
//
//import java.util.Date;
//import java.util.Optional;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import com.example.demo.Model.MyUser;
//import com.example.demo.Model.PasswordResetToken;
//import com.example.demo.Model.RegisterToken;
//import com.example.demo.Repository.ResterationTokenRepository;
//
//@Service
//public class ManagingTokens {
//
//
//
//	@Autowired
//	private ResterationTokenRepository  tokenRepository;
//
//
//	public RegisterToken createToken(MyUser user, String token) {
//	    RegisterToken registerToken = new  RegisterToken();
//	    registerToken.setUser(user);
//	    registerToken.setToken(token);
//	    
//	   
//	    long expirationTimeMillis = System.currentTimeMillis() + (48 * 60 * 60 * 1000);
//	    registerToken.setExpiryDate(new Date(expirationTimeMillis));
//	    
//	    return tokenRepository.save(registerToken);
//	}
//
//	public Optional<RegisterToken> findByToken(String token) {
//		return tokenRepository.findByToken(token);
//	}
//
//	public void deleteToken(RegisterToken token) {
//		tokenRepository.delete(token);
//	}
//
//	 public Optional<PasswordResetToken> findByUserId(Long userId) {
//		    return tokenRepository.findByUserId(userId);
//		}
//}
