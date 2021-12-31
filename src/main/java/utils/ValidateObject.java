package utils;

import utils.Helper;

public class ValidateObject<T> {
    Class<T> type;
    T value;
    public ValidateObject(Class<T> type, T value){
        this.type = Helper.wrapTypeIfPrimitive(type);
        this.value = value;
    }

    public Class<T> getType() {
        return type;
    }

    public T getValue() {
        return value;
    }
}
