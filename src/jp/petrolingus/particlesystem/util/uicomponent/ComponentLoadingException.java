package jp.petrolingus.particlesystem.util.uicomponent;

public class ComponentLoadingException extends RuntimeException {
	
	public ComponentLoadingException() {
	}
	
	public ComponentLoadingException(String message) {
		super(message);
	}
	
	public ComponentLoadingException(String message, Throwable cause) {
		super(message, cause);
	}
	
	public ComponentLoadingException(Throwable cause) {
		super(cause);
	}
	
	public ComponentLoadingException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
