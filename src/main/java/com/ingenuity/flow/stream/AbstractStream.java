package com.ingenuity.flow.stream;

import com.ingenuity.CommonPropertyLiterals;
import com.ingenuity.flow.struct.TransformGraph;
import com.ingenuity.transform.AbstractTransform;
import com.ingenuity.transform.Transform;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.BlockingQueue;

public class AbstractStream implements StreamInterface{

    private TransformGraph  transformGraph  =   new TransformGraph();
    private HashMap<Integer, BlockingQueue<?>[]>  bQueueMap   =   new HashMap<Integer, BlockingQueue<?>[]>();
    //private HashMap<Integer, Integer> transformHashMap  =   new HashMap<Integer, Integer>();


    @Override
    public void setTransformGraph(TransformGraph tGraph) {

        this.transformGraph =   tGraph;

        HashMap<Integer, AbstractTransform> tmap   =  this.transformGraph.getTransformMap();
        Iterator<Integer> keys  =   tmap.keySet().iterator();

        while(keys.hasNext()){
            AbstractTransform tform = tmap.get(keys.next());
            if (tform.getTransformProperties().get(CommonPropertyLiterals.transform_threads) != null) {
                int threads = Integer.parseInt(tform.getTransformProperties().get(CommonPropertyLiterals.transform_threads));
                setTransformQueue(tform.hashCode(), new BlockingQueue<?>[threads]);
            } else
                setTransformQueue(tform.hashCode(), new BlockingQueue<?>[1]);

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

        return null;
    }

    @Override
    public HashMap<Integer, BlockingQueue<?>[]> getDownstreamQueue(int tHashcode) {
        return null;
    }
}
