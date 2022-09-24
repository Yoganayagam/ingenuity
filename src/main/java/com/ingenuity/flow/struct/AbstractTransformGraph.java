package com.ingenuity.flow.struct;

import com.ingenuity.transform.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public abstract class AbstractTransformGraph implements TransformGraphInterface {

    //private List<TransformInterface> lstTransformInterface =   new ArrayList<TransformInterface>();
    private HashMap<Integer, AbstractTransform> mapTransformInterface = new HashMap<Integer, AbstractTransform>();
    private HashMap<Integer, AbstractTransform> mapSrcTransformInterface = new HashMap<Integer, AbstractTransform>();
    private HashMap<Integer, AbstractTransform> mapTgtTransformInterface = new HashMap<Integer, AbstractTransform>();
    private HashMap<Integer, TransformLinkSet> mapTransformForwardLink = new HashMap<Integer, TransformLinkSet>();
    private HashMap<Integer, TransformLinkSet> mapTransformBackwardLink = new HashMap<Integer, TransformLinkSet>();
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
    public int getTransformGraphID() {
        return this.transformGraphID;
    }

    @Override
    public AbstractTransform addTransform(AbstractTransform transform) {

        int hCode   =   transform.hashCode();

        mapTransformInterface.put(hCode,transform);

        switch (transform.getTransformType()) {

            case SOURCE:
                mapSrcTransformInterface.put(hCode,transform);
                break;
            case TARGET:
                mapTgtTransformInterface.put(hCode,transform);
                break;
            default:
                // The transform is neither Source or Target. Do Nothing applies.
        }

        this.transformKeys = mapTransformInterface.keySet();
        return transform;
    }

    @Override
    public AbstractTransform updateTransform(AbstractTransform oldtransform, AbstractTransform transform) {

        int hashCode   =   transform.hashCode();

        if ( oldtransform.getTransformName().equals(transform.getTransformName())) {
            mapTransformInterface.replace(hashCode, transform);

            switch (transform.getTransformType()) {

                case SOURCE:
                    mapSrcTransformInterface.replace(hashCode, transform);
                    break;
                case TARGET:
                    mapTgtTransformInterface.replace(hashCode, transform);
                    break;

            }

        } else { // changing transform-name is time consuming as per below logic

            int oldHashCode =   oldtransform.hashCode();
            //int hashCode  =   transform.hashCode();

            if (mapTransformInterface.containsKey(oldHashCode)) {
                mapTransformInterface.remove(oldHashCode);
                mapTransformInterface.put(hashCode, transform);

                switch (transform.getTransformType()) {

                    case SOURCE:
                        mapSrcTransformInterface.remove(oldHashCode);
                        mapSrcTransformInterface.put(hashCode, transform);
                        break;
                    case TARGET:
                        mapTgtTransformInterface.remove(oldHashCode);
                        mapTgtTransformInterface.put(hashCode, transform);
                        break;

                }

            }

            this.transformKeys = mapTransformInterface.keySet();

            if (mapTransformForwardLink.containsKey(oldHashCode)) {
                TransformLinkSet<TransformLink> links = mapTransformForwardLink.get(oldHashCode);
                for (TransformLink link : links) {
                    mapTransformLinkInterface.remove(link.hashCode());
                    link.setTransformLinkName(link.getTransformLinkName().replaceFirst(oldtransform.getTransformName(), transform.getTransformName()));
                    mapTransformLinkInterface.put(link.hashCode(), link);
                }
                mapTransformForwardLink.put(hashCode, links);
                transform.addOutputDatalinks();
                mapTransformForwardLink.remove(oldHashCode);
            }

            if (mapTransformBackwardLink.containsKey(oldHashCode)) {
                TransformLinkSet<TransformLink> links = mapTransformBackwardLink.get(oldHashCode);
                for (TransformLink link : links) {
                    mapTransformLinkInterface.remove(link.hashCode());
                    link.setTransformLinkName(link.getTransformLinkName().replaceAll(oldtransform.getTransformName().concat("$"), transform.getTransformName()));
                    mapTransformLinkInterface.put(link.hashCode(), link);
                }
                mapTransformBackwardLink.put(hashCode, links);
                transform.addInputDatalinks();
                mapTransformBackwardLink.remove(oldHashCode);
            }

            this.transformLinkKeys = mapTransformLinkInterface.keySet();
        }
        return transform;
    }

    @Override
    public AbstractTransform removeTransform(AbstractTransform transform) {
        AbstractTransform outVal = null;
        int srcHashCode = transform.hashCode();

        //remove transform links
        if(mapTransformForwardLink.containsKey(srcHashCode)){
            TransformLinkSet<TransformLink> links = mapTransformForwardLink.get(srcHashCode);
            for(TransformLink link:links){
                mapTransformLinkInterface.remove(link.hashCode());
            }
            mapTransformForwardLink.remove(srcHashCode);

        }

        if(mapTransformBackwardLink.containsKey(srcHashCode)){
            TransformLinkSet<TransformLink> links = mapTransformBackwardLink.get(srcHashCode);
            for(TransformLink link:links){
                mapTransformLinkInterface.remove(link.hashCode());
            }
            mapTransformBackwardLink.remove(srcHashCode);
        }

        this.transformLinkKeys  =   mapTransformLinkInterface.keySet();

        if( this.transformKeys.contains(srcHashCode) )
            outVal = mapTransformInterface.get(srcHashCode);

        // remove transform
        mapTransformInterface.remove(srcHashCode);

        switch (transform.getTransformType()) {

            case SOURCE:
                mapSrcTransformInterface.remove(srcHashCode);
                break;
            case TARGET:
                mapTgtTransformInterface.remove(srcHashCode);
                break;

        }

        this.transformKeys  =   mapTransformInterface.keySet();

        return outVal;
    }

    @Override
    public AbstractTransformLink connectTransform(AbstractTransform sourceTransform, AbstractTransform targetTransform) {

        TransformLink transformLink =   new TransformLink(sourceTransform, targetTransform);
        mapTransformLinkInterface.put(transformLink.hashCode(), transformLink);
        transformLinkKeys = mapTransformLinkInterface.keySet();

        // update Forward links for transform
        if (mapTransformForwardLink.containsKey(sourceTransform.hashCode())){
            TransformLinkSet<TransformLink> transformLinks1  =   mapTransformForwardLink.get(sourceTransform.hashCode());
            transformLinks1.add(transformLink);
            sourceTransform.addOutputDatalinks();
        } else{
            mapTransformForwardLink.put(sourceTransform.hashCode(), new TransformLinkSet(){ {add(transformLink);} });
            sourceTransform.addOutputDatalinks();
        }

        // update backward links for transform
        if (mapTransformBackwardLink.containsKey(targetTransform.hashCode())) {
            TransformLinkSet<TransformLink> transformLinks2 = mapTransformBackwardLink.get(targetTransform.hashCode());
            transformLinks2.add(transformLink);
            targetTransform.addInputDatalinks();
        } else {
            mapTransformBackwardLink.put(targetTransform.hashCode(), new TransformLinkSet(){ {add(transformLink);} });
            targetTransform.addInputDatalinks();
        }
        return transformLink;
    }

    @Override
    public AbstractTransformLink removeLink(AbstractTransformLink tLink) {
        //return new TransformLink(null, null);

        int tLinkhasCode = tLink.hashCode();

        // update Forward links for transform
        TransformLinkSet<TransformLink> transformLinks1  =   mapTransformForwardLink.get(tLink.getSourceTransformHashCode());
        transformLinks1.remove(tLink);

        // update backward links for transform
        TransformLinkSet<TransformLink> transformLinks2  =   mapTransformBackwardLink.get(tLink.getTargetTransformHashCode());
        transformLinks2.remove(tLink);
        mapTransformLinkInterface.remove(tLinkhasCode);

        this.transformLinkKeys  =   mapTransformLinkInterface.keySet();

        return tLink;

    }

    @Override
    public void addTransformGraphProperty(String pKey, String pValue) {
        this.transformGraphProperties.put(pKey, pValue);
    }

    @Override
    public ConcurrentHashMap<String, String> getTransformGraphProperties() {

        return this.transformGraphProperties;
    }

    public HashMap<Integer, TransformLinkSet> getForwardLinks(){
        return this.mapTransformForwardLink;
    }

    public HashMap<Integer, TransformLinkSet> getBackwardLinks(){
        return this.mapTransformBackwardLink;
    }

    public HashMap<Integer, AbstractTransform> getTransformMap(){
        return this.mapTransformInterface;
    }

    public void createGraphStructure(){

        //ArrayList<AbstractTransform> lstTransforms   =   (ArrayList<AbstractTransform>) ();

        for(AbstractTransform tform: mapSrcTransformInterface.values()){
            System.out.print(tform.getTransformID());

            //this.crawlLinks(tform, new ArrayList<Integer>(){{add(0);}});
            this.crawlLinks(tform, new ArrayList<Integer>());
            System.out.println();
            System.out.println("######################################################");

        }


    }

    public void crawlLinks(AbstractTransform transform, List<Integer> indents){

        if (this.mapTransformForwardLink.containsKey(transform.hashCode())){
            indents.add(0);indents.add(0);indents.add(0);
            TransformLinkSet<TransformLink> transformLinks = mapTransformForwardLink.get(transform.hashCode());

            boolean prv = true;
            boolean curr = false;

            for(TransformLink tLink : transformLinks){

                curr = ! curr;

                if(curr != prv){
                    System.out.println();
                    System.out.println("------------------------------");
                    for(int cnt : indents) System.out.print(' ');

                }

                System.out.print( "-->" + mapTransformInterface.get(tLink.getTargetTransformHashCode()).getTransformID());

                this.crawlLinks(mapTransformInterface.get(tLink.getTargetTransformHashCode()), indents);

                prv = curr;
            }
            indents.remove(0);indents.remove(0);indents.remove(0);

        }

    }


}
