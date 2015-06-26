package com.eyeball.utils.reflect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import com.eyeball.utils.misc.Utils;

/**
 * 
 * A class with reflection related things such as invoking methods, getting
 * fields and more.
 * 
 * @author Eyeball
 * 
 */
public class ReflectionHelper {

	/**
	 * 
	 * Gets a list of all the annotations.
	 * 
	 * @param className
	 *            The class name.
	 * @return All the annotations.
	 * @throws ClassNotFoundException
	 *             If the specified class cannot be found.
	 */
	public static Annotation[] getAnnotations(String className)
			throws ClassNotFoundException {

		Class<?> theClass = Class.forName(className);

		ArrayList<Annotation> annoArrayList = new ArrayList<Annotation>();

		Annotation[] annoDeclared = theClass.getDeclaredAnnotations();

		Annotation[] anno = theClass.getAnnotations();

		Utils.arrayCopy(annoArrayList, annoDeclared);

		Utils.arrayCopy(annoArrayList, anno);

		return annoArrayList.toArray(new Annotation[] {});

	}

	/**
	 * 
	 * Gets a list of all the annotations.
	 * 
	 * @param method
	 *            The method.
	 * @return All the annotations.
	 */
	public static Annotation[] getAnnotations(Method method) {

		ArrayList<Annotation> annoArrayList = new ArrayList<Annotation>();

		Annotation[] annoDeclared = method.getDeclaredAnnotations();

		Annotation[] anno = method.getAnnotations();

		Utils.arrayCopy(annoArrayList, annoDeclared);

		Utils.arrayCopy(annoArrayList, anno);

		return annoArrayList.toArray(new Annotation[] {});

	}

	/**
	 * 
	 * Gets all the annotations of the specified type.
	 * 
	 * @param className
	 *            The class to check
	 * @param type
	 *            The Annotation type.
	 * @return The annotation, or null if it does not exist.
	 * @throws ClassNotFoundException
	 *             If the class cannot be found.
	 */
	public static <A extends Annotation> A getAnnotationWithType(
			String className, Class<A> type) throws ClassNotFoundException {
		Class<?> c = Class.forName(className);
		A anno = c.getAnnotation(type);
		return anno;
	}

	/**
	 * 
	 * Gets all the annotations of the specified type.
	 * 
	 * @param method
	 *            The method.
	 * @param type
	 *            The Annotation type.
	 * @return The annotation, or null if it does not exist.
	 */
	public static <A extends Annotation> A getAnnotationWithType(Method method,
			Class<A> type) {
		A anno = method.getAnnotation(type);
		return anno;
	}

	/**
	 * 
	 * Gets a class by name.
	 * 
	 * @param className
	 *            The name of the class, with the package.
	 * @return The class if it exists.
	 * @throws ClassNotFoundException
	 *             If the class cannot be found.
	 */
	public static Class<?> getClass(String className)
			throws ClassNotFoundException {
		return Class.forName(className);
	}

	/**
	 * 
	 * Gets a specified field from a class.
	 * 
	 * @param classToAccess
	 *            The class.
	 * @param instance
	 *            The class instance, or null if the field is static.
	 * @param fieldName
	 *            The name of the field.
	 * @return The field.
	 * @throws NoSuchFieldException
	 *             If the field could not be found anywhere in the class.
	 * @throws SecurityException
	 *             If the security manager denies access to the
	 *             {@link Field#setAccessible(boolean)} method.
	 * @throws IllegalAccessException
	 *             If the field is private and
	 *             {@link Field#setAccessible(boolean)} somehow did not allow
	 *             reading. This could be due to a security manager.
	 */
	@SuppressWarnings("unchecked")
	public static <T, E> T getField(Class<? super E> classToAccess, E instance,
			String fieldName) throws NoSuchFieldException, SecurityException,
			IllegalAccessException {
		Field field = classToAccess.getDeclaredField(fieldName);
		field.setAccessible(true);
		return (T) field.get(instance);
	}

