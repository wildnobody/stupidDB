package zech.stupidDB;

import java.lang.reflect.Field;

public class ClassManipulation {
	public static FieldDissect [] nameAndTypeList(Class<?> clazz) {
		Field [] field = clazz.getDeclaredFields();
		FieldDissect [] fieldDissects = new FieldDissect[field.length];
		for(int i = 0; i < field.length; i++) {
			fieldDissects[i] = new FieldDissect(field[i].getName(), field[i].getType().getSimpleName());
			if(field[i].isAnnotationPresent(PrimaryKey.class)) {
				fieldDissects[i].setPK(true);
				fieldDissects[i].setAutoincrement(((PrimaryKey) field[i].getAnnotation(PrimaryKey.class)).autoincrement());
			}
		}
		return fieldDissects;
	}
	
	public static FieldDissect [] dissectWithValues(Object currentObject) throws IllegalAccessException, NoSuchFieldException{
		Class<?> clazz = currentObject.getClass();
		FieldDissect [] values = nameAndTypeList(clazz);
		Field current;
		for(int i = 0; i < values.length; i++) {
			current = clazz.getDeclaredField(values[i].getName());
			current.setAccessible(true);
			values[i].setValue(String.valueOf(current.get(currentObject)));
		}
		return values;
	}
	
	public static String getPrimaryKey(Class<?> clazz) {
		for(Field field : clazz.getDeclaredFields()) {
			if(field.isAnnotationPresent(PrimaryKey.class)) {
				return field.getName();
			}
		}
		return null;
	}
	
	public static boolean hasPrimaryKey(Class<?> class1) {
		if(getPrimaryKey(class1) != null)
			return true;
		else
			return false;
	}
	
	public static Object getVariableValue(Object instance, String varName) throws NoSuchFieldException, IllegalAccessException{
		Field f  = instance.getClass().getDeclaredField(varName);
		f.setAccessible(true);
		return f.get(instance);
	}
	
	public static void setVariableValue(Object instance, String varName, Object newValue) throws NoSuchFieldException, IllegalAccessException {
		Field f  = instance.getClass().getDeclaredField(varName);
		f.setAccessible(true);
		f.set(instance, newValue);
	}
	
	//public static Object builder(Class<?> c, )
	
	
}
