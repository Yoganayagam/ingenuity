package com.ingenuity.transform;

import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractSource implements TransformOutputBufferInterface, TransformInterface {

    private short iOuputDatalinks      =   0;
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
    public short addOutputDatalinks() {

        return ++iOuputDatalinks;
    }

    @Override
    public short getTotalOutputDatalinks() {

        return iOuputDatalinks;
    }


    @Override
    public short addOutputBufferCount() {

        return ++bCount;
    }

    @Override
    public short getOutputBufferCount() {

        return bCount;
    }

    @Override
    public int addOutputBufferSize(short bCount, int size) {

        if (bufferSize.length < (bCount+1))
            bufferSize[bCount] = size;
        else
            bufferSize[bCount] = bufferSize[bCount] + size;

        return bufferSize[bCount];
    }

    @Override
    public int setOutputBufferSize(short buffer, int size) {
        if (bufferSize.length < (bCount+1)) //buffer not available
            return -1;
        else
            bufferSize[bCount] = size;
        return bufferSize[bCount];
    }

    @Override
    public int reduceOutputBufferSize(short bCount, int size) {

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
    public int[] getOutputBufferList() {
        return bufferSize;
    }
}
