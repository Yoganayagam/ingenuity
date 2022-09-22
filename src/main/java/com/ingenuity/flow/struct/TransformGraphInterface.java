package com.ingenuity.flow.struct;

import com.ingenuity.transform.AbstractTransform;
import com.ingenuity.transform.AbstractTransformLink;

import java.util.concurrent.ConcurrentHashMap;

public interface TransformGraphInterface {

    public String setTransformGraphName(String name);

    public String getTransformGraphName();

    public int setTransformGraphID(int ID);

    public int getTransformGraphID();
    public AbstractTransform addTransform(AbstractTransform transform);

    public AbstractTransform updateTransform(AbstractTransform oldtransform, AbstractTransform transform);
    public AbstractTransform removeTransform(AbstractTransform transform);
    public AbstractTransformLink connectTransform(AbstractTransform sourceTransform, AbstractTransform targetTransform);
    public AbstractTransformLink removeLink(AbstractTransformLink tLink);
    public void addTransformGraphProperty(String pKey, String pValue);
    public ConcurrentHashMap<String, String> getTransformGraphProperties();

}
