package com.pelucco.recaptcha;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class RecaptchaServiceStandard extends RecaptchaService {

	@Value("${google.recaptcha.secret.standard}")
	String recaptchaSecret;

	@Override
	public String getRecaptchaSecret() {
		return recaptchaSecret;
	}
	
}
