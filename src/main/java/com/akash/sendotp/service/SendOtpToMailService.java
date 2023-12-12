package com.akash.sendotp.service;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class SendOtpToMailService {

	@Autowired
	private JavaMailSender javaMailSender;
	private final Map<String, String> otpStorage = new HashMap<>();
	public void sendOtpService(String email) {
		String otp= generateOtp();
		otpStorage.put(email, otp);
		try {
			sendOtpToMail(email,otp);
			
		}catch(MessagingException e) {
			throw new RuntimeException("unable to send otp.");
		}
	}
	public boolean verifyOtp(String email, String userEnteredOtp) {
        String storedOtp = otpStorage.get(email);
        return storedOtp != null && storedOtp.equals(userEnteredOtp);
    }
	
	private String generateOtp() {
		SecureRandom random =new SecureRandom();
		int otp = 100000 + random.nextInt(90000);
		return String.valueOf(otp);
	}
	private void sendOtpToMail(String email, String otp) throws MessagingException {
	    MimeMessage mimeMessage = javaMailSender.createMimeMessage();
	    MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
	    mimeMessageHelper.setTo(email);
	    mimeMessageHelper.setSubject("Your OTP is ");
	    mimeMessageHelper.setText("<p>Your OTP is: " + otp + "</p>", true); // Set the third parameter to true
	    javaMailSender.send(mimeMessage);
	}


}
