package com.xiaojd.base.lang;

public class DjError extends Error {

	private static final long serialVersionUID = 1L;

	public DjError(Throwable cause) {
		super(cause);
		// TODO Auto-generated constructor stub
	}

	public DjError(String message) {
		super(message);
	}

}
