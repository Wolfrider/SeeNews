package com.oliver.seenews.base.arch;

import java.util.HashMap;

public class ServiceLocator {

    private HashMap<String, Object> mObjs;

    private static class Holder {
        private static final ServiceLocator INSTANCE = new ServiceLocator();
    }

    public static ServiceLocator getInstance() {
        return Holder.INSTANCE;
    }

    private ServiceLocator() {
        mObjs = new HashMap<>();
    }

    public void put(Object obj, Class<?> clazz) {
        mObjs.put(clazz.getName(), obj);
    }

    public Object get(Class<?> clazz) {
        return mObjs.get(clazz.getName());
    }

}
