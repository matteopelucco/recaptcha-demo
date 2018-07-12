package com.pelucco.recaptcha;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RecaptchaServiceInvisible extends RecaptchaService {

	@Value("${google.recaptcha.secret.invisible}")
	String recaptchaSecret;

	@Override
	public String getRecaptchaSecret() {
		return recaptchaSecret;
	}
	
}
