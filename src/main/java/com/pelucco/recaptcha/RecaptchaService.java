package com.pelucco.recaptcha;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;

public abstract class RecaptchaService  {

	Logger log = LoggerFactory.getLogger(RecaptchaService.class);
	
	private static final String GOOGLE_RECAPTCHA_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";
	
	@Autowired
	RestTemplateBuilder restTemplateBuilder;
	
	public String verifyRecaptcha(String ip, String recaptchaResponse) {
		Map<String, String> body = new HashMap<>();
		body.put("secret", getRecaptchaSecret());
		body.put("response", recaptchaResponse);
		body.put("remoteip", ip);
		log.debug("Request body for recaptcha: {}", body);
		ResponseEntity<Map> recaptchaResponseEntity = restTemplateBuilder.build().postForEntity(
				GOOGLE_RECAPTCHA_VERIFY_URL + "?secret={secret}&response={response}&remoteip={remoteip}", body,
				Map.class, body);
		log.debug("Response from recaptcha: {}", recaptchaResponseEntity);
		Map<String, Object> responseBody = recaptchaResponseEntity.getBody();
		boolean recaptchaSucess = (Boolean) responseBody.get("success");
		if (!recaptchaSucess) {
			List<String> errorCodes = (List) responseBody.get("error-codes");
			String errorMessage = errorCodes.stream().map(s -> RecaptchaUtil.RECAPTCHA_ERROR_CODE.get(s))
					.collect(Collectors.joining(", "));
			return errorMessage;
		} else {
			return StringUtils.EMPTY;
		}
	}

	public abstract String getRecaptchaSecret();
	
}
