package com.frnomad.pluginbasic.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class Store {

    private static HashMap<String, List<Object>> storeList = new HashMap<>();
    private static HashMap<String, Object> storeObj = new HashMap<>();

    public static void set(String key, Object obj) {
        storeObj.put(key, obj);
    }

    public static void removeObj(String key) {
        storeObj.remove(key);
    }

    public static Object get(String key) {
        return storeObj.get(key);
    }

    public static void register(String key, Class cls) {
        storeList.put(key, new ArrayList<>());
    }

    public static void removeList(String key) {
        storeList.remove(key);
    }

    public static Object getList(String key) {
        return storeList.get(key);
    }

    public static void add(String key, Object obj) {
        List<Object> prev = storeList.get(key);
        prev.add(obj);
        storeList.put(key, prev);
    }

}
