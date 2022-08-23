package com.ingenuity.transform;

public abstract class AbstractTransformLink implements TransformLinkInterface{

    private String transformLinkName;
    private int transformLinkID;

    public AbstractTransformLink(TransformInterface source, TransformInterface target){
        this.transformLinkName = source.getTransformName().concat("_link_").concat(target.getTransformName());
    }

    @Override
    public String setTransformLinkName(String name) {
        this.transformLinkName = name;
        return this.transformLinkName;
    }

    @Override
    public String getTransformLinkName() {
        return this.transformLinkName;
    }

    @Override
    public int setTransformLinkID(int ID) {
        this.transformLinkID = ID;
        return this.transformLinkID;
    }

    @Override
    public int getTransformLinkID() {
        return this.transformLinkID;
    }

    @Override
    public boolean equals(TransformLinkInterface tLinkInterface) {
        return this.transformLinkName.equals(tLinkInterface.getTransformLinkName());
    }
}
