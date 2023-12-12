package com.akash.sendotp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.akash.sendotp.service.SendOtpToMailService;

@RestController

public class HomeController {
	@Autowired
	private SendOtpToMailService sendOtpToMailService;
	@GetMapping("/")
	public String home() {
		return "Welcome  to send otp to mail";
	}
	
	@PostMapping("/sendOtp/{email}")
	
	public String sendOtpToMail(@RequestParam("email") String email) {
		sendOtpToMailService.sendOtpService(email);
		return "otp send Sucessfully"+email;
		
	}
	@PostMapping("/verifyOtp")
    public String verifyOtp(@RequestParam("email") String email, @RequestParam("otp") String userEnteredOtp) {
        if (sendOtpToMailService.verifyOtp(email, userEnteredOtp)) {
            return "OTP Verified Successfully";
        } else {
            return "Invalid OTP. Please try again.";
        }
	

	

	}
	}
