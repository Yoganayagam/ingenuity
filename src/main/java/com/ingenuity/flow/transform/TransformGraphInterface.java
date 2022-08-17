package com.ingenuity.flow.transform;

import com.ingenuity.transform.TransformInterface;

import java.util.List;

public interface TransformGraphInterface {

    public TransformInterface addTransform(List<TransformInterface> sourceTransforms);
    public TransformInterface addTransform(List<TransformInterface> sourceTransforms, List<TransformInterface> targetTransform);

    public void connectTransform(TransformInterface sourceTransform, TransformInterface targetTransform);



}
