package com.ingenuity.transform;

import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractTarget implements TransformInputBufferInterface, TransformInterface{

    private short iInputDatalinks =   0;
    private ConcurrentHashMap<String, String> transformProperties = new ConcurrentHashMap<String, String>();

    private short bCount  =   0;

    private int bufferSize[] = new int[]{};

    @Override
    public void addTransformProperty(String pKey, String pValue) {
        transformProperties.putIfAbsent(pKey, pValue);
    }

    @Override
    public ConcurrentHashMap<String, String> getTransformProperties() {
        return transformProperties;
    }

    @Override
    public short addInputBufferCount() {
        return ++bCount;
    }

    @Override
    public short getInputBufferCount() {
        return bCount;
    }

    @Override
    public int addInputBufferSize(short buffer, int size) {
        if (bufferSize.length < (bCount+1))
            bufferSize[bCount] = size;
        else
            bufferSize[bCount] = bufferSize[bCount] + size;

        return bufferSize[bCount];
    }

    @Override
    public int setInputBufferSize(short buffer, int size) {
        if (bufferSize.length < (bCount+1)) //buffer not available
            return -1;
        else
            bufferSize[bCount] = size;
        return bufferSize[bCount];
    }

    @Override
    public int reduceInputBufferSize(short buffer, int size) {
        if (bufferSize.length < (bCount+1))     //buffer not available
            return -1;
        else if (bufferSize[bCount] < size)     //existing buffer size less than reduce size value
            return -1;
        else {
            bufferSize[bCount] = bufferSize[bCount] + size;
        }
        return bufferSize[bCount];
    }

    @Override
    public short addInputDatalinks() {
        return ++this.iInputDatalinks;
    }

    @Override
    public short getTotalInputDataLinks() {
        return iInputDatalinks;
    }

    @Override
    public int[] getInputBufferList() {
        return bufferSize;
    }
}
