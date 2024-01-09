package com.github.paopaoyue.onmyojimod.utility;

import java.lang.reflect.Field;


public class Reflect {

    public static <O, T> T getPrivate(Class<O> cls, Object obj, String varName, Class<T> type) {
        try {
            Field f = cls.getDeclaredField(varName);
            f.setAccessible(true);
            return type.cast(f.get(obj));
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static <O, T> T getStaticPrivate(Class<O> cls, String varName, Class<T> type) {
        return getPrivate(cls, null, varName, type);
    }

    public static <O, T> void setPrivate(Class<O> cls, Object obj, String varName, T value) {
        try {
            Field f = cls.getDeclaredField(varName);
            f.setAccessible(true);
            f.set(obj, value);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }

    public static <O, T> void setStaticPrivate(Class<O> cls, String varName, T value) {
        setPrivate(cls, null, varName, value);
    }
}
