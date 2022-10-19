package com.ingenuity.flow.persistStruct;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ingenuity.flow.struct.TransformGraph;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AbstractFlowStructMetadata implements FlowStructMetadataInterface {

    private TransformGraph transformGraph;
    private ObjectMapper objMapper;
    private ObjectNode rootONode;
    private JsonNode rootJNode;
    private byte[] jsonByteData;

    public AbstractFlowStructMetadata(TransformGraph tGraph){
        this.transformGraph =   tGraph;
        this.objMapper =  new ObjectMapper();
        this.rootONode = objMapper.createObjectNode();
    }

    public AbstractFlowStructMetadata(byte[] jsonData){
        this.jsonByteData   =   jsonData;
        this.objMapper =  new ObjectMapper();
        try {
            this.rootJNode  =   this.objMapper.readTree(this.jsonByteData);
            this.rootONode  =   (ObjectNode) this.rootJNode;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean addStructHeader(String name, String type, int version) {

        ObjectNode  hdrNode =   objMapper.createObjectNode();
        hdrNode.put("name",name);
        hdrNode.put("structure_type",type);
        hdrNode.put("datetime",(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date(System.currentTimeMillis()))));
        hdrNode.put("version",String.valueOf(version));

        rootONode.set("header",hdrNode);

        return true;
    }

    @Override
    public boolean updateDatetime() {
        String updateDateTime = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ").format(new Date(System.currentTimeMillis()));
        JsonNode datetimeNode   =   this.rootJNode.path("header");
        ObjectNode dateTimeONode    =   ((ObjectNode)datetimeNode).put("datetime",updateDateTime);
        this.rootONode.set("header", ((JsonNode) dateTimeONode));
        return true;
    }

    @Override
    public boolean addHashtoMetadata() {
        return false;
    }

    @Override
    public boolean checkHash() {
        return false;
    }

    @Override
    public boolean addStreamStructure() {
        return false;
    }

    @Override
    public boolean addStructProperties() {
        return false;
    }

    @Override
    public byte[] getJSONString() {
        byte[] retVal;
        try {
            retVal = this.objMapper.writeValueAsBytes(this.rootONode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        return retVal;
    }
}
