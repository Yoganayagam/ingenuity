package com.ingenuity.transform;

import java.util.Arrays;

public class TransformFactory {

    private int transformID = 1;

    public Transform getTransform(String type, String name) throws Exception {

        if (type==null || (type.trim().length() == 0)){
            throw new Exception("Transformation type not provided.");
        }

        /**
        if(!validTransformType(type)){
            throw new Exception("Invalid Transformation Type");
        }
        */
        TransformType transformType = TransformType.valueOf(type);

        Transform tForm =   new Transform();
        tForm.setTransformID(transformID);

        switch (type) {

            case "SOURCE":
                tForm.setTransformType(TransformType.SOURCE);
                break;
            case "TARGET":
                tForm.setTransformType(TransformType.TARGET);
                break;
            case "TRANSFORM":
                tForm.setTransformType(TransformType.TRANSFORM);
                break;
             default:
                 throw new Exception("Invalid Transformation Type");
        }

        if (name == null || (name.trim().length() == 0)){
            String tName =  transformType.name() + '_' + transformID;
            tForm.setTransformName(tName);
        } else
            tForm.setTransformName(name);

        transformID++;

        return tForm;
    }

    public boolean validTransformType(String type){

        for (TransformType tType : TransformType.values()){
            if (tType.name().equals(type)){
                return true;
            }
        }

        return false;
    }

}
