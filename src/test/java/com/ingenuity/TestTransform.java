package com.ingenuity;

import com.ingenuity.flow.struct.TransformGraph;
import com.ingenuity.transform.AbstractTransformLink;
import com.ingenuity.transform.Transform;
import com.ingenuity.transform.TransformType;
import org.testng.annotations.DataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.ArrayList;

@Test()
public class TestTransform {
    @DataProvider(name="inputtransforms")
    public Transform[][] createTransforms(){
        // 1-2, 1-3
        // 2-4
        // 3-5
        // 4-6
        // 5-6
        // 6-7
        // 5-8
        // 8-9

        ArrayList<Transform> lTransform = new ArrayList<Transform>();
        //Transform[] lTransform = new Transform[3];

        Transform src1      =    new Transform();
        src1.setTransformID(1);
        src1.setTransformName("source1");
        src1.setTransformType(TransformType.SOURCE);

        Transform src2      =    new Transform();
        src2.setTransformID(2);
        src2.setTransformName("transform2");
        src2.setTransformType(TransformType.TRANSFORM);

        Transform src3      =    new Transform();
        src3.setTransformID(3);
        src3.setTransformName("transform3");
        src3.setTransformType(TransformType.TRANSFORM);

        Transform src4      =    new Transform();
        src4.setTransformID(4);
        src4.setTransformName("transform4");
        src4.setTransformType(TransformType.TRANSFORM);

        Transform src5      =    new Transform();
        src5.setTransformID(5);
        src5.setTransformName("transform5");
        src5.setTransformType(TransformType.TRANSFORM);

        Transform src6      =    new Transform();
        src6.setTransformID(6);
        src6.setTransformName("transform6");
        src6.setTransformType(TransformType.TRANSFORM);

        Transform src7      =    new Transform();
        src7.setTransformID(7);
        src7.setTransformName("target7");
        src7.setTransformType(TransformType.TARGET);

        Transform src8      =    new Transform();
        src8.setTransformID(8);
        src8.setTransformName("transform8");
        src8.setTransformType(TransformType.TRANSFORM);

        Transform src9      =    new Transform();
        src9.setTransformID(9);
        src9.setTransformName("target9");
        src9.setTransformType(TransformType.TARGET);

        lTransform.add(src1);
        lTransform.add(src2);
        lTransform.add(src3);
        lTransform.add(src4);
        lTransform.add(src5);
        lTransform.add(src6);
        lTransform.add(src7);
        lTransform.add(src8);
        lTransform.add(src9);

        Transform[][] treturn = new Transform[lTransform.size()][1];
        treturn = (Transform[][]) lTransform.toArray();

        return treturn;
    }

    @Test(dataProvider = "inputtransforms")
    public static void Test1(Transform[] lTransforms){
        // 1-2, 1-3
        // 2-4
        // 3-5
        // 4-6
        // 5-6
        // 6-7
        // 5-8
        // 8-9
        //ArrayList<Transform> lTransforms = createTransforms();
        //TransformLinkSet<TransformLink> tlinks = createSet(lTransforms);

        TransformGraph tGraph = new TransformGraph();
        tGraph.setTransformGraphID(1);
        tGraph.setTransformGraphName("TestGraph1");

        for(Transform t : lTransforms){
            tGraph.addTransform(t);
        }

        tGraph.connectTransform(lTransforms[0], lTransforms[1]);
        tGraph.connectTransform(lTransforms[0], lTransforms[2]);
        tGraph.connectTransform(lTransforms[1], lTransforms[3]);
        AbstractTransformLink link3_5 = tGraph.connectTransform(lTransforms[2], lTransforms[4]);
        AbstractTransformLink link4_6 = tGraph.connectTransform(lTransforms[3], lTransforms[5]);
        AbstractTransformLink link4_5 = tGraph.connectTransform(lTransforms[4], lTransforms[5]);
        AbstractTransformLink link6_7 = tGraph.connectTransform(lTransforms[5], lTransforms[6]);
        AbstractTransformLink link5_8 = tGraph.connectTransform(lTransforms[4], lTransforms[7]);
        tGraph.connectTransform(lTransforms[7], lTransforms[8]);

        tGraph.createGraphStructure();

        tGraph.removeLink(link5_8);
        tGraph.connectTransform(lTransforms[2], lTransforms[7]);
        tGraph.createGraphStructure();

        tGraph.removeLink(link6_7);
        tGraph.removeLink(link4_6);
        tGraph.connectTransform(lTransforms[5], lTransforms[8]);
        tGraph.connectTransform(lTransforms[3], lTransforms[6]);
        tGraph.createGraphStructure();
    }

}

