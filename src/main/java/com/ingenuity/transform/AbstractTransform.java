package com.ingenuity.transform;

import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractTransform implements TransformInterface, TransformInputBufferInterface, TransformOutputBufferInterface{

    private short iInputDatalinks      =   0;
    private short iOuputDatalinks      =   0;
    private ConcurrentHashMap<String, String> transformProperties = new ConcurrentHashMap<String, String>();

    private short bCountInput =   0;
    private short bCountOutput =   0;
    private int bufferSizeInput[] = new int[]{};
    private int bufferSizeOutput[] = new int[]{};

    TransformType transformType;

    String transformName;

    int transformID;

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
        return ++bCountInput;
    }

    @Override
    public short getInputBufferCount() {
        return bCountInput;
    }

    @Override
    public int addInputBufferSize(short buffer, int size) {
        if (bufferSizeInput.length < (bCountInput +1))
            bufferSizeInput[bCountInput] = size;
        else
            bufferSizeInput[bCountInput] = bufferSizeInput[bCountInput] + size;

        return bufferSizeInput[bCountInput];
    }

    @Override
    public int setInputBufferSize(short buffer, int size) {
        if (bufferSizeInput.length < (bCountInput +1)) //buffer not available
            return -1;
        else
            bufferSizeInput[bCountInput] = size;
        return bufferSizeInput[bCountInput];
    }

    @Override
    public int reduceInputBufferSize(short buffer, int size) {
        if (bufferSizeInput.length < (bCountInput +1))     //buffer not available
            return -1;
        else if (bufferSizeInput[bCountInput] < size)     //existing buffer size less than reduce size value
            return -1;
        else {
            bufferSizeInput[bCountInput] = bufferSizeInput[bCountInput] + size;
        }
        return bufferSizeInput[bCountInput];
    }

    @Override
    public short addInputDatalinks() {
        return ++iInputDatalinks;
    }

    @Override
    public short getTotalInputDataLinks() {
        return iInputDatalinks;
    }

    @Override
    public int[] getInputBufferList() {
        return bufferSizeInput;
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

        return ++bCountOutput;
    }

    @Override
    public short getOutputBufferCount() {

        return bCountOutput;
    }

    @Override
    public int addOutputBufferSize(short bCount, int size) {

        if (bufferSizeOutput.length < (bCount+1))
            bufferSizeOutput[bCount] = size;
        else
            bufferSizeOutput[bCount] = bufferSizeOutput[bCount] + size;

        return bufferSizeOutput[bCount];
    }

    @Override
    public int setOutputBufferSize(short buffer, int size) {
        if (bufferSizeOutput.length < (bCountOutput +1)) //buffer not available
            return -1;
        else
            bufferSizeOutput[bCountOutput] = size;
        return bufferSizeOutput[bCountOutput];
    }

    @Override
    public int reduceOutputBufferSize(short bCount, int size) {

        if (bufferSizeOutput.length < (bCount+1))     //buffer not available
            return -1;
        else if (bufferSizeOutput[bCount] < size)     //existing buffer size less than reduce size value
            return -1;
        else {
            bufferSizeOutput[bCount] = bufferSizeOutput[bCount] + size;
        }
        return bufferSizeOutput[bCount];
    }

    @Override
    public int[] getOutputBufferList() {
        return bufferSizeOutput;
    }


    @Override
    public String setTransformName(String name) {
        return this.transformName = name;
    }

    @Override
    public String getTransformName() {
        return this.transformName;
    }

    @Override
    public int setTransformID(int ID) {
        this.transformID = ID;
        return this.transformID;
    }

    @Override
    public int getTransformID() {
        return transformID;
    }

    @Override
    public boolean equals(TransformInterface tInterface) {
        return this.transformName.equals(tInterface.getTransformName());
    }

    @Override
    public int hashCode() { // revisit
        return this.transformName.hashCode();
    }

    @Override
    public TransformType transformType(TransformType type) {
        return this.transformType = type;
    }

    @Override
    public TransformType transformType() {
        return this.transformType;
    }

}
