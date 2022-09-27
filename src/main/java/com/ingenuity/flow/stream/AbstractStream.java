package com.ingenuity.flow.stream;

import com.ingenuity.CommonPropertyLiterals;
import com.ingenuity.flow.struct.TransformGraph;
import com.ingenuity.transform.AbstractTransform;
import com.ingenuity.transform.Transform;
import com.ingenuity.transform.TransformLink;
import com.ingenuity.transform.TransformLinkSet;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

public class AbstractStream implements StreamInterface{

    private TransformGraph  transformGraph  =   new TransformGraph();
    private HashMap<Integer, BlockingQueue<?>[]>  bQueueMap   =   new HashMap<Integer, BlockingQueue<?>[]>();
    private HashMap<Integer, HashMap<Integer,BlockingQueue<?>[]>> bUpstreamQueueMap = new HashMap<Integer, HashMap<Integer,BlockingQueue<?>[]>>();
    private HashMap<Integer, HashMap<Integer,BlockingQueue<?>[]>> bDownstreamQueueMap = new HashMap<Integer, HashMap<Integer,BlockingQueue<?>[]>>();
    //private HashMap<Integer, Integer> transformHashMap  =   new HashMap<Integer, Integer>();


    @Override
    public void setTransformGraph(TransformGraph tGraph) {

        this.transformGraph =   tGraph;

        HashMap<Integer, AbstractTransform> tmap   =  this.transformGraph.getTransformMap();
        HashMap<Integer, TransformLinkSet> hMap_Upstream_links = this.transformGraph.getBackwardLinks();
        HashMap<Integer, TransformLinkSet> hMap_Downstream_links = this.transformGraph.getForwardLinks();

        Iterator<Integer> keys  =   tmap.keySet().iterator();

        while(keys.hasNext()){
            int hCode       =   keys.next();
            TransformLinkSet<TransformLink> linkSetBackward = hMap_Upstream_links.get(hCode);
            TransformLinkSet<TransformLink> linkSetForward = hMap_Downstream_links.get(hCode);

            AbstractTransform tform = tmap.get(hCode);
            if (tform.getTransformProperties().get(CommonPropertyLiterals.transform_threads) != null) {
                int threads = Integer.parseInt(tform.getTransformProperties().get(CommonPropertyLiterals.transform_threads));
                setTransformQueue(tform.hashCode(), new BlockingQueue<?>[threads]);
            } else
                setTransformQueue(tform.hashCode(), new BlockingQueue<?>[1]);

            this.setUpstreamQueues(hCode, linkSetBackward);
            this.setDownstreamQueues(hCode, linkSetForward);

        }

    }

    @Override
    public TransformGraph getTransformGraph() {
        return this.transformGraph;
    }

    @Override
    public void setTransformQueue(int tHashcode, BlockingQueue<?>[] bQueueArray) {
            bQueueMap.put(tHashcode, bQueueArray);
    }

    @Override
    public BlockingQueue<?>[] getTransformQueue(int tHashcode) {
        return bQueueMap.get(tHashcode);
    }

    @Override
    public HashMap<Integer, BlockingQueue<?>[]> getUpstreamQueue(int tHashcode) {
        return this.bUpstreamQueueMap.get(tHashcode);
    }

    @Override
    public HashMap<Integer, BlockingQueue<?>[]> getDownstreamQueue(int tHashcode) {
        return this.bDownstreamQueueMap.get(tHashcode);
    }

    @Override
    public boolean setUpstreamQueues(int tHashcode, TransformLinkSet<TransformLink> linkSet) {
        HashMap<Integer, BlockingQueue<?>[]> hMapQueue   =   new HashMap<Integer, BlockingQueue<?>[]>();
        for (TransformLink link : linkSet){
            Integer hCode = link.getSourceTransformHashCode();
            BlockingQueue<?>[] bQueue = bQueueMap.get(hCode);
            hMapQueue.put(hCode, bQueue);
        }
        bUpstreamQueueMap.put(tHashcode, hMapQueue);
        return true;
    }

    @Override
    public boolean setDownstreamQueues(int tHashcode, TransformLinkSet<TransformLink> linkSet) {
        HashMap<Integer, BlockingQueue<?>[]> hMapQueue   =   new HashMap<Integer, BlockingQueue<?>[]>();
        for (TransformLink link : linkSet){
            Integer hCode = link.getSourceTransformHashCode();
            BlockingQueue<?>[] bQueue = bQueueMap.get(hCode);
            hMapQueue.put(hCode, bQueue);
        }
        bDownstreamQueueMap.put(tHashcode, hMapQueue);
        return true;
    }
}
