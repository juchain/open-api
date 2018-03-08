package com.blockshine.api.util.exception;

import static java.lang.String.format;

public class BaseException extends RuntimeException {

    /**
	 * 
	 */
	private static final long serialVersionUID = -4958775625092469866L;

	public BaseException() {
        super();
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(Throwable cause, String message, Object... args) {
        super(format(message, args), cause);
    }
}
