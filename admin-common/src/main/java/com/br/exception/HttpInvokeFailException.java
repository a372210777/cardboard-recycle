package com.br.exception;


public class HttpInvokeFailException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public HttpInvokeFailException(String message, Throwable cause) {
        super(message, cause);
    }

    public HttpInvokeFailException(Throwable cause) {
        super(cause);
    }

    public HttpInvokeFailException(String message) {
        super(message);
    }
    
}
