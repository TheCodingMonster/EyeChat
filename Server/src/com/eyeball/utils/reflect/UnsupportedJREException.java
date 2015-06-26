package com.eyeball.utils.reflect;

import java.io.IOException;

public class UnsupportedJREException extends IOException {

	private static final long serialVersionUID = 1L;
	private Throwable cause;

	public UnsupportedJREException(String message) {
		super(message);
	}

	public UnsupportedJREException(String message, Throwable cause) {
		super(message);
		this.cause = cause;
	}
	
	@Override
	public Throwable getCause() {
		return cause;
	}

}
