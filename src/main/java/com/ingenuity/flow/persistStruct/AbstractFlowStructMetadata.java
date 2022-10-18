package com.ingenuity.flow.persistStruct;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ingenuity.flow.struct.TransformGraph;

import java.text.SimpleDateFormat;
import java.util.Date;

public abstract class AbstractFlowStructMetadata implements FlowStructMetadataInterface {

    private TransformGraph transformGraph;
    ObjectMapper objMapper;
    ObjectNode rootNode;

    public AbstractFlowStructMetadata(TransformGraph tGraph){
        this.transformGraph =   tGraph;
        this.objMapper =  new ObjectMapper();
        this.rootNode = objMapper.createObjectNode();
    }

    @Override
    public boolean addStructHeader(String name, String type, int version) {

        ObjectNode  hdrNode =   objMapper.createObjectNode();
        hdrNode.put("name",name);
        hdrNode.put("structure_type",type);
        hdrNode.put("datetime",(new SimpleDateFormat().format(new Date(System.currentTimeMillis()))));
        hdrNode.put("version",String.valueOf(version));

        rootNode.set("header",hdrNode);

        return true;
    }

    @Override
    public boolean updateDatetime() {
        return false;
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
}