	/**
	 * 
	 * Sets a field to a specified value.
	 * 
	 * @param classToAccess
	 *            The class to access.
	 * @param instance
	 *            The instance.
	 * @param fieldName
	 *            The field name.
	 * @param newField
	 *            The value of the new field.
	 * @throws NoSuchFieldException
	 *             If the field does not exist.
	 * @throws SecurityException
	 *             If the security manager denies access to the
	 *             {@link Field#setAccessible(boolean)} method.
	 * @throws IllegalArgumentException
	 *             If newField is not the same type as the underlying field.
	 * @throws IllegalAccessException
	 *             If the security manager denies access from reading the field.
	 */
	public static <E> void setField(Class<? super E> classToAccess, E instance,
			String fieldName, Object newField) throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		Field field = classToAccess.getDeclaredField(fieldName);
		field.setAccessible(true);
		field.set(instance, newField);

	}

	/**
	 * 
	 * Sets a field to a specified value.
	 * 
	 * @param instance
	 *            The instance.
	 * @param field
	 *            The field.
	 * @param newField
	 *            The value of the new field.
	 * @throws IllegalArgumentException
	 *             If newField is not the same type as the underlying field.
	 * @throws IllegalAccessException
	 *             If the security manager denies access from reading the field.
	 */
	public static <E> void setField(E instance, Field field, Object newField)
			throws IllegalArgumentException, IllegalAccessException {
		field.setAccessible(true);
		field.set(instance, newField);
	}

	/**
	 * 
	 * Gets a method from a class.
	 * 
	 * @param className
	 *            The class which has the method.
	 * @param methodName
	 *            The method's name.
	 * @param argumentTypes
	 *            The arguments that the method takes.
	 * @return The method.
	 * @throws NoSuchMethodException
	 *             If the specified method does not exist.
	 * @throws SecurityException
	 *             If the security manager does not allow getting of the method.
	 * @throws ClassNotFoundException
	 *             If the specified class cannot be found.
	 * 
	 * @author Eyeball
	 * 
	 */
	public static Method getMethod(String className, String methodName,
			Class<?>... argumentTypes) throws NoSuchMethodException,
			SecurityException, ClassNotFoundException {
		Class<?> clazz = ReflectionHelper.getClass(className);
		Method method = clazz.getDeclaredMethod(methodName, argumentTypes);
		method.setAccessible(true);
		return method;
	}

	/**
	 * 
	 * Invokes a method with a return type..
	 * 
	 * @param className
	 *            The class that has the method.
	 * @param methodName
	 *            The method's name.
	 * @param instance
	 *            The instance. If it is a static method, this may be null.
	 *            Otherwise a NullPointerException will be thrown.
	 * @param arguments
	 *            The method's arguments.
	 * @throws ClassNotFoundException
	 *             If the specified class cannot be found.
	 * @throws NoSuchMethodException
	 *             If the specified method does not exist.
	 * @throws SecurityException
	 *             If the security manager does not allow reading and getting of
	 *             the method.
	 * @throws IllegalAccessException
	 *             Should not happen.
	 * @throws IllegalArgumentException
	 *             If instance is not the same as the class in className.
	 * @throws InvocationTargetException
	 *             If an exception was thrown during invocation of the method.
	 * @throws NullPointerException
	 *             If instance is null and it is not a static method.
	 * 
	 * @return tHE RESULT.
	 */
	public static <A> A invokeMethod(String className, String methodName,
			Object instance, Class<A> type, Object... arguments)
			throws ClassNotFoundException, NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NullPointerException {

		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

		for (Object object : arguments) {
			Class<?> c = object.getClass();
			classes.add(c);
		}

		Class<?>[] clazzes = classes.toArray(new Class[] {});

		Method method = ReflectionHelper.getMethod(className, methodName,
				clazzes);

		@SuppressWarnings("unchecked")
		A toReturn = (A) method.invoke(instance, arguments);

		return toReturn;
	}

	/**
	 * 
	 * Invokes a method with no return type..
	 * 
	 * @param className
	 *            The class that has the method.
	 * @param methodName
	 *            The method's name.
	 * @param instance
	 *            The instance. If it is a static method, this may be null.
	 *            Otherwise a NullPointerException will be thrown.
	 * @param arguments
	 *            The method's arguments.
	 * @throws ClassNotFoundException
	 *             If the specified class cannot be found.
	 * @throws NoSuchMethodException
	 *             If the specified method does not exist.
	 * @throws SecurityException
	 *             If the security manager does not allow reading and getting of
	 *             the method.
	 * @throws IllegalAccessException
	 *             Should not happen.
	 * @throws IllegalArgumentException
	 *             If instance is not the same as the class in className.
	 * @throws InvocationTargetException
	 *             If an exception was thrown during invocation of the method.
	 * @throws NullPointerException
	 *             If instance is null and it is not a static method.
	 * 
	 */
	public static void invokeMethod(String className, String methodName,
			Object instance, Object... arguments)
			throws ClassNotFoundException, NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException,
			NullPointerException {

		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

		for (Object object : arguments) {
			Class<?> c = object.getClass();
			classes.add(c);
		}

		Class<?>[] clazzes = classes.toArray(new Class[] {});

		Method method = ReflectionHelper.getMethod(className, methodName,
				clazzes);

		method.invoke(instance, arguments);
	}

	/**
	 * 
	 * Checks if a class exists.
	 * 
	 * @param className
	 *            The class to find.
	 * @return If it was found.
	 */
	public static boolean classExists(String className) {
		try {
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			return false;
		}
		return true;
	}

}
