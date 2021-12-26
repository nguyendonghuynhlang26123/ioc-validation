package utils;

import java.util.HashMap;
import java.util.Map;

public class Helper {
    final static Map<Class<?>, Class<?>> primitiveMapper = new HashMap<>();
    static {
        primitiveMapper.put(boolean.class, Boolean.class);
        primitiveMapper.put(byte.class, Byte.class);
        primitiveMapper.put(short.class, Short.class);
        primitiveMapper.put(char.class, Character.class);
        primitiveMapper.put(int.class, Integer.class);
        primitiveMapper.put(long.class, Long.class);
        primitiveMapper.put(float.class, Float.class);
        primitiveMapper.put(double.class, Double.class);
    }

    public static Class wrapTypeIfPrimitive(Class clazz){
        return clazz.isPrimitive() ? primitiveMapper.get(clazz) : clazz;
    }
}
