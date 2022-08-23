package com.ingenuity.transform;

public enum TransformType {

    SOURCE ((short)1,"source"),
    TARGET ((short)2,"target"),
    TRANSFORM ((short)3,"transform");
    private short tnum;
    private String tname;
    TransformType(short tnum, String tname){
        this.tnum   =   tnum;
        this.tname  =   tname;
    }

}
