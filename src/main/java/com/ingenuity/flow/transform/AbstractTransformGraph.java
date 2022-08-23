package com.ingenuity.flow.transform;

import com.ingenuity.transform.*;
import com.sun.xml.internal.ws.util.StringUtils;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractTransformGraph implements TransformGraphInterface {

    //private List<TransformInterface> lstTransformInterface =   new ArrayList<TransformInterface>();
    private HashMap<Integer, AbstractTransform> mapTransformInterface = new HashMap<Integer, AbstractTransform>();
    private HashMap<Integer, AbstractTransformLink[]> mapTransformForwardLink = new HashMap<Integer, AbstractTransformLink[]>();
    private HashMap<Integer, AbstractTransformLink[]> mapTransformBackwardLink = new HashMap<Integer, AbstractTransformLink[]>();
    private HashMap<Integer, AbstractTransformLink> mapTransformLinkInterface = new HashMap<Integer, AbstractTransformLink>();

    private ConcurrentHashMap<String, String> transformGraphProperties = new ConcurrentHashMap<String, String>();
    private Set<Integer> transformKeys        =   new HashSet<Integer>();
    private Set<Integer> transformLinkKeys        =   new HashSet<Integer>();
    String transformGraphName;

    int transformGraphID;

    @Override
    public String setTransformGraphName(String name) {
        return this.transformGraphName = name;
    }

    @Override
    public String getTransformGraphName() {
        return this.transformGraphName;
    }

    @Override
    public int setTransformGraphID(int ID) {
        return this.transformGraphID = ID;
    }

    @Override
    public int getTransformID() {
        return this.transformGraphID;
    }

    @Override
    public AbstractTransform addTransform(AbstractTransform transform) {
        mapTransformInterface.put(transform.hashCode(),transform);
        this.transformKeys = mapTransformInterface.keySet();
        return transform;
    }

    @Override
    public AbstractTransform updateTransform(AbstractTransform oldtransform, AbstractTransform transform) {

        if ( oldtransform.getTransformName().equals(transform.getTransformName())) {
            mapTransformInterface.replace(transform.hashCode(), transform);
        } else { // changing transform-name is time consuming as per below logic

            oldHashCode =   oldtransform.hashCode();
            hashCode  =   transform.hashCode();

            if (mapTransformInterface.containsKey(oldHashCode)) {
                mapTransformInterface.remove(oldHashCode);
                mapTransformInterface.put(hashCode, transform);
            }

            this.transformKeys = mapTransformInterface.keySet();

            if (mapTransformForwardLink.containsKey(oldHashCode)) {
                AbstractTransformLink[] links = mapTransformForwardLink.get(oldHashCode);
                for (AbstractTransformLink link : links) {
                    mapTransformLinkInterface.remove(link.hashCode());
                    link.setTransformLinkName(link.getTransformLinkName().replaceFirst(oldtransform.getTransformName(), transform.getTransformName()));
                    mapTransformLinkInterface.put(link.hashCode(), link);
                }
                mapTransformForwardLink.put(hashCode, links);
                mapTransformForwardLink.remove(oldHashCode);
            }

            if (mapTransformBackwardLink.containsKey(oldHashCode)) {
                AbstractTransformLink[] links = mapTransformBackwardLink.get(oldHashCode);
                for (AbstractTransformLink link : links) {
                    mapTransformLinkInterface.remove(link.hashCode());
                    link.setTransformLinkName(link.getTransformLinkName().replaceAll(oldtransform.getTransformName().concat("$"), transform.getTransformName()));
                    mapTransformLinkInterface.put(link.hashCode(), link);
                }
                mapTransformBackwardLink.put(hashCode, links);
                mapTransformBackwardLink.remove(oldHashCode);
            }
        }
        return transform;
    }

    @Override
    public AbstractTransform removeTransform(AbstractTransform transform) {
        AbstractTransform outVal = null;
        int srcHashCode = transform.hashCode();

        if(mapTransformForwardLink.containsKey(srcHashCode)){

            AbstractTransformLink[] links = mapTransformForwardLink.get(srcHashCode);
            for(AbstractTransformLink link:links){
                mapTransformLinkInterface.remove(link.hashCode());
            }
            mapTransformForwardLink.remove(srcHashCode);
        }

        if(mapTransformBackwardLink.containsKey(srcHashCode)){

            AbstractTransformLink[] links = mapTransformBackwardLink.get(srcHashCode);
            for(AbstractTransformLink link:links){
                mapTransformLinkInterface.remove(link.hashCode());
            }
            mapTransformBackwardLink.remove(srcHashCode);
        }

        if( this.transformKeys.contains(srcHashCode) )
            outVal = mapTransformInterface.get(srcHashCode);

        // remove transform links as well

        return outVal;
    }

    @Override
    public AbstractTransformLink connectTransform(AbstractTransform sourceTransform, AbstractTransform targetTransform) {

        TransformLink transformLink =   new TransformLink(sourceTransform, targetTransform);
        mapTransformLinkInterface.put(transformLink.hashCode(), transformLink);
        transformLinkKeys = mapTransformLinkInterface.keySet();

        return transformLink;
    }

    @Override
    public AbstractTransform removeLink(AbstractTransform sourceTransform, AbstractTransform targetTransform) {
        //return null;
    }

    @Override
    public void addTransformGraphProperty(String pKey, String pValue) {
        this.transformGraphProperties.put(pKey, pValue);
    }

    @Override
    public ConcurrentHashMap<String, String> getTransformGraphProperties() {
        return this.transformGraphProperties;
    }
}
