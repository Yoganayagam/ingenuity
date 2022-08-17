package com.ingenuity.transform;

import java.util.concurrent.ConcurrentHashMap;

public interface TransformInterface {


    public void addTransformProperty(String pKey, String pValue);

    public ConcurrentHashMap<String, String> getTransformProperties();


}
