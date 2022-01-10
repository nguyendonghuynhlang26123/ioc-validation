package utils;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.zip.DataFormatException;

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

    static Set<String> allowedDateFormat = Set.of(
            "dd/MM/yyyy",
            "MM/DD/YYYY",
            "DD/MM/YYYY",
            "YYYY/MM/DD",
            "dd-MM-yyyy",
            "DD-MM-YYYY",
            "MM-DD-YYYY",
            "YYYY-MM-DD"
    );

    public static Class wrapTypeIfPrimitive(Class clazz){
        return clazz.isPrimitive() ? primitiveMapper.get(clazz) : clazz;
    }

    public static ChronoLocalDate parseDate(String date) throws DataFormatException {
        ChronoLocalDate parsedDate = null;
        for(String format: allowedDateFormat){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
            try {
                parsedDate = ChronoLocalDate.from(formatter.parse(date));
                break;
            } catch (DateTimeParseException ignored){
            }
        }
        if (parsedDate==null){
            throw new DataFormatException("Date format not allowed");
        }
        return parsedDate;
    }
}
