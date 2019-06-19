package cn.springboot.osbulkparts.common;

public class CommonBusinessException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5498261068354488351L;

	public CommonBusinessException() {
		super();
	}

	public CommonBusinessException(String message) {
		super(message);
	}

	public CommonBusinessException(String message, Throwable cause) {
		super(message, cause);
    }
}
