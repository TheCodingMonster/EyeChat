package com.eyeball.utils.misc;

import java.util.List;


/**
 * A Utility class.
 * @author Eyeball
 *
 */
public class Utils {

	/**
	 * 
	 * Copy the contents of a Object[] to a List.
	 * 
	 * @param dest
	 *            The destination list.
	 * @param source
	 *            The original list.
	 * @return The new list.
	 * 
	 * @throws ClassCastException
	 *             If somehow an object cannot be added to the list.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List arrayCopy(List dest, Object[] source) throws ClassCastException {
		for (Object o : source)
			dest.add(o);
		return dest;
	}

}
