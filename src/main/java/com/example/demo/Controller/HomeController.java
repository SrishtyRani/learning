package com.example.demo.Controller;


import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

	@GetMapping("/home")
	public String Home() {
		return "text";
	}

	@GetMapping("/admin/home")
	 public String admin() {


		return "home_admin";
	}

	@GetMapping("/user/home")
	public String user() {
		return "Home_user";
	}

	@GetMapping("/login")
	public String handleLogin() {

		  Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		  if(authentication==null||authentication instanceof AnonymousAuthenticationToken){
			  
			  return"auth-normal-sign-in";
			  
		  }
		  else {
			  if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMIN"))) {
	                return "redirect:/admin/home"; 
	            } else if (authentication.getAuthorities().stream().anyMatch(r -> r.getAuthority().equals("ROLE_USER"))) {
	                return "redirect:/user/home"; 
	            } else {
	             
	                return "redirect:/admin/home";
	            }

		  }


	}
}




