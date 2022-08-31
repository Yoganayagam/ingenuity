package com.ingenuity.flow.transform;

import com.ingenuity.transform.AbstractTransform;
import com.ingenuity.transform.AbstractTransformLink;
import com.ingenuity.transform.TransformInterface;
import com.ingenuity.transform.TransformLinkInterface;

import java.util.List;
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
    public AbstractTransformLink removeLink(AbstractTransform sourceTransform, AbstractTransform targetTransform);
    public void addTransformGraphProperty(String pKey, String pValue);
    public ConcurrentHashMap<String, String> getTransformGraphProperties();

}
