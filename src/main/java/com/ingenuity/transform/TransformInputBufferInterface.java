package com.ingenuity.transform;

public interface TransformInputBufferInterface {


    public short addInputBufferCount();

    public short getInputBufferCount();

    public int addInputBufferSize(short buffer, int size);

    public int setInputBufferSize(short buffer, int size);

    public int reduceInputBufferSize(short buffer, int size);

    public short addInputDatalinks();

    public short getTotalInputDataLinks();

    public int[] getInputBufferList();


}
