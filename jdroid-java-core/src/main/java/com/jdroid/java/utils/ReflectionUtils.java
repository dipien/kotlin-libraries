package com.jdroid.java.utils;

import com.jdroid.java.domain.Identifiable;
import com.jdroid.java.exception.UnexpectedException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Reflection related utilities
 */
public abstract class ReflectionUtils {

	public static Class<?> getClass(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new UnexpectedException(e);
		}
	}
	
	public static Class<?> getSafeClass(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			return null;
		}
	}

	public static <T> T safeNewInstance(String className) {
		try {
			return (T)newInstance(className);
		} catch (Exception e) {
			return null;
		}
	}

	public static <T> T newInstance(String className) {
		return (T)newInstance(getClass(className));
	}
	
	/**
	 * Create a class for the specified type,.
	 * 
	 * @param <T> the class to instantiate to be returned
	 * @param clazz the class to be instantiated
	 * @return an instance of the class specified
	 */
	public static <T> T newInstance(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch (Exception e) {
			throw new UnexpectedException("Unable to instantiate class [" + clazz.getSimpleName() + "]", e);
		}
	}

	public static <T> T newInstance(String className, List<Class<?>> parameterTypes, List<Object> parameterValues) {
		return (T)newInstance(getClass(className), parameterTypes, parameterValues);
	}
	
	/**
	 * Create a class for the specified type, using the specified constructor with the passed parameters.
	 * 
	 * @param <T> the class to instantiate to be returned
	 * @param clazz the class to be instantiated
	 * @param parameterTypes a constructor with this parameters will be used to instantiate the class
	 * @param parameterValues parameter values to be used when instantiating
	 * @return an instance of the class specified
	 */
	public static <T> T newInstance(Class<T> clazz, List<Class<?>> parameterTypes, List<Object> parameterValues) {
		try {
			Constructor<T> constructor = clazz.getConstructor(parameterTypes.toArray(new Class[0]));
			return constructor.newInstance(parameterValues.toArray(new Object[0]));
		} catch (Exception e) {
			throw new UnexpectedException("Unable to instantiate class [" + clazz.getSimpleName() + "]", e);
		}
	}
	
	/**
	 * Set a value in the given object without using getters or setters
	 *
	 * @param object The object where we want to set the field
	 * @param fieldName The name of the field to set
	 * @param value The new value to set
	 */
	public static void set(Object object, String fieldName, Object value) {
		Field field = ReflectionUtils.getField(object.getClass(), fieldName);
		field.setAccessible(true);
		try {
			field.set(object, value);
		} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
			throw new UnexpectedException(e);
		} finally {
			field.setAccessible(false);
		}
	}
	
		/**
	 	 * Set a value in a static field
	 	 *
	 	 * @param clazz The Class whose field should be modified
	 	 * @param fieldName The name of the field to set
	 	 * @param value The new value to set
	 	 */
		public static void setStaticField(Class<?> clazz, String fieldName, Object value) {
			Field field = ReflectionUtils.getField(clazz, fieldName);
			field.setAccessible(true);
			try {
				field.set(null, value);
			} catch (SecurityException | IllegalArgumentException | IllegalAccessException e) {
				throw new UnexpectedException(e);
			} finally {
				field.setAccessible(false);
			}
		}
	
	public static Object get(Field field, Object object) {
		field.setAccessible(true);
		try {
			return field.get(object);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			throw new UnexpectedException(e);
		}
	}
	
	public static Object get(Object object, String fieldName) {
		Field field = getField(object, fieldName);
		field.setAccessible(true);
		return get(field, object);
	}

	public static Object invokeStaticMethod(String className, String methodName, List<Class<?>> parameterTypes, List<Object> parameterValues) {
		return invokeStaticMethod(getClass(className), methodName, parameterTypes, parameterValues);
	}

	public static Object invokeStaticMethod(Class<?> clazz, String methodName, List<Class<?>> parameterTypes, List<Object> parameterValues) {
		return invokeMethod(clazz, null, methodName, parameterTypes, parameterValues);
	}

	public static Object invokeMethod(String className, Object obj, String methodName, List<Class<?>> parameterTypes, List<Object> parameterValues) {
		return invokeMethod(getClass(className), obj, methodName, parameterTypes, parameterValues);
	}

	public static Object invokeMethod(Class<?> clazz, Object obj, String methodName, List<Class<?>> parameterTypes, List<Object> parameterValues) {
		try {
			Method method = clazz.getMethod(methodName, parameterTypes.toArray(new Class[0]));
			return method.invoke(obj, parameterValues.toArray(new Object[0]));
		} catch (NoSuchMethodException e) {
			throw new UnexpectedException(e);
		} catch (InvocationTargetException e) {
			throw new UnexpectedException(e);
		} catch (IllegalAccessException e) {
			throw new UnexpectedException(e);
		}
	}

	public static Object getStaticFieldValue(Class<?> clazz, String fieldName) {
		Field field = getField(clazz, fieldName);
		return get(field, (Object)null);
	}

	public static Object getStaticFieldValue(Class<?> clazz, String fieldName, Object defaultValue) {
		try {
			Field field = clazz.getDeclaredField(fieldName);
			field.setAccessible(true);
			return field.get(null);
		} catch (IllegalAccessException | IllegalArgumentException e) {
			throw new UnexpectedException(e);
		} catch (NoSuchFieldException e) {
			return defaultValue;
		}
	}

	public static Field getField(Object object, String fieldName) {
		try {
			return object.getClass().getDeclaredField(fieldName);
		} catch (SecurityException | NoSuchFieldException e) {
			throw new UnexpectedException(e);
		}
	}
	
	/**
	 * Returns a {@link Field} from a class or any of its super classes.
	 * 
	 * @param clazz The Class whose {@link Field} is looked for
	 * @param fieldName The name of the {@link Field} to get
	 * @return The {@link Field}
	 */
	public static Field getField(Class<?> clazz, String fieldName) {
		
		try {
			return clazz.getDeclaredField(fieldName);
		} catch (NoSuchFieldException e) {
			
			// If the field wasn't found in the object class, its superclass must
			// be checked
			if (clazz.getSuperclass() != null) {
				return ReflectionUtils.getField(clazz.getSuperclass(), fieldName);
			}
			// If the field wasn't found and the object doesn't have a
			// superclass, an exception is thrown
			throw new UnexpectedException("The class '" + clazz.getName() + "' doesn't have a field named '"
					+ fieldName + "'.");
		}
	}
	
	/**
	 * Returns the value of a given {@link Field} from an {@link Object}. Or null if the {@link Object} doesn't have a
	 * {@link Field} with the given name.
	 * 
	 * @param <T> The type of the value
	 * @param object The {@link Object} whose value is being retrieved
	 * @param fieldName The name of the {@link Field}
	 * @return The value of the {@link Field}
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getFieldValue(Object object, String fieldName) {
		Field field = ReflectionUtils.getField(object.getClass(), fieldName);
		field.setAccessible(true);
		try {
			return (T)field.get(object);
		} catch (Exception ex) {
			return null;
		}
	}
	
	public static Class<?> getType(Object object, String fieldName) {
		try {
			Field field = object.getClass().getDeclaredField(fieldName);
			return field.getType();
		} catch (SecurityException | NoSuchFieldException e) {
			throw new UnexpectedException(e);
		}
	}
	
	public static void setId(Identifiable identifiable, Object id) {
		ReflectionUtils.set(identifiable, "id", id);
	}
}
