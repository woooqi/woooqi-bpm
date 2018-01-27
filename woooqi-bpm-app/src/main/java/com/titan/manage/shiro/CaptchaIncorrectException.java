package com.titan.manage.shiro;

import org.apache.shiro.authc.AuthenticationException;

public class CaptchaIncorrectException extends AuthenticationException {

	private static final long serialVersionUID = 1L;

	public CaptchaIncorrectException() {
		super();
	}

	public CaptchaIncorrectException(String message, Throwable cause) {
		super(message, cause);
	}

	public CaptchaIncorrectException(String message) {
		super(message);
	}

	public CaptchaIncorrectException(Throwable cause) {
		super(cause);
	}
}
