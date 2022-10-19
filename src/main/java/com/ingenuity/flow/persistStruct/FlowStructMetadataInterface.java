package com.ingenuity.flow.persistStruct;

public interface FlowStructMetadataInterface {

    public boolean addStructHeader(String name, String type, int version);
    public boolean updateDatetime();
    public boolean addHashtoMetadata();
    public boolean checkHash();
    public boolean addStreamStructure();
    public boolean addStructProperties();

    public byte[] getJSONString();


}
