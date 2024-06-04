package com.example.demo.ApIConfigration;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.example.demo.Model.MyUser;
import com.example.demo.Repository.MyUserRepository;

@Component
public class DetailService implements UserDetailsService {

    private final MyUserRepository repository;

    public DetailService(MyUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MyUser user = repository.findByEmail(email)
                                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + email));
        
        if (!user.isVerified()) {
        	
            throw new UsernameNotFoundException("User not verified: " + email);
        }
        return User.builder()
                   .username(user.getEmail())
                   .password(user.getPassword())
                   .build();
    }
    

	public void authenticate(UsernamePasswordAuthenticationToken authentication) {
		// TODO Auto-generated method stub
		
	}
}


