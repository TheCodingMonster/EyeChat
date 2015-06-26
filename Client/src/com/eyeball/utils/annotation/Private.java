package com.eyeball.utils.annotation;

/**
 * 
 * Use this annotation on a method or field to say it is private. Example usage:
 * An interface says the method is public.<br>
 * <br>
 * <code>
 * {@literal @}Private<br>
 * public void someMethod() {<br>
 * <br>
 * }
 * </code>
 * 
 * @author Eyeball
 * 
 */
public @interface Private {
	
	String reason();

}
