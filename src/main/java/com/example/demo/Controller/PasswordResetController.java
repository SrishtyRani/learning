//
//package com.example.demo.Controller;
//
//import java.util.Date;
//import java.util.Optional;
//import java.util.UUID;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Controller;
//import //
//org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import com.example.demo.Model.MyUser;
//import com.example.demo.Model.PasswordResetToken;
//import com.example.demo.Repository.MyUserRepository;
//import com.example.demo.Service.EmailService;
//
//import com.example.demo.Service.PasswordResetTokenService;
//
//@Controller
//public class PasswordResetController {
//
//	@Autowired
//	private MyUserRepository userRepository;
//
//	@Autowired
//	private PasswordResetTokenService tokenService;
//
//	@Autowired
//	private PasswordEncoder passwordEncoder;
//
//	@Autowired
//	private EmailService emailService;
//
//	@GetMapping("/password-forgot")
//	public String showForgotPasswordForm() {
//		return "password_forget_form";
//	}
//
//	@PostMapping("/password-forgot")
//	public String processForgotPassword(@RequestParam("email") String email) {
//		Optional<MyUser> optionalUser = userRepository.findByEmail(email);
//		if (optionalUser.isPresent()) {
//			MyUser user = optionalUser.get();
//			Long userId = user.getId();
//			Optional<PasswordResetToken> existingTokenOptional = tokenService.findByUserId(userId);
//
//			String token;
//			if (existingTokenOptional.isPresent()) {
//				token = existingTokenOptional.get().getToken();
//			} else {
//				token = UUID.randomUUID().toString();
//				tokenService.createToken(user, token);
//			}
//
//			String resetLink = "http://localhost:8080/password-reset?token=" + token;
//			emailService.sendPasswordResetEmail(email, resetLink);
//
//			return "redirect:/password-forgot?success";
//		} else {
//			return "redirect:/password-forgot?error";
//		}
//	}
//
//	@GetMapping("/password-reset")
//	public String showResetPasswordForm(@RequestParam("token") String token) {
//		Optional<PasswordResetToken> optionalToken = tokenService.findByToken(token);
//		if (optionalToken.isPresent()) {
//
//			return "password_reset_form";
//		} else {
//
//			return "redirect:/password-forgot?error";
//		}
//	}
//
//	@PostMapping("/password-reset")
//	public String processResetPassword(@RequestParam("token") String token, @RequestParam("password") String password) {
//		Optional<PasswordResetToken> optionalToken = tokenService.findByToken(token);
//		if (optionalToken.isPresent()) {
//			PasswordResetToken resetToken = optionalToken.get();
//			if (resetToken.getExpiryDate().after(new Date())) {
//				MyUser user = resetToken.getUser();
//				user.setPassword(passwordEncoder.encode(password));
//				userRepository.save(user);
//
//				tokenService.deleteToken(resetToken);
//				return "redirect:/login?resetSuccess";
//			} else {
//
//				return "redirect:/password-forgot?error=expired";
//			}
//		} else {
//
//			return "redirect:/password-forgot?error";
//		}
//	}
//
//}
