package com.reborn.common.util;

import java.lang.reflect.Field;
/**
 * 
* @Title 反射工具

* @Description: TODO

* @author reborngao  Email 460600117@qq.com

* @CreateTime：2017年1月4日 下午12:25:22
 
* @version V1.0
 */
public class ReflectHelper {
	/**
	 * 获取obj对象fieldName的属性值
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static Object getValueByFieldName(Object obj, String fieldName)
			throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
				Field field = getFieldByFieldName(obj, fieldName);
				Object value = null;
				if(field!=null){
					if (field.isAccessible()) {
						value = field.get(obj);
					} else {
						field.setAccessible(true);  //setAccessible(true) 并不是将方法的访问权限改成了public，而是取消java的权限控制检查。
													//所以即使是public方法，其accessible 属相默认也是false
						value = field.get(obj);
						field.setAccessible(false);
					}
				}
				return value;
	}
	/**
	 * 获取obj对象fieldName的Field
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static Field getFieldByFieldName(Object obj, String fieldName){
		for(Class<?> superClass=obj.getClass();superClass!=Object.class;superClass=superClass){
			try {
				return superClass.getDeclaredField(fieldName);
			} catch (NoSuchFieldException e) {
			}
		}
		return null;
	}
	
	/**
	 * 设置obj对象fieldName的属性值
	 * @param obj
	 * @param fieldName
	 * @param value
	 * @throws SecurityException
	 * @throws NoSuchFieldException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	public static void setValueByFieldName(Object obj, String fieldName,
			Object value) throws SecurityException, NoSuchFieldException,
			IllegalArgumentException, IllegalAccessException {
		Field field = obj.getClass().getDeclaredField(fieldName);
		if (field.isAccessible()) {
			field.set(obj, value);
		} else {
			field.setAccessible(true);
			field.set(obj, value);
			field.setAccessible(false);
		}
	}
}
