package com.ingenuity.flow.stream;

import com.ingenuity.flow.struct.TransformGraph;
import com.ingenuity.transform.Transform;
import com.ingenuity.transform.TransformLink;
import com.ingenuity.transform.TransformLinkSet;

import java.util.HashMap;
import java.util.concurrent.BlockingQueue;

public interface StreamInterface {

    public void setTransformGraph(TransformGraph tGraph);

    public TransformGraph getTransformGraph();

    public void setTransformQueue(int tHashcode, BlockingQueue<?>[] bQueue);

    public BlockingQueue<?> [] getTransformQueue(int tHashcode);

    public HashMap<Integer, BlockingQueue<?>[]> getUpstreamQueue(int tHashcode);

    public HashMap<Integer, BlockingQueue<?>[]> getDownstreamQueue(int tHashcode);

    public boolean setUpstreamQueues(int tHashcode, TransformLinkSet<TransformLink> linkSet);

    public boolean setDownstreamQueues(int tHashcode, TransformLinkSet<TransformLink> linkSet);

}
