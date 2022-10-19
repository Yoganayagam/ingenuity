package com.ingenuity.flow.persistStruct;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ingenuity.flow.struct.TransformGraph;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

        MessageDigest md    = null;
        try {
            md    =   MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            return false;
        }

        if (this.rootONode.path("header").has("hash")){
            this.rootONode.remove("hash");
        }
        byte[] byteVal  =   null;
        try {
            byteVal =   this.objMapper.writeValueAsBytes(this.rootONode);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        byte[] hashByte =   md.digest(byteVal);
        StringBuffer hexStringBuffer    =   new StringBuffer();

        for(byte ele : byteVal){
            hexStringBuffer.append(String.format("%02X",ele));
        }

        JsonNode headerJNode    =   this.rootONode.get("header");
        ObjectNode hdrONode =   ((ObjectNode)headerJNode).put("hash",hexStringBuffer.toString());
        this.rootONode.set("header", (JsonNode) hdrONode);

        return true;
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
