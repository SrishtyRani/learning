package com.example.demo.ApIConfigration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.example.demo.config.AuthenticationSuccessHandler;



@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
	
	 @Autowired
	    private JwtAuthenticationFilter filter;
	    

		@Autowired
		private DetailService userDetailService;							

		 @Bean
		
		     SecurityFilterChain restApiSecurityFilterChain(HttpSecurity http) throws Exception {

		        http.csrf(csrf -> csrf.disable())
		        .securityMatcher(new AntPathRequestMatcher("/api/v2/**"))
		        .cors(cors->cors.disable())
		        
		        .authorizeHttpRequests(registry -> {
					registry.requestMatchers("/api/v2/**").permitAll();
					registry.requestMatchers("/static/**").permitAll();
					registry.requestMatchers("/profilepic/**").permitAll();
					registry.anyRequest().authenticated();
					})
		        .sessionManagement(manager -> manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
		        .authenticationProvider(authenticationProvider()).addFilterBefore(
		        		 filter, UsernamePasswordAuthenticationFilter.class)
		        
		    	.formLogin(login -> login.loginPage("/user/login")
						.loginProcessingUrl("/user/login")
							.successHandler(new AuthenticationSuccessHandler())
							.permitAll())
		        		 
		        		    .logout(logout -> logout
		        	                .logoutUrl("/logout") 
		        	                .logoutSuccessUrl("/user/login")
		        	                .invalidateHttpSession(true)
		        	                .deleteCookies("JSESSIONID")
		        	            );
		     
		        return http.build();
		    }
		    
	    
	    
	    
	    @Bean
		SecurityFilterChain loginsecurityFilterChain(HttpSecurity http) throws Exception {
		
			return http
					.csrf(AbstractHttpConfigurer::disable)
					.authorizeHttpRequests(registry -> {
						registry.requestMatchers("/home").permitAll();
						registry.requestMatchers("/register/**").permitAll();
						registry.requestMatchers("/static/**").permitAll();
						registry.requestMatchers("/files/**").permitAll();
			
						registry.requestMatchers("/admins/**").hasRole("ADMIN");
				
						registry.requestMatchers("/login").permitAll();
					
						registry.anyRequest().authenticated();
					})
					
			

				.formLogin(login -> login.loginPage("/login")
						.loginProcessingUrl("/login")
							.successHandler(new AuthenticationSuccessHandler())
							.permitAll())
					.logout(logout -> logout.logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
							.logoutSuccessUrl("/Login")
							.permitAll())
					
					.build();

		}

	    
	    @Bean
	  AuthenticationManager authenticationManager(AuthenticationConfiguration builder) throws Exception {
	     return builder.getAuthenticationManager();
	 }
	    
	   
	    
	    	@Bean
		AuthenticationProvider authenticationProvider() {
			DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
			provider.setUserDetailsService(userDetailService);
			provider.setPasswordEncoder(passwordEncoder());
			return provider;
		}
	   
	    	@Bean
	   	PasswordEncoder passwordEncoder() {
	   		return new BCryptPasswordEncoder();
	    	}

	    	
	    	@Bean
	    	 MessageSource messageSource() {
	    	    ReloadableResourceBundleMessageSource messageSource
	    	      = new ReloadableResourceBundleMessageSource();
	    	    
	    	    messageSource.setBasename("classpath:messages");
	    	    messageSource.setDefaultEncoding("UTF-8");
	    	    return messageSource;
	    	}
	    	
	    	@Bean
	    	 LocalValidatorFactoryBean getValidator() {
	    	    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
	    	    bean.setValidationMessageSource(messageSource());
	    	    return bean;
	    	}
}
