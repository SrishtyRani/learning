package com.example.demo.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Model.MyUser;
import com.example.demo.Model.PasswordResetToken;
import com.example.demo.Repository.PasswordResetTokenRepository;

@Service
public class PasswordResetTokenService {

    @Autowired
    private PasswordResetTokenRepository tokenRepository;

    public PasswordResetToken createToken(MyUser user, String token) {
      
        Long userId = user.getId();
        Optional<PasswordResetToken> existingTokenOptional = tokenRepository.findByUserId(userId);
        if (existingTokenOptional.isPresent()) {
          
            PasswordResetToken existingToken = existingTokenOptional.get();
            existingToken.setToken(token);
        
            LocalDateTime expirationTime = LocalDateTime.now().plusHours(24);
            Date expiryDate = Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant());
            existingToken.setExpiryDate(expiryDate);
            return tokenRepository.save(existingToken);
        } else {
          
            PasswordResetToken resetToken = new PasswordResetToken();
            resetToken.setUser(user);
            resetToken.setToken(token);
          
            LocalDateTime expirationTime = LocalDateTime.now().plusHours(24);
            Date expiryDate = Date.from(expirationTime.atZone(ZoneId.systemDefault()).toInstant());
            resetToken.setExpiryDate(expiryDate);
            return tokenRepository.save(resetToken);
        }
    }

    public Optional<PasswordResetToken> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }

    public void deleteToken(PasswordResetToken resetToken) {
        tokenRepository.delete(resetToken);
    }

    public Optional<PasswordResetToken> findByUserId(Long userId) {
        return tokenRepository.findByUserId(userId);
    }

	public void updateToken(PasswordResetToken existingTokenOptional) {
		// TODO Auto-generated method stub
		
	}

}
