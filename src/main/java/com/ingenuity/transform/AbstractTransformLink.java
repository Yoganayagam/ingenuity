package com.ingenuity.transform;

public abstract class AbstractTransformLink implements TransformLinkInterface{

    private String transformLinkName;
    private int transformLinkID;

    private int sourceTransformHashCode;

    private int targetTransformHashCode;

    public AbstractTransformLink(TransformInterface source, TransformInterface target){
        this.transformLinkName = source.getTransformName().concat("_link_").concat(target.getTransformName());
        this.setSourceTransformHashCode(source.hashCode());
        this.setTargetTransformHashCode(target.hashCode());
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

    public int getSourceTransformHashCode() {
        return sourceTransformHashCode;
    }

    public void setSourceTransformHashCode(int sourceTransformHashCode) {
        this.sourceTransformHashCode = sourceTransformHashCode;
    }

    public int getTargetTransformHashCode() {
        return targetTransformHashCode;
    }

    public void setTargetTransformHashCode(int targetTransformHashCode) {
        this.targetTransformHashCode = targetTransformHashCode;
    }

    @Override
    public boolean equals(TransformLinkInterface tLinkInterface) {
        return this.transformLinkName.equals(tLinkInterface.getTransformLinkName());
    }

    @Override
    public int hashCode() {
        return this.transformLinkName.hashCode();
    }
}
