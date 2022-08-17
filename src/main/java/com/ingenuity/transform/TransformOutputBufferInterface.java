package com.ingenuity.transform;

public interface TransformOutputBufferInterface {

    public short addOutputBufferCount();

    public short getOutputBufferCount();

    public int addOutputBufferSize(short buffer, int size);

    public int setOutputBufferSize(short buffer, int size);

    public int reduceOutputBufferSize(short buffer, int size);

    public short addOutputDatalinks();

    public short getTotalOutputDatalinks();

    public int[] getOutputBufferList();

}
